package utilities;

import java.util.Locale;
import products.Product;

/** I print the sorted products at the required positions */
public class ResultPrinter {

    /**
     * I print every product up to 10, otherwise I print the sample rows
     * I count rows from 1 and use intervals of 100 up to 1000, then 1000
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
     * I print the row number, product type, prices, and difference
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
