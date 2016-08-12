package net.ehardt.digitalreasoning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import net.ehardt.digitalreasoning.task.TaskList;
import net.ehardt.digitalreasoning.task.TextParserTask;

public class ThirdRequirement {

	public static void main(String[] args) throws Exception {
		List<String> phrases = new ArrayList<>();

		try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/NER.txt"));) {
			String line = null;

			while ((line = reader.readLine()) != null) {
				if (line.trim().length() > 0) {
					phrases.add(line);
				}
			}

			// longest first - a poor man's way of enforcing greediness
			Collections.sort(phrases, new Comparator<String>() {
				@Override
				public int compare(String s1, String s2) {
					return s2.length() - s1.length();
				}
			});
		} catch (IOException e) {
			System.err.format("%s: %s%n\n", e.getClass().getName(), e);
		}

		TaskList tasks = new TaskList();

		Path zipFilePath = Paths.get("src/main/resources/nlp_data.zip");
		try (FileSystem zipFileFs = FileSystems.newFileSystem(zipFilePath, null);) {
			Path p = zipFileFs.getPath("/nlp_data");

			DirectoryStream<Path> ds = Files.newDirectoryStream(p);
			for (Path fp : ds) {
				String fpContents = new String(Files.readAllBytes(fp));

				tasks.addTask(new TextParserTask(fpContents, fp.getFileName().toString(), phrases));
			}
		}

		// add our tasks to the pool
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		for (TextParserTask tpt : tasks.getTasks()) {
			executor.execute(tpt);
		}

		// shutdown in an orderly fashion - make sure to allow things to finish
		executor.shutdown();

		while (!executor.isTerminated()) {
		}

		StringWriter sw = new StringWriter();
		JAXBContext c = JAXBContext.newInstance(TaskList.class);
		Marshaller m = c.createMarshaller();

		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(tasks, sw);
		System.out.print(sw.toString());
	}
}
