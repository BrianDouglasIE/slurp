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
 * A Slurp instance is used to register and execute {@link SlurpTask}s. It
 * manages a collection of tasks that can be executed by their name.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 * 
 * @author Brian Douglas
 */
public class Slurp {

	private final HashMap<String, SlurpTask> tasks = new HashMap<>();

	/**
	 * Constructs a new Slurp instance.
	 */
	public Slurp() {
	}

	/**
	 * Returns a set of all registered task names.
	 * 
	 * @return a set of task names
	 */
	public Set<String> getTaskNames() {
		return tasks.keySet();
	}

	/**
	 * Registers a {@link SlurpTask}. The registered task can then be executed by
	 * its name.
	 * 
	 * @param task the task to register
	 * @throws DuplicateTaskException if a task with the same name already exists
	 */
	public void registerTask(SlurpTask task) {
		if (tasks.containsKey(task.name())) {
			throw new DuplicateTaskException("Task with name '" + task.name() + "' already exists");
		}

		tasks.put(task.name(), task);
	}

	/**
	 * Executes a registered {@link SlurpTask} by its name.
	 * 
	 * @param taskName the name of the task to execute
	 * @throws TaskNotFoundException if no task with the specified name is found
	 */
	public void executeTask(String taskName) {
		if (!tasks.containsKey(taskName)) {
			throw new TaskNotFoundException("Task with name '" + taskName + "' not found");
		}

		tasks.get(taskName).runnable().run();
	}

	/**
	 * Executes a registered {@link SlurpTask}.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask(SlurpTask task) {
		executeTask(task.name());
	}

	/**
	 * Executes a sequence of registered tasks by their names.
	 * 
	 * @param taskNames the names of the tasks to execute in sequence
	 * @throws IllegalArgumentException if the task names array is null or empty
	 */
	public void executeTaskSequence(String[] taskNames) {
		if (null == taskNames || 0 == taskNames.length) {
			throw new IllegalArgumentException("Task names cannot be null or empty");
		}

		executeTasks(taskNames);
	}

	/**
	 * Executes a sequence of registered tasks.
	 * 
	 * @param tasks the tasks to execute in sequence
	 * @throws IllegalArgumentException if the tasks array is null or empty
	 */
	public void executeTaskSequence(SlurpTask[] tasks) {
		if (null == tasks || 0 == tasks.length) {
			throw new IllegalArgumentException("Tasks cannot be null or empty");
		}

		String[] taskNames = Arrays.stream(tasks).map(SlurpTask::name).toArray(String[]::new);

		executeTasks(taskNames);
	}

	/**
	 * Executes a sequence of tasks by their names using a single-threaded executor.
	 * 
	 * @param taskNames the names of the tasks to execute
	 */
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