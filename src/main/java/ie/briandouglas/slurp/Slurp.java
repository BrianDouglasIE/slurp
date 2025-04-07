/*
 * Copyright 2025 Brian Douglas.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ie.briandouglas.slurp;

import ie.briandouglas.slurp.exceptions.DuplicateTaskException;
import ie.briandouglas.slurp.exceptions.TaskNotFoundException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *
 * @author Brian Douglas
 */
public class Slurp {

	private final HashMap<String, SlurpTask> tasks = new HashMap<>();

	public Slurp() {
	}

	public Set<String> getTaskNames() {
		return tasks.keySet();
	}

	public void registerTask(SlurpTask task) {
		if (tasks.containsKey(task.name())) {
			throw new DuplicateTaskException("Task with name '" + task.name() + "' already exists");
		}

		tasks.put(task.name(), task);
	}

	public void executeTask(String taskName) {
		if (!tasks.containsKey(taskName)) {
			throw new TaskNotFoundException("Task with name '" + taskName + "' not found");
		}

		tasks.get(taskName).runnable().run();
	}

	public void executeTask(SlurpTask task) {
		executeTask(task.name());
	}

	public void executeTaskSequence(String[] taskNames) {
		if (null == taskNames || 0 == taskNames.length) {
			throw new IllegalArgumentException("Task names cannot be null or empty");
		}

		executeTasks(taskNames);
	}

	public void executeTaskSequence(SlurpTask[] tasks) {
		if (null == tasks || 0 == tasks.length) {
			throw new IllegalArgumentException("Tasks cannot be null or empty");
		}

		String[] taskNames = Arrays.stream(tasks).map(SlurpTask::name).toArray(String[]::new);

		executeTasks(taskNames);
	}

	private void executeTasks(String[] taskNames) {
		ExecutorService executor = Executors.newSingleThreadExecutor();

		try {
			for (String taskName : taskNames) {
				executor.submit(() -> executeTask(taskName));
			}
		} finally {
			executor.shutdown();
		}
	}
}
