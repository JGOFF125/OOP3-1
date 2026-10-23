package products;

import java.util.Comparator;

/**  Member 1 completes this class */
public class OriginalPriceComparator implements Comparator<Product> {
    /**
     * Uses the normal Comparator contract; sorts handle descending order.
     * @param first first product
     * @param second second product
     * @return negative, zero, or positive comparison result
     */
    @Override
    public int compare(Product first, Product second) {
        // Member 1: compare the original prices
    	if (first == null || second == null) {
    		throw new NullPointerException("Prodcuts cannot be null.");
    	}
    	
    	return Double.compare(
    			first.getOriginalPrice(),
    			second.getOriginalPrice()
		);
    	
    }
}
