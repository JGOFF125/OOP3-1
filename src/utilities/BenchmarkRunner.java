package utilities;

/**
 * Measures how long a sorting operation takes to run in nanoseconds.
 */
public class BenchmarkRunner {

    /**
     * Measures the time taken to run a sorting task.
     * completed by Adeeb
     * @param sortingTask the action containing the sort operation
     * @return total time taken in nanoseconds
     */
    public static long measureNanos(Runnable sortingTask) {
        // Stop if no sorting action was provided
        if (sortingTask == null) {
            throw new IllegalArgumentException("Sorting task cannot be empty.");
        }

        // Record start time, run the sort, and record end time
        long startTime = System.nanoTime();
        sortingTask.run();
        long endTime = System.nanoTime();

        // Calculate and return the total elapsed nanoseconds
        return endTime - startTime;
    }
}