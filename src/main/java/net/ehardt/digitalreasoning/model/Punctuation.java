package net.ehardt.digitalreasoning.model;

public class Punctuation extends SentenceComponent {

	public Punctuation() {
		super();
	}

	public Punctuation(String wordString) {
		super(wordString);
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public WordType getType() {
		return WordType.punctuation;
	}
}
