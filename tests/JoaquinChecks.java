import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import appDomain.AppDriver;
import products.Product;
import products.Jeans;
import products.OriginalPriceComparator;
import products.UpdatedPriceComparator;
import utilities.BubbleSort;
import utilities.InsertionSort;
import utilities.ResultPrinter;
import utilities.SelectionSort;

/** I check the sorts, printed rows, and application routing */
public class JoaquinChecks {
    /** I use this product for output boundary checks */
    private static class TestProduct extends Product {
        /** I set both prices for this test product */
        TestProduct(double original, double updated) { super(original, updated); }
        /** I calculate the difference for the output checks */
        @Override public double calcRealDiff() {
            return 0.5 * Math.abs(getOriginalPrice() - getUpdatedPrice());
        }

    }

    /** I stop at the first failed check */
    public static void main(String[] args) throws Exception {
        Random random = new Random(304);
        for (int algorithm = 0; algorithm < 3; algorithm++) {
            for (Comparator<Product> comparison : Arrays.asList(new OriginalPriceComparator(),
                    new UpdatedPriceComparator(), Comparator.<Product>naturalOrder())) {
                checkSort(algorithm, new Product[0], comparison);
                checkSort(algorithm, new Product[] {new Jeans(7, 2)}, comparison);
                for (int attempt = 0; attempt < 100; attempt++) {
                    Product[] items = new Product[random.nextInt(150)];
                    for (int i = 0; i < items.length; i++) {
                        items[i] = new Jeans(random.nextInt(41), random.nextInt(41));
                    }
                    checkSort(algorithm, items, comparison);
                }
            }
        }
        for (int size : new int[] {0, 1, 10, 11, 99, 100, 101, 999, 1000, 1001, 2000, 2001, 1000000}) {
            checkOutput(size);
        }
        checkDriver();
        System.out.println("PASS: Product sorting, 13 output boundaries, and all six driver routes");
    }

    /** I compare each sorted value with the expected descending order */
    private static void checkSort(int algorithm, Product[] input, Comparator<Product> comparison) {
        Product[] expected = input.clone();
        Arrays.sort(expected, comparison.reversed()); // I use this only to get the expected order
        Product[] actual = input.clone();
        if (algorithm == 0) BubbleSort.sort(actual, comparison);
        if (algorithm == 1) InsertionSort.sort(actual, comparison);
        if (algorithm == 2) SelectionSort.sort(actual, comparison);
        for (int i = 0; i < actual.length; i++) {
            require(comparison.compare(expected[i], actual[i]) == 0, "Sort " + algorithm + " failed");
        }
    }

    /** I check the printed row positions and product values */
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

    /** I check that each option runs the right sort */
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
            task.run();
            require(items[0].calcRealDiff() == 5, "Difference route");
        }
        PrintStream original = System.err;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        try {
            System.setErr(new PrintStream(captured));
            AppDriver.main(new String[] {"-fres/products1.txt", "-tx", "-sb"});
        } finally { System.setErr(original); }
        require(captured.toString().contains("Invalid input:"), "Invalid argument message");
    }

    /** I stop with a message if a check fails */
    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
