package net.ehardt.digitalreasoning.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import net.ehardt.digitalreasoning.model.SentenceComponent.WordType;

public class PunctuationTest {

	@Test
	public void testGetValue() {
		assertNull(new Punctuation().getValue());

		assertEquals(".", new Punctuation(".").getValue());

		Punctuation p = new Punctuation();
		p.setValue("-");
		assertEquals("-", p.getValue());
	}

	@Test
	public void testGetType() {
		assertEquals(WordType.punctuation, new Punctuation().getType());
		assertEquals(WordType.punctuation, new Punctuation("-").getType());
		
		Punctuation p = new Punctuation();
		p.setValue(":");
		assertEquals(WordType.punctuation, p.getType());
	}
}
