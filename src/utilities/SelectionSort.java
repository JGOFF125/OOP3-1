package utilities;

import java.util.Comparator;
import products.Product;

/** I select the largest remaining product for each position */
public class SelectionSort {

    /**
     * I put larger values first without changing the comparator result
     * @param items array to rearrange
     * @param comparison comparison used to determine which element is larger
     */
    public static void sort(Product[] items, Comparator<Product> comparison) {
        for (int index = 0; index < items.length - 1; index++) {
            int largest = index;
            for (int candidate = index + 1; candidate < items.length; candidate++) {
                if (comparison.compare(items[candidate], items[largest]) > 0) {
                    largest = candidate;
                }
            }
            if (largest != index) {
                Product temp = items[index];
                items[index] = items[largest];
                items[largest] = temp;
            }
        }
    }
}
