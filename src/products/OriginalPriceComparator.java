package products;

import java.util.Comparator;

/** Compares original prices. Member 1 completes this class. */
public class OriginalPriceComparator implements Comparator<Product> {
    /**
     * Uses the normal Comparator contract; sorts handle descending order.
     * @param first first product
     * @param second second product
     * @return negative, zero, or positive comparison result
     */
    @Override
    public int compare(Product first, Product second) {
        // TODO Member 1: compare the original prices.
        throw new UnsupportedOperationException("Original price comparison is not implemented yet.");
    }
}
