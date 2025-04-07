package ie.briandouglas.slurp;

public record SlurpTask(String name, Runnable runnable) {
	public SlurpTask {
		if (null == name || name.isBlank()) {
			throw new IllegalArgumentException("Name cannot be null or blank");
		}
		if (null == runnable) {
			throw new IllegalArgumentException("Runnable cannot be null");
		}
	}
}
