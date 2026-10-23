import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import appDomain.AppDriver;
import products.Product;
import utilities.BubbleSort;
import utilities.InsertionSort;
import utilities.ResultPrinter;
import utilities.SelectionSort;

/** Standalone checks for Joaquin's work, without replacing teammates' code */
public class JoaquinChecks {
    /** Product substitute used only in these checks */
    private static class TestProduct extends Product {
        /** Creates a test product with two prices */
        TestProduct(double original, double updated) { super(original, updated); }
        /** Returns an adjusted difference for printer and driver checks */
        @Override public double calcRealDiff() {
            return 0.5 * Math.abs(getOriginalPrice() - getUpdatedPrice());
        }
        /** Supplies natural comparison while the real base class is unfinished */
        @Override public int compareTo(Product other) {
            return Double.compare(calcRealDiff(), other.calcRealDiff());
        }
    }

    /** Runs the checks; any failure throws an AssertionError */
    public static void main(String[] args) throws Exception {
        Random random = new Random(304);
        for (int algorithm = 0; algorithm < 3; algorithm++) {
            for (Comparator<Integer> comparison : Arrays.asList(
                    Comparator.<Integer>naturalOrder(), Comparator.<Integer>reverseOrder())) {
                checkSort(algorithm, new Integer[0], comparison);
                checkSort(algorithm, new Integer[] {7}, comparison);
                checkSort(algorithm, new Integer[] {3, 3, 3}, comparison);
                checkSort(algorithm, new Integer[] {9, 5, 0, -1}, comparison);
                checkSort(algorithm, new Integer[] {-1, 0, 5, 9}, comparison);
                for (int attempt = 0; attempt < 100; attempt++) {
                    Integer[] items = new Integer[random.nextInt(150)];
                    for (int i = 0; i < items.length; i++) items[i] = random.nextInt(41) - 20;
                    checkSort(algorithm, items, comparison);
                }
            }
        }
        Comparator<TestProduct> prices = Comparator.comparingDouble(Product::getOriginalPrice);
        for (int algorithm = 0; algorithm < 3; algorithm++) {
            TestProduct[] items = {new TestProduct(5, 0), new TestProduct(10, 0), new TestProduct(5, 2)};
            if (algorithm == 0) BubbleSort.sort(items, prices);
            if (algorithm == 1) InsertionSort.sort(items, prices);
            if (algorithm == 2) SelectionSort.sort(items, prices);
            require(items[0].getOriginalPrice() == 10, "Generic product sorting");
        }
        for (int size : new int[] {0, 1, 10, 11, 99, 100, 101, 999, 1000, 1001, 2000, 2001, 1000000}) {
            checkOutput(size);
        }
        checkDriver();
        System.out.println("PASS: 630 integer sort cases, generic products, 13 output boundaries, and driver routing");
    }

    /** Compares production sorting with an independent expected ordering */
    private static void checkSort(int algorithm, Integer[] input, Comparator<Integer> comparison) {
        Integer[] expected = input.clone();
        Arrays.sort(expected, comparison.reversed()); // Test oracle only
        Integer[] actual = input.clone();
        if (algorithm == 0) BubbleSort.sort(actual, comparison);
        if (algorithm == 1) InsertionSort.sort(actual, comparison);
        if (algorithm == 2) SelectionSort.sort(actual, comparison);
        require(Arrays.equals(expected, actual), "Sort " + algorithm + " failed");
    }

    /** Verifies sampled one-based positions and all required product fields */
    private static void checkOutput(int size) {
        Product[] products = new Product[size];
        Arrays.fill(products, new TestProduct(20, 10));
        PrintStream original = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(captured));
            ResultPrinter.print(products);
        } finally { System.setOut(original); }
        String output = captured.toString();
        if (size == 0) {
            require(output.contains("No products"), "Empty output");
            return;
        }
        String[] lines = output.strip().split("\\R");
        int interval = size <= 1000 ? 100 : 1000;
        int expectedCount = size <= 10 ? size : 2 + (size - 1) / interval;
        require(lines.length == expectedCount + 1, "Row count for " + size);
        for (int row = 1; row < lines.length; row++) {
            String[] fields = lines[row].strip().split("\\s+");
            int expectedPosition = size <= 10 ? row : row == 1 ? 1
                    : row == lines.length - 1 ? size : (row - 1) * interval;
            require(Integer.parseInt(fields[0]) == expectedPosition, "Position for " + size);
            require(fields[1].equals("TestProduct") && fields[2].equals("20.00")
                    && fields[3].equals("10.00") && fields[4].equals("5.0"), "Product fields");
        }
    }

    /** Checks dispatch without implementing unfinished production dependencies */
    @SuppressWarnings("unchecked")
    private static void checkDriver() throws Exception {
        Method chooseComparison = AppDriver.class.getDeclaredMethod("chooseComparison", char.class);
        chooseComparison.setAccessible(true);
        require(chooseComparison.invoke(null, 'o').getClass().getSimpleName().equals("OriginalPriceComparator"), "Original route");
        require(chooseComparison.invoke(null, 'u').getClass().getSimpleName().equals("UpdatedPriceComparator"), "Updated route");
        Comparator<Product> comparison = (Comparator<Product>) chooseComparison.invoke(null, 'd');
        Method chooseSort = AppDriver.class.getDeclaredMethod("chooseSort", char.class, Product[].class, Comparator.class);
        chooseSort.setAccessible(true);
        for (char algorithm : new char[] {'b', 'i', 's', 'm', 'q', 'z'}) {
            Product[] items = {new TestProduct(4, 0), new TestProduct(10, 0)};
            Runnable task = (Runnable) chooseSort.invoke(null, algorithm, items, comparison);
            if (algorithm == 'b' || algorithm == 'i' || algorithm == 's' || algorithm == 'z') {
                task.run();
                require(items[0].calcRealDiff() == 5, "Difference route");
            } else {
                try { task.run(); throw new AssertionError("Expected unfinished component"); }
                catch (UnsupportedOperationException expected) {
                    String name = algorithm == 'm' ? "Merge" : algorithm == 'q' ? "Quick" : "Heap";
                    require(expected.getMessage().contains(name), "Unfinished sort route");
                }
            }
        }
        PrintStream original = System.err;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        try {
            System.setErr(new PrintStream(captured));
            AppDriver.main(new String[] {"-fres/products1.txt", "-tx", "-sb"});
        } finally { System.setErr(original); }
        require(captured.toString().contains("Invalid input:"), "Invalid argument message");
    }

    /** Stops the checks with a useful failure message */
    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
