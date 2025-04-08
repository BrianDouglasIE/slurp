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
package ie.briandouglas.slurp.exceptions;

/**
 * Exception thrown when a requested task cannot be found.
 * 
 * @author Brian Douglas
 */
public class TaskNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code TaskNotFoundException} with the specified detail message.
     * 
     * @param message The detail message, which provides additional information about
     *                the task that could not be found.
     */
    public TaskNotFoundException(String message) {
        super(message);
    }
}
