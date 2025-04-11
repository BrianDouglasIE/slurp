package ie.briandouglas.slurp;

import ie.briandouglas.slurp.exceptions.DuplicateTaskException;
import ie.briandouglas.slurp.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Brian Douglas
 */
public class SlurpTest {

	public SlurpTest() {
	}

	@Test
	public void testSlurp() {
		System.out.println("Slurp");
		assertInstanceOf(Slurp.class, new Slurp());
	}

	@Test
	public void testAddTaskSuccessfully() {
		Slurp instance = new Slurp();
		SlurpTask task = new SlurpTask("myTask", () -> {
		});
		instance.registerTask(task);
		assertDoesNotThrow(() -> instance.executeTask(task));
	}

	@Test
	public void testAddTaskWithDuplicateName() {
		Slurp instance = new Slurp();
		SlurpTask task = new SlurpTask("myTask", () -> {
		});
		instance.registerTask(task);
		assertThrows(DuplicateTaskException.class, () -> instance.registerTask(task));
	}

	@Test
	public void testExecuteTaskSuccessfully() {
		final int[] numbers = new int[] { 0 };
		Slurp instance = new Slurp();
		SlurpTask task = new SlurpTask("myTask", () -> {
			numbers[0]++;
		});

		instance.registerTask(task);

		assertDoesNotThrow(() -> instance.executeTask(task));
		assertEquals(numbers[0], 1);
	}

	@Test
	public void testExecuteUnknownTask() {
		Slurp instance = new Slurp();
		assertThrows(TaskNotFoundException.class, () -> instance.executeTask("notFound"));
	}

	@Test
	public void testGetTaskNames() {
		Slurp instance = new Slurp();
		SlurpTask a = new SlurpTask("a", () -> {
		});
		SlurpTask b = new SlurpTask("b", () -> {
		});
		instance.registerTask(a);
		instance.registerTask(b);
		assertEquals(instance.getTaskNames().toString(), "[a, b]");
	}

	@Test
	public void testExecuteTaskSequenceSuccessfully() {
		final int[] numbers = new int[] { 0 };
		Slurp instance = new Slurp();
		SlurpTask incrementTask = new SlurpTask("increment", () -> {
			numbers[0]++;
		});
		SlurpTask decrementTask = new SlurpTask("decrement", () -> {
			numbers[0]--;
		});

		instance.registerTask(incrementTask);
		instance.registerTask(decrementTask);

		SlurpTask[] taskSequence = new SlurpTask[] { incrementTask, decrementTask };

		assertDoesNotThrow(() -> instance.executeTaskSequence(taskSequence));
		assertEquals(numbers[0], 0);
	}
}
