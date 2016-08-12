package net.ehardt.digitalreasoning;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import net.ehardt.digitalreasoning.model.TextBody;

public class FirstRequirement {

	public static void main(String[] args) throws Exception {

		String s;
		TextBody tb = new TextBody();

		try {
			s = new String(Files.readAllBytes(Paths.get("src/main/resources/nlp_data.txt")));

			tb.parseTextBody(s);
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringWriter sw = new StringWriter();
		JAXBContext c = JAXBContext.newInstance(TextBody.class);
		Marshaller m = c.createMarshaller();

		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(tb, sw);
		System.out.print(sw.toString());
	}
}
