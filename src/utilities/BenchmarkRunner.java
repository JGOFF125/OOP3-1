package utilities;

/** Measures only the supplied sorting operation */
public class BenchmarkRunner {
    /**
     * Measures only the supplied sorting task using System.nanoTime().
     * @param sortingTask sorting call to measure
     * @return elapsed nanoseconds; the driver displays a measurement unit
     */
    public static long measureNanos(Runnable sortingTask) {
        java.util.Objects.requireNonNull(sortingTask, "Sorting task cannot be null");
        long start = System.nanoTime();
        sortingTask.run();
        return System.nanoTime() - start;
    }
}
