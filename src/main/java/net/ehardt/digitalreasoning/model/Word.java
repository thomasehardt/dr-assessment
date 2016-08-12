package net.ehardt.digitalreasoning.model;

public class Word extends SentenceComponent {

	public Word() {
		super();
	}
	
	public Word(String wordString) {
		super(wordString);
	}
	
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public WordType getType() {
		return WordType.word;
	}
}
