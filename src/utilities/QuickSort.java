package utilities;

import java.util.Comparator;
import products.Product;

/**
 * I use Quick Sort with a Median-of-3 pivot for descending order
 */
public class QuickSort {


    /**
     * I sort directly from largest to smallest
     *
     * @param items      product array to rearrange
     * @param comparison comparator to determine the relative order of products
     */
    public static void sort(Product[] items, Comparator<Product> comparison) {
        // I stop if there is nothing to sort
        if (items == null || items.length <= 1) {
            return;
        }

        int low = 0;
        int high = items.length - 1;

        // I keep the bounds of sections that still need sorting
        int[] arr = new int[items.length];
        int top = -1;

        // I start with the whole array
        arr[top + 1] = low;
        arr[top + 2] = high;
        top = top + 2;

        // I keep going until no sections remain
        while (top >= 0) {

            // I take the bounds of the next section
            high = arr[top];
            low = arr[top - 1];
            top = top - 2;

            // I partition this section and get the pivot position
            int pivotIndex = splitSection(items, low, high, comparison);

            // I save the left section if it still needs sorting
            if (pivotIndex - 1 > low) {
                arr[top + 1] = low;
                arr[top + 2] = pivotIndex - 1;
                top = top + 2;
            }

            // I save the right section if it still needs sorting
            if (pivotIndex + 1 < high) {
                arr[top + 1] = pivotIndex + 1;
                arr[top + 2] = high;
                top = top + 2;
            }
        }
    }

    /**
     * I partition the section with larger products on the left
     * I choose the pivot from the first, middle, and last products
     * @param items product array to partition
     * @param low first index of the section
     * @param high last index of the section
     * @param comparison normal product comparison
     * @return final index of the Median-of-3 pivot
     */
    private static int splitSection(Product[] items, int low, int high, Comparator<Product> comparison) {
        // I handle a two-product section directly
        if (high - low < 2) {
            if (comparison.compare(items[low], items[high]) < 0) {
                swap(items, low, high);
            }
            return low;
        }

        int mid = low + (high - low) / 2;

        // I order the first, middle, and last products to find their median
        if (comparison.compare(items[low], items[mid]) < 0) {
            swap(items, low, mid);
        }
        if (comparison.compare(items[low], items[high]) < 0) {
            swap(items, low, high);
        }
        if (comparison.compare(items[mid], items[high]) < 0) {
            swap(items, mid, high);
        }

        // I park the median pivot next to the last product
        Product pivot = items[mid];
        swap(items, mid, high - 1);

        int i = low;
        int j = high - 1;

        // I move larger products left and smaller products right
        while (true) {
            i = i + 1;
            while (comparison.compare(items[i], pivot) > 0) {
                i = i + 1;
            }

            j = j - 1;
            while (comparison.compare(items[j], pivot) < 0) {
                j = j - 1;
            }

            if (i >= j) {
                break;
            }
            swap(items, i, j);
        }

        // I put the pivot in its final position
        swap(items, i, high - 1);
        return i;
    }

    /**
     * I swap the products at these two indexes
     * @param items array containing the products
     * @param i first position
     * @param j second position
     */
    private static void swap(Product[] items, int i, int j) {
        Product temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }
}
