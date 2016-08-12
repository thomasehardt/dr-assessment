package net.ehardt.digitalreasoning.task;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import net.ehardt.digitalreasoning.model.TextBody;

public class TextParserTask implements Runnable {
	private String filename;
	private String text;
	private List<String> phrases;
	private TextBody result = new TextBody();
	
	public TextParserTask(String text) {
		this.text = text;
	}

	public TextParserTask(String text, String filename) {
		this.text = text;
		this.filename = filename;
		this.phrases = Collections.emptyList();
	}

	public TextParserTask(String text, String filename, List<String> phrases) {
		this.text = text;
		this.filename = filename;
		this.phrases = phrases;
	}

	@XmlElement(name = "textBody")
	public TextBody getResult() {
		return result;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@XmlAttribute(name = "filename")
	public String getFilename() {
		return filename;
	}

	@Override
	public void run() {
		result.parseTextBody(text, phrases);
	}
}
