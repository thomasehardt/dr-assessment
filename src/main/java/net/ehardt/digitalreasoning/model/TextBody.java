package net.ehardt.digitalreasoning.model;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TextBody {
	private List<Sentence> sentences = new ArrayList<>();
	
	public void parseTextBody(String s) {
		BreakIterator sbi = BreakIterator.getSentenceInstance();
		sbi.setText(s);
		
		int start = sbi.first();
		for (int end = sbi.next() ; end != BreakIterator.DONE ; start = end, end = sbi.next()) {
			String sentenceText = s.substring(start, end);
			
			Sentence sentence = new Sentence();
			sentence.parseSentence(sentenceText);
			
			sentences.add(sentence);
		}
	}

	@XmlElement (name = "sentence")
	public List<Sentence> getSentences() {
		return sentences;
	}

	public void setSentences(List<Sentence> sentences) {
		this.sentences = sentences;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		for (Sentence s : sentences) {
			sb.append(s.toString());
		}
		
		return sb.toString();
	}
}
