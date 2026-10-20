package utilities;

import java.util.Locale;
import products.Product;

/** Displays the required sample of a sorted product array */
public class ResultPrinter {
    /**
     * Prints all rows up to 10, otherwise first, last, and interval rows
     * Positions are one-based: every 100th row up to 1,000, then every 1,000th
     * @param items sorted product array
     */
    public static void print(Product[] items) {
        if (items.length == 0) {
            System.out.println("No products to display.");
            return;
        }
        System.out.printf("%8s %-12s %16s %16s %s%n",
                "Row", "Product", "Original price", "Updated price", "Real difference");
        if (items.length <= 10) {
            for (int index = 0; index < items.length; index++) {
                printRow(items, index);
            }
            return;
        }
        printRow(items, 0);
        int interval = items.length <= 1000 ? 100 : 1000;
        for (int position = interval; position < items.length; position += interval) {
            printRow(items, position - 1);
        }
        printRow(items, items.length - 1);
    }

    /**
     * Displays one product with its position and all three comparison values
     * @param items sorted product array
     * @param index zero-based index of the row
     */
    private static void printRow(Product[] items, int index) {
        Product product = items[index];
        System.out.printf(Locale.ROOT, "%8d %-12s %16.2f %16.2f %s%n",
                index + 1, product.getClass().getSimpleName(),
                product.getOriginalPrice(), product.getUpdatedPrice(),
                Double.toString(product.calcRealDiff()));
    }
}
