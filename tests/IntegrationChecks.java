import java.io.*;
import java.nio.file.*;
import java.util.*;
import products.*;
import utilities.*;

/** Checks the integrated components without completing Merge or Quick Sort */
public class IntegrationChecks {
    private static int assertions;

    /** Runs parser, reader, product, sort, and timer checks */
    public static void main(String[] args) throws Exception {
        checkParser();
        checkReader();
        checkTimer();
        Random random = new Random(304);
        for (int n : new int[] {0, 1, 2, 3, 10, 100, 1000}) {
            Integer[] input = new Integer[n];
            for (int i = 0; i < n; i++) input[i] = random.nextInt(20) - 10;
            for (char algorithm : "bisz".toCharArray()) {
                for (Comparator<Integer> cmp : List.of(Comparator.<Integer>naturalOrder(), Comparator.<Integer>reverseOrder())) {
                    Integer[] expected = input.clone(), actual = input.clone();
                    Arrays.sort(expected, cmp.reversed());
                    sort(algorithm, actual, cmp);
                    require(Arrays.equals(actual, expected), "Integer sorting " + algorithm);
                }
            }
        }
        List<Comparator<Product>> comparisons = List.of(new OriginalPriceComparator(),
                new UpdatedPriceComparator(), Comparator.naturalOrder());
        for (String file : new String[] {"res/products1.txt", "res/products2.txt", "res/products3.txt"}) {
            Product[] original = ProductFileReader.read(file);
            require(original.length == (file.contains("products1") ? 10 : file.contains("products2") ? 1000 : 1000000), "Dataset count");
            for (char algorithm : (original.length > 1000 ? "z" : "bisz").toCharArray()) {
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
            System.out.println("PASS: " + file + " (all comparisons; " + (original.length > 1000 ? "Heap" : "Bubble/Insertion/Selection/Heap") + ")");
        }
        System.out.println("PASS: " + assertions + " integration assertions");
    }

    /** Dispatches only the algorithms supplied in the three archives */
    private static <T> void sort(char algorithm, T[] items, Comparator<? super T> cmp) {
        switch (algorithm) {
            case 'b' -> BubbleSort.sort(items, cmp);
            case 'i' -> InsertionSort.sort(items, cmp);
            case 's' -> SelectionSort.sort(items, cmp);
            case 'z' -> HeapSort.sort(items, cmp);
            default -> throw new AssertionError("Unexpected algorithm");
        }
    }

    /** Checks all argument orders, mixed case, quoting, and invalid options */
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

    /** Checks row errors, count mismatches, price validation, and missing files */
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

    /** Ensures the timer runs once and preserves failures from the task */
    private static void checkTimer() {
        int[] calls = {0};
        long elapsed = BenchmarkRunner.measureNanos(() -> calls[0]++);
        require(calls[0] == 1 && elapsed >= 0, "Timer execution");
        expect(IllegalStateException.class, () -> BenchmarkRunner.measureNanos(() -> { throw new IllegalStateException("test"); }));
    }

    /** Runs a checked operation expecting a specific exception */
    private static void expect(Class<? extends Throwable> type, CheckedAction action) {
        try { action.run(); } catch (Throwable error) {
            require(type.isInstance(error), "Expected " + type + ", got " + error);
            return;
        }
        throw new AssertionError("Missing exception " + type);
    }

    /** Allows an operation to throw a checked exception */
    @FunctionalInterface private interface CheckedAction { void run() throws Exception; }

    /** Records a successful assertion or stops at the first failure */
    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
        assertions++;
    }
}
