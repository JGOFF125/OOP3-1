import java.io.*;
import java.nio.file.*;
import java.util.*;
import products.*;
import utilities.*;

/** I check all six sorts with the actual application components */
public class IntegrationChecks {
    private static int assertions;

    /** I check arguments, reading, products, sorting, and timing */
    public static void main(String[] args) throws Exception {
        checkParser();
        checkReader();
        checkTimer();
        checkComparisons();
        checkSortEdges();
        List<Comparator<Product>> comparisons = List.of(new OriginalPriceComparator(),
                new UpdatedPriceComparator(), Comparator.naturalOrder());
        for (String file : new String[] {"res/products1.txt", "res/products2.txt", "res/products3.txt"}) {
            Product[] original = ProductFileReader.read(file);
            require(original.length == (file.contains("products1") ? 10 : file.contains("products2") ? 1000 : 1000000), "Dataset count");
            for (char algorithm : (original.length > 1000 ? "mqz" : "bismqz").toCharArray()) {
                for (Comparator<Product> cmp : comparisons) {
                    Product[] actual = original.clone();
                    sort(algorithm, actual, cmp);
                    for (int i = 1; i < actual.length; i++) {
                        require(cmp.compare(actual[i - 1], actual[i]) >= 0, "Descending products " + file + " " + algorithm);
                    }
                    IdentityHashMap<Product, Integer> counts = new IdentityHashMap<>();
                    for (Product p : original) counts.merge(p, 1, Integer::sum);
                    for (Product p : actual) {
                        Integer count = counts.get(p);
                        require(count != null && count > 0, "Product preservation");
                        if (count == 1) counts.remove(p); else counts.put(p, count - 1);
                    }
                    require(counts.isEmpty(), "No lost products");
                }
            }
            System.out.println("PASS: " + file + " (all comparisons; " + (original.length > 1000 ? "Merge/Quick/Heap" : "all six algorithms") + ")");
        }
        System.out.println("PASS: " + assertions + " integration assertions");
    }

    /** I check the comparison signs before testing any sort */
    private static void checkComparisons() {
        Product smaller = new Jeans(10, 8);
        Product larger = new Shoes(30, 20);
        for (Comparator<Product> cmp : List.of(new OriginalPriceComparator(),
                new UpdatedPriceComparator(), Comparator.<Product>naturalOrder())) {
            require(cmp.compare(smaller, larger) < 0, "Normal negative comparison");
            require(cmp.compare(larger, smaller) > 0, "Normal positive comparison");
            require(cmp.compare(smaller, smaller) == 0, "Equal comparison");
        }
        require(smaller.compareTo(larger) < 0, "Comparable difference contract");
        require(new Jeans(10, 4).calcRealDiff() == 3, "Jeans difference");
        require(new TShirts(10, 5).calcRealDiff() == 1, "TShirts difference");
        require(new Skirts(10, 0).calcRealDiff() == 1, "Skirts difference");
        require(new Dresses(10, 5).calcRealDiff() == 3, "Dresses difference");
        require(new Shorts(10, 0).calcRealDiff() == 3, "Shorts difference");
        require(new Jackets(10, 0).calcRealDiff() == 7, "Jackets difference");
        require(new Shoes(10, 0).calcRealDiff() == 4, "Shoes difference");
    }

    /** I check duplicates, repeated objects, and arrays in both directions */
    private static void checkSortEdges() {
        Random random = new Random(304);
        Product repeated = new Jeans(10, 5);
        Product[][] edges = {
            {}, {repeated}, {repeated, repeated, repeated},
            {new Jeans(5, 1), new Shoes(5, 1), new Jackets(5, 8)},
            {new Jeans(1, 0), new Jeans(2, 0), new Jeans(3, 0)},
            {new Jeans(3, 0), new Jeans(2, 0), new Jeans(1, 0)}
        };
        for (char algorithm : "bismqz".toCharArray()) {
            for (Comparator<Product> cmp : List.of(new OriginalPriceComparator(),
                    new UpdatedPriceComparator(), Comparator.<Product>naturalOrder())) {
                for (Product[] edge : edges) checkProductSort(algorithm, edge, cmp);
                for (int size = 0; size <= 150; size++) {
                    Product[] input = new Product[size];
                    for (int i = 0; i < size; i++) {
                        input[i] = i % 2 == 0 ? new Jeans(random.nextInt(20), random.nextInt(20))
                                : new Shoes(random.nextInt(20), random.nextInt(20));
                    }
                    checkProductSort(algorithm, input, cmp);
                }
            }
        }
    }

