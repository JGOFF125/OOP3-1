import java.util.Comparator;
import java.util.Random;
import products.Jeans;
import products.OriginalPriceComparator;
import products.Product;
import utilities.HeapSort;

/** I repeat the Product comparison counts shown in the Heap report */
public class HeapMeasurementChecks {

    /** I count calls to the original price comparator */
    private static class CountingComparison implements Comparator<Product> {
        /** I use the same price comparator as the application */
        private final OriginalPriceComparator prices = new OriginalPriceComparator();
        /** I count comparisons for this run */
        private long calls;

        /** I count one call and return the price comparison */
        @Override
        public int compare(Product first, Product second) {
            calls++;
            return prices.compare(first, second);
        }
    }

    /** I reset the seed for each size and check descending output */
    public static void main(String[] args) {
        System.out.println("n,comparisons,bound,ratio");
        for (int size : new int[] {10, 100, 1000, 10000, 100000, 1000000}) {
            Random random = new Random(304);
            Product[] items = new Product[size];
            for (int i = 0; i < size; i++) {
                items[i] = new Jeans(random.nextInt(1000000), 0);
            }
            CountingComparison comparison = new CountingComparison();
            HeapSort.sort(items, comparison);
            for (int i = 1; i < size; i++) {
                if (items[i - 1].getOriginalPrice() < items[i].getOriginalPrice()) {
                    throw new AssertionError("Heap output is not descending");
                }
            }
            double log = Math.log(size) / Math.log(2);
            long bound = (long) Math.ceil(2 * size + 2 * size * log);
            if (comparison.calls > bound) {
                throw new AssertionError("Comparison bound exceeded");
            }
            System.out.printf(java.util.Locale.ROOT, "%d,%d,%d,%.2f%n",
                    size, comparison.calls, bound, comparison.calls / (size * log));
        }
    }
}
