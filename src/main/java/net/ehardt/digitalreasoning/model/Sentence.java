package net.ehardt.digitalreasoning.model;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Sentence {
	private List<SentenceComponent> sentenceItems;

	public Sentence() {
		sentenceItems = new ArrayList<>();
	}

	public void parseSentence(String s) {
		BreakIterator wbi = BreakIterator.getWordInstance();
		wbi.setText(s);

		int start = wbi.first();
		for (int end = wbi.next() ; end != BreakIterator.DONE ; start = end, end = wbi.next()) {
			String wordString = s.substring(start, end);

			SentenceComponent w;

			if (wordString.trim().length() == 0) {
				w = new Whitespace(wordString);
			} else if (wordString.matches("\\w+")) {
				w = new Word(wordString);
			} else {
				w = new Punctuation(wordString);
			}

			sentenceItems.add(w);
		}
	}

	@XmlElement(name = "sentenceComponent")
	public List<SentenceComponent> getSentenceComponents() {
		return sentenceItems;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		for (SentenceComponent aw : sentenceItems) {
			sb.append(aw.toString());
		}

		return sb.toString();
	}

	public static int getWordCount(String phrase) {
		BreakIterator wbi = BreakIterator.getWordInstance();
		wbi.setText(phrase);

		int count = 0;

		while (wbi.next() != BreakIterator.DONE) {
			count++;
		}

		return count;
	}
}
