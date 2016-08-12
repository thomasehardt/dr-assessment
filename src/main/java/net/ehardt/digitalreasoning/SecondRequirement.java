package net.ehardt.digitalreasoning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import net.ehardt.digitalreasoning.model.Sentence;
import net.ehardt.digitalreasoning.model.SentenceComponent;
import net.ehardt.digitalreasoning.model.SentenceComponent.WordType;
import net.ehardt.digitalreasoning.model.TextBody;

public class SecondRequirement {

	public static void main(String[] args) throws Exception {
		List<String> phrases = new ArrayList<>();

		try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/NER.txt"));) {
			String line = null;

			while ((line = reader.readLine()) != null) {
				if (line.trim().length() > 0) {
					phrases.add(line);
				}
			}

			// sort in order that phrases with most words appear first
			Collections.sort(phrases, new Comparator<String>() {
				@Override
				public int compare(String s1, String s2) {
					return s2.length() - s1.length();
				}
			});
		} catch (IOException e) {
			System.err.format("%s: %s%n\n", e.getClass().getName(), e);
		}

		String s;
		TextBody tb = new TextBody();

		try {
			s = new String(Files.readAllBytes(Paths.get("src/main/resources/nlp_data.txt")));

			tb.parseTextBody(s, phrases);
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringWriter sw = new StringWriter();
		JAXBContext c = JAXBContext.newInstance(TextBody.class);
		Marshaller m = c.createMarshaller();

		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(tb, sw);
		System.out.printf("%s\n", sw);

		// per the requirement, print out each matched phrase
		for (Sentence sentence : tb.getSentences()) {
			for (SentenceComponent word : sentence.getSentenceComponents()) {
				if (word.getType() == WordType.phrase) {
					System.out.printf("matched phrase: %s\n", word);
				}
			}
		}
	}

	public static int getWordCount(String phrase) {
		BreakIterator wbi = BreakIterator.getWordInstance();
		wbi.setText(phrase);

		int count = 0;

		while (wbi.next() != BreakIterator.DONE) {
			count++;
		}

		return count;
	}
}
