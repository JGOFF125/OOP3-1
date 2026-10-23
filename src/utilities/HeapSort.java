package utilities;

import java.util.Comparator;
import products.Product;
import java.util.Objects;

/**
 * I use a min-heap to sort from largest to smallest
 * I move the smallest product to the end on each pass
 */
public class HeapSort {


    /**
     * I sort directly from largest to smallest
     *
     * @param items array to rearrange
     * @param comparison normal comparison of two elements
     * @throws NullPointerException if items or comparison is null
     */
    public static void sort(Product[] items, Comparator<Product> comparison) {
        Objects.requireNonNull(items, "The array to sort must not be null.");
        Objects.requireNonNull(comparison, "The comparator must not be null.");

        int size = items.length;

        for (int parent = size / 2 - 1; parent >= 0; parent--) {
            siftDown(items, parent, size, comparison);
        }

        for (int last = size - 1; last > 0; last--) {
            swap(items, 0, last);
            siftDown(items, 0, last, comparison);
        }
    }

    /**
     * I move the product down until neither child is smaller
     *
     * @param items array holding the heap
     * @param root index of the element to move down
     * @param size number of array positions that belong to the heap
     * @param comparison normal comparison of two elements
     */
    private static void siftDown(Product[] items, int root, int size,
            Comparator<Product> comparison) {
        while (true) {
            int left = 2 * root + 1;
            if (left >= size) {
                return;
            }

            int smallest = left;
            int right = left + 1;
            if (right < size && comparison.compare(items[right], items[left]) < 0) {
                smallest = right;
            }

            if (comparison.compare(items[smallest], items[root]) >= 0) {
                return;
            }

            swap(items, root, smallest);
            root = smallest;
        }
    }

    /**
     * I swap the products at the two positions
     *
     * @param items array containing the elements
     * @param first index of the first element
     * @param second index of the second element
     */
    private static void swap(Product[] items, int first, int second) {
        Product temp = items[first];
        items[first] = items[second];
        items[second] = temp;
    }
}
