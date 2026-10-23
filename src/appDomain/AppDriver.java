package appDomain;

import java.io.IOException;
import java.util.Comparator;
import java.util.Locale;
import products.OriginalPriceComparator;
import products.Product;
import products.UpdatedPriceComparator;
import utilities.BenchmarkRunner;
import utilities.BubbleSort;
import utilities.CommandLineOptions;
import utilities.HeapSort;
import utilities.InsertionSort;
import utilities.MergeSort;
import utilities.ProductFileReader;
import utilities.QuickSort;
import utilities.ResultPrinter;
import utilities.SelectionSort;

/** Connects file input, comparison, sorting, timing, and console output */
public class AppDriver {
    /**
     * Runs the selected algorithm and shows useful messages for expected errors
     * @param args attached file, comparison, and sorting options in any order
     */
    public static void main(String[] args) {
        try {
            CommandLineOptions options = CommandLineOptions.parse(args);
            Comparator<Product> comparison = chooseComparison(options.getCompareType());
            Product[] items = ProductFileReader.read(options.getFilePath());
            Runnable sortingTask = chooseSort(options.getSortType(), items, comparison);

            // Only the sorting call belongs inside the timed section
            long elapsedNanos = BenchmarkRunner.measureNanos(sortingTask);
            System.out.printf(Locale.ROOT,
                    "Sorted %d products using %s (%s), largest to smallest.%n",
                    items.length, sortName(options.getSortType()),
                    comparisonName(options.getCompareType()));
            System.out.printf(Locale.ROOT, "Sorting time: %.3f milliseconds%n",
                    elapsedNanos / 1_000_000.0);
            ResultPrinter.print(items);
        } catch (IllegalArgumentException exception) {
            System.err.println("Invalid input: " + exception.getMessage());
            System.err.println("Usage: java -jar Sort.jar -f<file> -t<o|u|d> -s<b|s|i|m|q|z>");
        } catch (IOException exception) {
            System.err.println("Could not read product data: " + exception.getMessage());
        } catch (UnsupportedOperationException exception) {
            System.err.println("A required component is still unfinished: " + exception.getMessage());
        }
    }

    /**
     * Selects a normal comparison; each sort handles descending order itself
     * @param type o for original, u for updated, or d for adjusted difference
     * @return selected product comparison
     * @throws IllegalArgumentException when the type is unknown
     */
    private static Comparator<Product> chooseComparison(char type) {
        return switch (type) {
            case 'o' -> new OriginalPriceComparator();
            case 'u' -> new UpdatedPriceComparator();
            case 'd' -> Comparator.naturalOrder();
            default -> throw new IllegalArgumentException("Comparison must be o, u, or d.");
        };
    }

    /**
     * Prepares the selected sorting call before the timer starts
     * @param type b, s, i, m, q, or z
     * @param items product array to sort
     * @param comparison normal product comparison
     * @return task which runs the selected algorithm once
     * @throws IllegalArgumentException when the algorithm is unknown
     */
    private static Runnable chooseSort(char type, Product[] items, Comparator<Product> comparison) {
        return switch (type) {
            case 'b' -> () -> BubbleSort.sort(items, comparison);
            case 's' -> () -> SelectionSort.sort(items, comparison);
            case 'i' -> () -> InsertionSort.sort(items, comparison);
            case 'm' -> () -> MergeSort.sort(items, comparison);
            case 'q' -> () -> QuickSort.sort(items, comparison);
            case 'z' -> () -> HeapSort.sort(items, comparison);
            default -> throw new IllegalArgumentException("Sort must be b, s, i, m, q, or z.");
        };
    }

    /**
     * Gives the selected algorithm a readable label for the results
     * @param type sorting option
     * @return algorithm name
     */
    private static String sortName(char type) {
        return switch (type) {
            case 'b' -> "Bubble Sort";
            case 's' -> "Selection Sort";
            case 'i' -> "Insertion Sort";
            case 'm' -> "Merge Sort";
            case 'q' -> "Quick Sort (Median-of-3)";
            case 'z' -> "Heap Sort";
            default -> throw new IllegalArgumentException("Unknown sorting algorithm.");
        };
    }

    /**
     * Gives the selected comparison a readable label for the results
     * @param type comparison option
     * @return compared property name
     */
    private static String comparisonName(char type) {
        return switch (type) {
            case 'o' -> "original price";
            case 'u' -> "updated price";
            case 'd' -> "adjusted difference";
            default -> throw new IllegalArgumentException("Unknown comparison type.");
        };
    }
}
