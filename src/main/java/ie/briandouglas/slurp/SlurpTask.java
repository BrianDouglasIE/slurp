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