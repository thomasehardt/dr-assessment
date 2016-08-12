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
		parseSentence(s, new ArrayList<String>());
	}
	
	public void parseSentence(String s, List<String> phrases) {
		BreakIterator wbi = BreakIterator.getWordInstance();
		wbi.setText(s);

		int start = wbi.first();
		for (int end = wbi.next() ; end != BreakIterator.DONE ; start = end, end = wbi.next()) {
			String wordString = s.substring(start, end);
			
			SentenceComponent w;
			
			if (wordString.trim().length() == 0) {
				w = new Whitespace(wordString);
			} else {
				String phraseMatch = getPhraseMatch(s.substring(start), phrases);
				if (phraseMatch != null) {
					w = new Phrase(phraseMatch);
					
					int skipCount = getWordCount(phraseMatch);
					while (skipCount > 1) {
						end = wbi.next();
						skipCount--;
					}
				} else if (wordString.matches("\\w+")){
					w = new Word(wordString);
				} else {
					w = new Punctuation(wordString);
				}
			}
			
			sentenceItems.add(w);
		}
	}
	
	private String getPhraseMatch(String s, List<String> phrases) {
		String matchedPhrase = null;
		
		for (String phrase : phrases) {
			if (s.startsWith(phrase)) {
				if (s.length() >= phrase.length()) {
					int pCount = getWordCount(phrase);
					
					StringBuffer sb = new StringBuffer();
					BreakIterator wbi = BreakIterator.getWordInstance();
					wbi.setText(s);

					int start = wbi.first();
					for (int end = wbi.next() ; end != BreakIterator.DONE && pCount > 0; start = end, end = wbi.next()) {
						
						sb.append(s.substring(start, end));
						pCount--;
					}
					if (sb.toString().compareTo(phrase) == 0) {
						matchedPhrase = phrase;
						break;
					}
				}
				
			}
		}
		
		return matchedPhrase;
	}

	@XmlElement(name = "word")
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
