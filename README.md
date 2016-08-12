## Assumptions that were made
* we are working with small-ish bodies of text for processing; something that we can expect to fit into memory (e.g. an email or a user's email, not an entire company's email)
* using just the output XML, we should be able to re-create the original text
* no binary characters; we are working with "text" only
* Java's sentence parsing is good enough - there are some real trade-offs (see [below](##Limitations of Java's sentence parsing))
* punctuation is used to end sentences; we aren't working with multiple sentence fragments such as is commonly found in a list

## testing methods
* unit tests
* since I'm outputting XML, I wrote a simple XSLT file to transform the output XML back into the original text; this allowed me to do some easy testing (and catch things that were missed in unit tests)
* I love [XMLStarlet](http://xmlstar.sourceforge.net/), and given how easy it is to use, I used it to validate the XML instead of writing a tests.

## Shortcuts taken
* testing is done only on English text; the input text is known to be English, so for the case of this project (and in the interest of time), no testing was done with, say, Chinese or Arabic text (though `BreakIterator` is `Locale` aware and would be expected to work)
* no frameworks used for XML processing; instead, I used JAXB which does have an advantage of simply requiring annotations
* no frameworks used for file reading; in a production task, I would likely turn to Apache Commons or something similar for this
* the task class for processing multiple files does not include the reading of the file; instead, the file is read and the contents are stored with the task object

## Other approaches
### parsing
The use of a trusted and well-tested NLP library would be a much better way to properly parse the text into sentences. Since that is expressly forbidden, I saw two options: use what Java provides in `BreakIterator` or turn to regular expressions.

I played around with `BreakIterator` to make sure that it met my needs adequately, and it alleviated a lot more testing and such with regular expressions. Also, unless they are very well-written (and likely difficult to read), handling other languages with different ways of breaking apart words would be nearly impossible.

### XML
I like simplicity, so the XML is simple. For an individual task execution, there is a `textBody` which has 1 or more `sentence` elements, each containing 1 or more `sentenceComponent` elements. Instead of the generic `sentenceComponent`, I could have used `word`, `phrase`, `punctuation`, and `whitespace`, but I feel this is a bit too specific, so instead of being elements, these are attributes.

### separation of model and business logic
If I were writing a parsing library, I would have completely separated the model from the parsing logic, but for the purposes of this project, I felt it was an OK trade-off to include the parsing logic in `TextBody` and `Sentence`. It also helps for multi-threading to have it combined.

I also opted to have parsing be a separate step, as this allows us to delay the "heavy lifting" until the last responsible moment.

### tasks - should we read files or not?
tl;dr: NO

When setting up the tasks for each file in the ZIP file, I read the content and send it to the task instead of having the task read the file contents. This is a good choice for processing the texts in parallel. If the tasks would read the files (either as pointers to the ZIP file or as files extracted from the ZIP file), it would not allow for the tasks to be processed, say, on other computers. For a small project such as this, it's not a concern, but it is the choice I would make if I were handling millions of such files.

## other thoughts
TDD always seems like extra work until it proves otherwise. During some exploratory testing with `BreakIterator`, I made some assumptions that weren't true and refactored a few things - having simple tests made this quickly visible.

I struggled a bit with naming things; specifically, what to call the pieces that make up a `Sentence`. I settled on `SentenceComponent`, but I'm sure there's a standard term that I just couldn't think of.

## Limitations of Java's sentence parsing
Given the limitation of no third-party libraries for NLP, I was faced with a choice: roll my own or use what Java provides. In a real-world scenario, I would use an existing library (either one written in-house or a third-party one), so I opted to use Java's `BreakIterator` class to perform sentence/word tokenization.

The biggest limitation is that of how sentence boundaries are determined:
```
Doctor Who is my favorite T.V. show.
```
is considered one sentence - the second period in `T.V.` is correctly identified as _not_ being the end of a sentence. However, this sentence:
```
Dr. Cox is my favorite T.V. doctor.
```
is broken into two sentences:
```
Dr.
```
and
```
Cox is my favorite T.V. doctor.
```
In the specific case of using `BreakIterator`, it would be possible to have a list of common abbreviations that we might encounter and all initials (e.g. A. B.) and override the end-of-sentence designation in such cases. We would still be left with a problem with initials; `Philip K. Dick` would not end a sentence, but how would we handle `My favorite comedian is Louis C. K. He is very funny.`?

There is no analysis of the text, so only typical terminal punctuation marks (e.g. ., !, and ? for English) are used to determine sentence endings. Because of the way Java handles internationalization, this should work across any language, but we still cannot process sentence fragments that we might typically come across. For example, in an email, we might see:
```
Here are the things we need to do:
* develop product
* test product
* release product
* make money
```
Using `BreakIterator`, this is treated as one sentence because there are no terminal punctuation marks.
