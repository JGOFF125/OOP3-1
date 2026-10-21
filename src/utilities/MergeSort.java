package utilities;

import java.util.Comparator;
import products.Product;

/**
 * Sorts product arrays from largest to smallest using Merge Sort
 */
public class MergeSort {

    /**
     * Sorts the array directly from largest to smallest.
     * completed by Adeeb
     * @param items      product array to rearrange
     * @param comparison comparator to determine the relative order of products
     */
    public static void sort(Product[] items, Comparator<Product> comparison) {
        //Stop if the array is empty or already has only 1 item
        if (items == null || items.length <= 1) {
            return;
        }

        int totalItems = items.length;

        //Start by merging sections of size 1, then size 2, size 4, and so on
        for (int sectionSize = 1; sectionSize < totalItems; sectionSize = 2 * sectionSize) {
            for (int leftStart = 0; leftStart < totalItems - 1; leftStart = leftStart + (2 * sectionSize)) {
                
                int middle = Math.min(leftStart + sectionSize - 1, totalItems - 1);
                int rightEnd = Math.min(leftStart + (2 * sectionSize) - 1, totalItems - 1);

                combineSections(items, leftStart, middle, rightEnd, comparison);
            }
        }
    }

    //Combines two neighboring sorted sections into one larger sorted section from largest to smallest.
    private static void combineSections(Product[] items, int left, int middle, int right, Comparator<Product> comparison) {
        int leftLength = middle - left + 1;
        int rightLength = right - middle;

        Product[] leftGroup = new Product[leftLength];
        Product[] rightGroup = new Product[rightLength];

        //Copy items into temporary holding arrays
        for (int i = 0; i < leftLength; i = i + 1) {
            leftGroup[i] = items[left + i];
        }
        for (int j = 0; j < rightLength; j = j + 1) {
            rightGroup[j] = items[middle + 1 + j];
        }

        int leftIndex = 0;
        int rightIndex = 0;
        int mainIndex = left;

        //Compare items from both groups and place the larger item into the main array first
        while (leftIndex < leftLength && rightIndex < rightLength) {
            if (comparison.compare(leftGroup[leftIndex], rightGroup[rightIndex]) >= 0) {
                items[mainIndex] = leftGroup[leftIndex];
                leftIndex = leftIndex + 1;
            } else {
                items[mainIndex] = rightGroup[rightIndex];
                rightIndex = rightIndex + 1;
            }
            mainIndex = mainIndex + 1;
        }

        //Copy any leftover items from the left group
        while (leftIndex < leftLength) {
            items[mainIndex] = leftGroup[leftIndex];
            leftIndex = leftIndex + 1;
            mainIndex = mainIndex + 1;
        }

        //Copy any leftover items from the right group
        while (rightIndex < rightLength) {
            items[mainIndex] = rightGroup[rightIndex];
            rightIndex = rightIndex + 1;
            mainIndex = mainIndex + 1;
        }
    }
}