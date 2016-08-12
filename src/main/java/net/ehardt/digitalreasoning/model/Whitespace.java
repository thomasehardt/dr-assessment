package net.ehardt.digitalreasoning.model;

public class Whitespace extends SentenceComponent {

	public Whitespace() {
		super();
	}
	
	public Whitespace(String whiteSpaceString) {
		super(whiteSpaceString);
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public WordType getType() {
		return WordType.whitespace;
	}
}
