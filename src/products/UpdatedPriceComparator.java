package products;

import java.util.Comparator;

/** Compares updated prices, Member 1 */
public class UpdatedPriceComparator implements Comparator<Product> {
    /**
     * Uses the normal Comparator contract; sorts handle descending order.
     * @param first first product
     * @param second second product
     * @return negative, zero, or positive comparison result
     */
    @Override
    public int compare(Product first, Product second) {
        //Member 1: compare the updated prices
        throw new UnsupportedOperationException("Updated price comparison is not implemented yet.");
    }
}
