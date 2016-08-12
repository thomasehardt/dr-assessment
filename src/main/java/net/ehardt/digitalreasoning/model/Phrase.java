package net.ehardt.digitalreasoning.model;

public class Phrase extends SentenceComponent {

	public Phrase() {
		super();
	}
	
	public Phrase(String phrase) {
		super(phrase);
	}
	
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public WordType getType() {
		return WordType.phrase;
	}
}
