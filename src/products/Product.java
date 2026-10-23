package products;

/** I keep both prices here and compare products by their calculated difference */
public abstract class Product implements Comparable<Product> {
    /** I store the original price */
    private final double originalPrice;
    /** I store the updated price */
    private final double updatedPrice;

    /**
     * I check both prices before storing them
     * @param originalPrice original price
     * @param updatedPrice updated price
     */
    public Product(double originalPrice, double updatedPrice) {
        if (!Double.isFinite(originalPrice) || !Double.isFinite(updatedPrice)
                || originalPrice < 0 || updatedPrice < 0) {
            throw new IllegalArgumentException("Prices must be finite, non-negative numbers.");
        }
        this.originalPrice = originalPrice;
        this.updatedPrice = updatedPrice;
    }

    /**
     * I return the original price
     * @return original price
     */
    public double getOriginalPrice() { return originalPrice; }

    /**
     * I return the updated price
     * @return updated price
     */
    public double getUpdatedPrice() { return updatedPrice; }

    /**
     * I let each product type calculate its adjusted difference
     * @return adjusted absolute difference between the prices
     */
    public abstract double calcRealDiff();

    /**
     * I compare the calculated differences with the normal Comparable result
     * @param other product to compare
     * @return negative, zero, or positive comparison result
     */
    @Override
    public int compareTo(Product other) {
        // I compare the calculated differences
    	if (other == null) {
    		throw new NullPointerException("Product cannot be null.");
    	}

    	return Double.compare(this.calcRealDiff(), other.calcRealDiff());
    }
}
