package utilities;

import java.util.Comparator;
import products.Product;

/** I use insertion sort to put larger products first */
public class InsertionSort {

    /**
     * I put larger values first without changing the comparator result
     * @param items array to rearrange
     * @param comparison comparison used to determine which element is larger
     */
    public static void sort(Product[] items, Comparator<Product> comparison) {
        for (int index = 1; index < items.length; index++) {
            Product current = items[index];
            int previous = index - 1;
            // I move smaller products right to make room
            while (previous >= 0 && comparison.compare(items[previous], current) < 0) {
                items[previous + 1] = items[previous];
                previous--;
            }
            items[previous + 1] = current;
        }
    }
}
