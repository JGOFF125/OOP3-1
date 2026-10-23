package utilities;

import java.util.Comparator;
import products.Product;

/** I use bubble sort to put larger products first */
public class BubbleSort {

    /**
     * I put larger values first without changing the comparator result
     * @param items array to rearrange
     * @param comparison comparison used to determine which element is larger
     */
    public static void sort(Product[] items, Comparator<Product> comparison) {
        for (int end = items.length - 1; end > 0; end--) {
            boolean swapped = false;
            for (int index = 0; index < end; index++) {
                if (comparison.compare(items[index], items[index + 1]) < 0) {
                    Product temp = items[index];
                    items[index] = items[index + 1];
                    items[index + 1] = temp;
                    swapped = true;
                }
            }
            // I stop early if nothing moved
            if (!swapped) {
                break;
            }
        }
    }
}
