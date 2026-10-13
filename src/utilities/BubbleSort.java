package utilities;

import java.util.Comparator;

/** Rearranges an array in descending order using bubble sort */
public class BubbleSort {
    /**
     * Sorts in place using the normal comparator contract, without timing or output
     * @param <T> type of array elements
     * @param items array to rearrange
     * @param comparison comparison used to determine which element is larger
     */
    public static <T> void sort(T[] items, Comparator<? super T> comparison) {
        for (int end = items.length - 1; end > 0; end--) {
            boolean swapped = false;
            for (int index = 0; index < end; index++) {
                if (comparison.compare(items[index], items[index + 1]) < 0) {
                    T temp = items[index];
                    items[index] = items[index + 1];
                    items[index + 1] = temp;
                    swapped = true;
                }
            }
            // No swaps means the remaining items are already in order
            if (!swapped) {
                break;
            }
        }
    }
}
