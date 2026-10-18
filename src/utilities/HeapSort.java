package utilities;

import java.util.Comparator;
import java.util.Objects;

/**
 * Heap sort that arranges an array from largest to smallest.
 * It uses a min-heap, and each smallest element is moved to the end of the array.
 */
public class HeapSort {

    /**
     * Sorts the array directly from largest to smallest.
     *
     * @param <T> type of array elements
     * @param items array to rearrange
     * @param comparison normal comparison of two elements
     * @throws NullPointerException if items or comparison is null
     */
    public static <T> void sort(T[] items, Comparator<? super T> comparison) {
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
     * Moves an element down the heap until neither child is smaller than it.
     *
     * @param <T> type of array elements
     * @param items array holding the heap
     * @param root index of the element to move down
     * @param size number of array positions that belong to the heap
     * @param comparison normal comparison of two elements
     */
    private static <T> void siftDown(T[] items, int root, int size,
            Comparator<? super T> comparison) {
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
     * Swaps two elements of an array.
     *
     * @param <T> type of array elements
     * @param items array containing the elements
     * @param first index of the first element
     * @param second index of the second element
     */
    private static <T> void swap(T[] items, int first, int second) {
        T temp = items[first];
        items[first] = items[second];
        items[second] = temp;
    }
}