package net.ehardt.digitalreasoning.model;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class TextBodyTest {

	@Test
	public void testParseTextBody() {
		// start with simple tests - number of sentences
		String testString = "My name is Yon Yonson. I live in Wisconsin. I work in a lumber mill there.";

		TextBody tb = new TextBody();
		tb.parseTextBody(testString);
		List<Sentence> sentences = tb.getSentences();

		assertEquals(3, sentences.size());

		assertEquals("My name is Yon Yonson. ", sentences.get(0).toString());
		assertEquals("I live in Wisconsin. ", sentences.get(1).toString());
		assertEquals("I work in a lumber mill there.", sentences.get(2).toString());
	}
}
