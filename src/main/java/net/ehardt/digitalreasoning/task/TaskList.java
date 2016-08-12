package net.ehardt.digitalreasoning.task;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="tasks")
public class TaskList {
	private List<TextParserTask> tasks = new ArrayList<>();

	public void addTask(TextParserTask task) {
		tasks.add(task);
	}
	
	@XmlElement(name="task")
	public List<TextParserTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<TextParserTask> tasks) {
		this.tasks = tasks;
	}
}
