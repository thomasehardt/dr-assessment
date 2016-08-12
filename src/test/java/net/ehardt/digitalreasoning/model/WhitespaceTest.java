package net.ehardt.digitalreasoning.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import net.ehardt.digitalreasoning.model.SentenceComponent.WordType;

public class WhitespaceTest {

	@Test
	public void testGetValue() {
		assertNull(new Whitespace().getValue());

		assertEquals("  ", new Whitespace("  ").getValue());

		Whitespace w = new Whitespace();
		w.setValue(" ");
		assertEquals(" ", w.getValue());
	}

	@Test
	public void testGetType() {
		assertEquals(WordType.whitespace, new Whitespace().getType());
		assertEquals(WordType.whitespace, new Whitespace("\n").getType());
		
		Whitespace w = new Whitespace();
		w.setValue("\t");
		assertEquals(WordType.whitespace, w.getType());
	}
}
