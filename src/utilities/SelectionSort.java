package utilities;

import java.util.Comparator;

/** Rearranges an array in descending order using selection sort */
public class SelectionSort {
    /**
     * Sorts in place using the normal comparator contract, without timing or output
     * @param <T> type of array elements
     * @param items array to rearrange
     * @param comparison comparison used to determine which element is larger
     */
    public static <T> void sort(T[] items, Comparator<? super T> comparison) {
        for (int index = 0; index < items.length - 1; index++) {
            int largest = index;
            for (int candidate = index + 1; candidate < items.length; candidate++) {
                if (comparison.compare(items[candidate], items[largest]) > 0) {
                    largest = candidate;
                }
            }
            if (largest != index) {
                T temp = items[index];
                items[index] = items[largest];
                items[largest] = temp;
            }
        }
    }
}
