package net.ehardt.digitalreasoning.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import net.ehardt.digitalreasoning.model.SentenceComponent.WordType;

public class PhraseTest {

	@Test
	public void testGetValue() {
		assertNull(new Phrase().getValue());

		assertEquals("Hello, world", new Phrase("Hello, world").getValue());

		Phrase p = new Phrase();
		p.setValue("The number thou shalt count to is three");
		assertEquals("The number thou shalt count to is three", p.getValue());
	}

	@Test
	public void testGetType() {
		assertEquals(WordType.phrase, new Phrase().getType());
		assertEquals(WordType.phrase, new Phrase("Coke Zero").getType());
		
		Phrase p = new Phrase();
		p.setValue("Flying monkeys");
		assertEquals(WordType.phrase, p.getType());
	}
}
