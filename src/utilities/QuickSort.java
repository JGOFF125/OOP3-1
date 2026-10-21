package utilities;

import java.util.Comparator;
import products.Product;

/**
 * Sorts product arrays from largest to smallest using an Quick Sort with Median of 3 pivot selection.
 */
public class QuickSort {

    /**
     * Sorts the array directly from largest to smallest.
     * completed by Adeeb
     * 
     * @param items      product array to rearrange
     * @param comparison comparator to determine the relative order of products
     */
    public static void sort(Product[] items, Comparator<Product> comparison) {
        //Stop if the array is empty or already has only 1 item
        if (items == null || items.length <= 1) {
            return;
        }

        int low = 0;
        int high = items.length - 1;

        //An array to store start and end positions for sections that need sorting
        int[] arr = new int[items.length];
        int top = -1;

        //Store the starting start and end bounds of the main array
        arr[top + 1] = low;
        arr[top + 2] = high;
        top = top + 2;

        //Keep sorting sections until there are no remaining sections left
        while (top >= 0) {

            //Take out the next start and end points to work on
            high = arr[top];
            low = arr[top - 1];
            top = top - 2;

            //split the current section and get the position of the middle separator item
            int pivotIndex = splitSection(items, low, high, comparison);

            //If there are items left to sort on the left side, save that section
            if (pivotIndex - 1 > low) {
                arr[top + 1] = low;
                arr[top + 2] = pivotIndex - 1;
                top = top + 2;
            }

            //If there are items left to sort on the right side, save that section
            if (pivotIndex + 1 < high) {
                arr[top + 1] = pivotIndex + 1;
                arr[top + 2] = high;
                top = top + 2;
            }
        }
    }

    /**
     * Rearranges items in a section so larger items move to the left side 
     * and smaller items move to the right side around a middle benchmark item.
     */
    private static int splitSection(Product[] items, int low, int high, Comparator<Product> comparison) {
        //if not enough sections just compare
        if (high - low < 2) {
            if (comparison.compare(items[low], items[high]) < 0) {
                swap(items, low, high);
            }
            return low;
        }

        int mid = low + (high - low) / 2;

        //Put the start, middle, and end items in order from largest to smallest
        if (comparison.compare(items[low], items[mid]) < 0) {
            swap(items, low, mid);
        }
        if (comparison.compare(items[low], items[high]) < 0) {
            swap(items, low, high);
        }
        if (comparison.compare(items[mid], items[high]) < 0) {
            swap(items, mid, high);
        }

        //Move the middle benchmark item out of the way
        Product pivot = items[mid];
        swap(items, mid, high - 1);

        int i = low;
        int j = high - 1;

        //Move larger items to the left and smaller items to the right
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

        //Place benchmark item in its correct middle position
        swap(items, i, high - 1);
        return i;
    }

    //Helper method to swap two products in the array by their index positions.
    private static void swap(Product[] items, int i, int j) {
        Product temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }
}