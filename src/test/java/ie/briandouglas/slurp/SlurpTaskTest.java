package ie.briandouglas.slurp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SlurpTaskTest {

	public class TestRunnable implements Runnable {
		public void run() {
		}
	}

	@Test
	void testCreateSlurpTaskWithRunnable() {
		assertDoesNotThrow(() -> new SlurpTask("myTask", new TestRunnable()));
	}

	@Test
	void testCreateSlurpTaskWithLambdaRunnable() {
		assertDoesNotThrow(() -> new SlurpTask("myTask", () -> {
		}));
	}

	@Test
	void testBlankName() {
		assertThrows(IllegalArgumentException.class, () -> new SlurpTask("", () -> {
		}));
	}

	@Test
	void testNullName() {
		assertThrows(IllegalArgumentException.class, () -> new SlurpTask(null, () -> {
		}));
	}

	@Test
	void testNullRunnable() {
		assertThrows(IllegalArgumentException.class, () -> new SlurpTask("myTask", null));
	}

}
