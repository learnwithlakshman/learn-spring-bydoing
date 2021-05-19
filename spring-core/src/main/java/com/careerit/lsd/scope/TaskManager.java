package com.careerit.lsd.scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

@Named
@Singleton
public class TaskManager {

	@Inject
	private Provider<Task> taskProvider;

	private int count = 0;

	public TaskManager() {
		System.out.println("Task Manager");
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Task getTask() {
		return taskProvider.get();
	}
	// Opened resource
	// Closed resource

	@PostConstruct
	public void init() {
		System.out.println("Opened Resource");
	}

	@PreDestroy
	public void destory() {
		System.out.println("Closed Resource");
	}

}
