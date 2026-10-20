package utilities;

import java.util.Comparator;

/** Rearranges an array in descending order using insertion sort */
public class InsertionSort {
    /**
     * Sorts in place using the normal comparator contract, without timing or output
     * @param <T> type of array elements
     * @param items array to rearrange
     * @param comparison comparison used to determine which element is larger
     */
    public static <T> void sort(T[] items, Comparator<? super T> comparison) {
        for (int index = 1; index < items.length; index++) {
            T current = items[index];
            int previous = index - 1;
            // Move smaller items right to make room for the current item
            while (previous >= 0 && comparison.compare(items[previous], current) < 0) {
                items[previous + 1] = items[previous];
                previous--;
            }
            items[previous + 1] = current;
        }
    }
}
