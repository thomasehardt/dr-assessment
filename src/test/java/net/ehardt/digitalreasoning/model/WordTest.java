package net.ehardt.digitalreasoning.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import net.ehardt.digitalreasoning.model.SentenceComponent.WordType;

public class WordTest {

	@Test
	public void testGetValue() {
		assertNull(new Word().getValue());

		assertEquals("foo", new Word("foo").getValue());

		Word w = new Word();
		w.setValue("bar");
		assertEquals("bar", w.getValue());
	}

	@Test
	public void testGetType() {
		assertEquals(WordType.word, new Word().getType());
		assertEquals(WordType.word, new Word("hello").getType());
		
		Word w = new Word();
		w.setValue("football");
		assertEquals(WordType.word, w.getType());
	}
}
