package net.ehardt.digitalreasoning.model;

import javax.xml.bind.annotation.XmlAttribute;

/*
 * represents a chunk of a sentence; can be
 * Word (single word)
 * Phrase (multiple words)
 * Punctuation
 * Whitespace
 */
public abstract class SentenceComponent {
	protected String value;

	public static enum WordType {word, punctuation, whitespace, phrase};
	
	public SentenceComponent() {}
	
	public SentenceComponent(String s) {
		this.value = s;
	}
	
	public String getValue() {
		return value;
	};
	
	@XmlAttribute(name = "type")
	public abstract WordType getType();

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		
		if (!(o instanceof SentenceComponent)) {
			return false;
		}
		
		SentenceComponent sc = (SentenceComponent) o;
		
		// equal iff value matches and type matches
		boolean valueMatches = sc.getValue().compareTo(this.getValue()) == 0;
		boolean typeMatches = sc.getType().compareTo(this.getType()) == 0;
		
		return valueMatches && typeMatches;
	}
}
