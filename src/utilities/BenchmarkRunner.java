package utilities;

/** I measure the sorting call without timing file input or output */
public class BenchmarkRunner {

    /**
     * I measure the sorting call in nanoseconds
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
