package utilities;

import java.util.Comparator;
import products.Product;

/**
 * I merge sorted sections with the larger products first
 */
public class MergeSort {


    /**
     * I sort directly from largest to smallest
     * @param items      product array to rearrange
     * @param comparison comparator to determine the relative order of products
     */
    public static void sort(Product[] items, Comparator<Product> comparison) {
        // I stop if there is nothing to sort
        if (items == null || items.length <= 1) {
            return;
        }

        int totalItems = items.length;

        // I merge sections of size 1, 2, 4, and so on
        for (int sectionSize = 1; sectionSize < totalItems; sectionSize = 2 * sectionSize) {
            for (int leftStart = 0; leftStart < totalItems - 1; leftStart = leftStart + (2 * sectionSize)) {

                int middle = Math.min(leftStart + sectionSize - 1, totalItems - 1);
                int rightEnd = Math.min(leftStart + (2 * sectionSize) - 1, totalItems - 1);

                combineSections(items, leftStart, middle, rightEnd, comparison);
            }
        }
    }

    /**
     * I combine the two sections and take the larger product first
     * @param items array containing both sections
     * @param left first index in the left section
     * @param middle last index in the left section
     * @param right last index in the right section
     * @param comparison normal product comparison
     */
    private static void combineSections(Product[] items, int left, int middle, int right, Comparator<Product> comparison) {
        int leftLength = middle - left + 1;
        int rightLength = right - middle;

        Product[] leftGroup = new Product[leftLength];
        Product[] rightGroup = new Product[rightLength];

        // I copy both sections into temporary arrays
        for (int i = 0; i < leftLength; i = i + 1) {
            leftGroup[i] = items[left + i];
        }
        for (int j = 0; j < rightLength; j = j + 1) {
            rightGroup[j] = items[middle + 1 + j];
        }

        int leftIndex = 0;
        int rightIndex = 0;
        int mainIndex = left;

        // I put the larger product back into the main array first
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

        // I copy anything left in the first section
        while (leftIndex < leftLength) {
            items[mainIndex] = leftGroup[leftIndex];
            leftIndex = leftIndex + 1;
            mainIndex = mainIndex + 1;
        }

        // I copy anything left in the second section
        while (rightIndex < rightLength) {
            items[mainIndex] = rightGroup[rightIndex];
            rightIndex = rightIndex + 1;
            mainIndex = mainIndex + 1;
        }
    }
}
