package net.ehardt.digitalreasoning.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SentenceTest {
	private static final String BASE_STRING = "My name is Yon Yonson. I live in Wisconsin.";
	private static final String WHITESPACE_STRING = "   ";
	private static final String ENDSPACE_STRING = " 1 2 3 ";
	private static final String PARSE_VALIDATION_STRING = "hello, world!";

	private static final String PHRASE_TEST_STRING = "My name is Yon Yonson. I love Halloween.";

	private static final List<String> PHRASE_HITS = new ArrayList<>();
	private static final List<String> PHRASE_MISSES = new ArrayList<>();
	private static final List<String> PHRASES = new ArrayList<>();

	@Before
	public void setup() {
		PHRASE_HITS.add("Yon Yonson");
		PHRASE_HITS.add("Halloween");

		PHRASE_MISSES.add("hello there");
		PHRASE_MISSES.add("Hall");

		PHRASES.addAll(PHRASE_HITS);
		PHRASES.addAll(PHRASE_MISSES);
	}

	@Test
	public void testParse() {
		// base case:
		// verify that a word, a whitespace token, and a punctuation mark each
		// count as one token
		Sentence s = new Sentence();
		s.parseSentence(BASE_STRING);
		assertEquals(19, s.getSentenceComponents().size());

		// verify that a string of whitespace count as one token
		s = new Sentence();
		s.parseSentence(WHITESPACE_STRING);
		assertEquals(1, s.getSentenceComponents().size());

		// verify that leading and trailing space is kept
		s = new Sentence();
		s.parseSentence(ENDSPACE_STRING);
		assertEquals(7, s.getSentenceComponents().size());

		// verify parsing works as expected
		s = new Sentence();
		s.parseSentence(PARSE_VALIDATION_STRING);
		List<SentenceComponent> tokens = s.getSentenceComponents();
		assertEquals(5, tokens.size());
		assertEquals("hello", tokens.get(0).getValue());
		assertEquals(",", tokens.get(1).getValue());
		assertEquals(" ", tokens.get(2).getValue());
		assertEquals("world", tokens.get(3).getValue());
		assertEquals("!", tokens.get(4).getValue());
	}

	@Test
	public void testGetPhraseMatchHits() {
		Sentence s = new Sentence();
		s.parseSentence(PHRASE_TEST_STRING, PHRASES);

		List<SentenceComponent> sentenceComponents = s.getSentenceComponents();

		for (String phrase : PHRASE_HITS) {
			assertTrue(sentenceComponents.contains(new Phrase(phrase)));
		}
	}

	@Test
	public void testGetPhraseMatchMisses() {
		Sentence s = new Sentence();
		s.parseSentence(PHRASE_TEST_STRING);

		List<SentenceComponent> sentenceComponents = s.getSentenceComponents();

		for (String phrase : PHRASE_MISSES) {
			assertFalse(sentenceComponents.contains(new Phrase(phrase)));
		}
	}
}
