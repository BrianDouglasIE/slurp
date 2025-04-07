package ie.briandouglas.slurp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SlurpTaskTest {

	@Test
	void testCreateSlurpTaskSuccess() {
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