    /** I check the values and make sure no products were lost */
    private static void checkProductSort(char algorithm, Product[] input, Comparator<Product> cmp) {
        Product[] expected = input.clone();
        Arrays.sort(expected, cmp.reversed()); // I use this only to get the expected order
        Product[] actual = input.clone();
        sort(algorithm, actual, cmp);
        IdentityHashMap<Product, Integer> counts = new IdentityHashMap<>();
        for (Product item : input) counts.put(item, counts.getOrDefault(item, 0) + 1);
        for (int i = 0; i < actual.length; i++) {
            require(cmp.compare(actual[i], expected[i]) == 0, "Expected sorted value " + algorithm);
            int count = counts.getOrDefault(actual[i], 0);
            require(count > 0, "Preserved product occurrence " + algorithm);
            if (count == 1) counts.remove(actual[i]); else counts.put(actual[i], count - 1);
        }
        require(counts.isEmpty(), "No missing products " + algorithm);
    }

    /** I run the sort selected by its option letter */
    private static void sort(char algorithm, Product[] items, Comparator<Product> cmp) {
        switch (algorithm) {
            case 'b' -> BubbleSort.sort(items, cmp);
            case 'i' -> InsertionSort.sort(items, cmp);
            case 's' -> SelectionSort.sort(items, cmp);
            case 'm' -> MergeSort.sort(items, cmp);
            case 'q' -> QuickSort.sort(items, cmp);
            case 'z' -> HeapSort.sort(items, cmp);
            default -> throw new AssertionError("Unexpected algorithm");
        }
    }

    /** I check argument order, mixed case, quotes, and invalid options */
    private static void checkParser() {
        int[][] orders = {{0,1,2},{0,2,1},{1,0,2},{1,2,0},{2,0,1},{2,1,0}};
        for (char type : "oud".toCharArray()) {
            for (char algorithm : "bsimqz".toCharArray()) {
                String[] options = {"-FC:/Test Folder/Products.TXT", "-T" + Character.toUpperCase(type), "-S" + Character.toUpperCase(algorithm)};
                for (int[] order : orders) {
                    CommandLineOptions parsed = CommandLineOptions.parse(new String[] {options[order[0]],options[order[1]],options[order[2]]});
                    require(parsed.getFilePath().equals("C:/Test Folder/Products.TXT") && parsed.getCompareType() == type && parsed.getSortType() == algorithm, "Parser case/order");
                }
            }
        }
        require(CommandLineOptions.parse(new String[] {"-f\"a b.txt\"", "-to", "–sz"}).getFilePath().equals("a b.txt"), "Quoted path");
        String[][] invalid = {{},{"-fa"},{"-fa","-to"},{"-f","-to","-sb"},{"-fa","-tx","-sb"},{"-fa","-to","-sy"},{"-fa","-to","-sb","-si"},{"-fa","-to","-sb","-x1"},{"-fa","-t","o","-sb"},{"-fa","-to","-sb","-fb"}};
        for (String[] input : invalid) expect(IllegalArgumentException.class, () -> CommandLineOptions.parse(input));
    }

    /** I check bad rows, counts, prices, and missing files */
    private static void checkReader() throws IOException {
        Path file = Files.createTempFile("oop reader ", ".txt");
        try {
            Files.writeString(file, "2\nJeans 10 20\nShoes 20 10\n");
            Product[] items = ProductFileReader.read(file.toString());
            require(items.length == 2 && items[0] instanceof Jeans && items[1] instanceof Shoes, "Reader types");
            for (String invalid : new String[] {"", "abc\n", "-1\n", "1\n", "1\nJeans 1 2\nShoes 3 4\n", "1\nUnknown 1 2\n", "1\nJeans 1\n", "1\nJeans abc 2\n", "1\nJeans NaN 2\n", "1\nJeans 1 Infinity\n", "1\nJeans -1 2\n"}) {
                Files.writeString(file, invalid);
                expect(IllegalArgumentException.class, () -> ProductFileReader.read(file.toString()));
            }
        } finally { Files.deleteIfExists(file); }
        expect(IOException.class, () -> ProductFileReader.read(file.toString()));
        for (double bad : new double[] {-1, Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY}) {
            expect(IllegalArgumentException.class, () -> new Jeans(bad, 1));
            expect(IllegalArgumentException.class, () -> new Jeans(1, bad));
        }
    }

    /** I check that the timer runs the task once and keeps its exceptions */
    private static void checkTimer() {
        int[] calls = {0};
        long elapsed = BenchmarkRunner.measureNanos(() -> calls[0]++);
        require(calls[0] == 1 && elapsed >= 0, "Timer execution");
        expect(IllegalStateException.class, () -> BenchmarkRunner.measureNanos(() -> { throw new IllegalStateException("test"); }));
    }

    /** I check that an operation throws the expected exception */
    private static void expect(Class<? extends Throwable> type, CheckedAction action) {
        try { action.run(); } catch (Throwable error) {
            require(type.isInstance(error), "Expected " + type + ", got " + error);
            return;
        }
        throw new AssertionError("Missing exception " + type);
    }

    /** I use this for checks that can throw an exception */
    @FunctionalInterface private interface CheckedAction { void run() throws Exception; }

    /** I count each passed check and stop on a failure */
    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
        assertions++;
    }
}
