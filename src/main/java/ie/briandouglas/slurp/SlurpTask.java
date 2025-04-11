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

/**
 * A record that represents a task to be executed by the Slurp framework. It
 * contains a name and a runnable task.
 * 
 * @param name     the name of the task
 * @param runnable the runnable task to be executed
 */
public record SlurpTask(String name, Runnable runnable) {

	/**
	 * Constructs a new {@link SlurpTask}.
	 * 
	 * @param name     the name of the task
	 * @param runnable the runnable task to be executed
	 * @throws IllegalArgumentException if the name is null, blank, or if the
	 *                                  runnable is null
	 */
	public SlurpTask {
		if (null == name || name.isBlank()) {
			throw new IllegalArgumentException("Name cannot be null or blank");
		}
		if (null == runnable) {
			throw new IllegalArgumentException("Runnable cannot be null");
		}
	}
}