package products;

import java.util.Comparator;

/** I compare the original prices */
public class OriginalPriceComparator implements Comparator<Product> {

    /**
     * I return the normal comparison result and leave descending order to the sort
     * @param first first product
     * @param second second product
     * @return negative, zero, or positive comparison result
     */
    @Override
    public int compare(Product first, Product second) {
        // I compare the original prices
    	if (first == null || second == null) {
    		throw new NullPointerException("Prodcuts cannot be null.");
    	}

    	return Double.compare(
    			first.getOriginalPrice(),
    			second.getOriginalPrice()
		);

    }
}
