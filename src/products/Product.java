package products;

/** Shared product structure. Member 1 completes comparison and validation. */
public abstract class Product implements Comparable<Product> {
    private final double originalPrice;
    private final double updatedPrice;

    /**
     * Creates a product with its two prices.
     * @param originalPrice original price
     * @param updatedPrice updated price
     */
    public Product(double originalPrice, double updatedPrice) {
        this.originalPrice = originalPrice;
        this.updatedPrice = updatedPrice;
    }

    /** @return original price */
    public double getOriginalPrice() { return originalPrice; }

    /** @return updated price */
    public double getUpdatedPrice() { return updatedPrice; }

    /** @return adjusted absolute difference between the prices */
    public abstract double calcRealDiff();

    /**
     * Compares the adjusted differences using the normal Comparable contract.
     * @param other product to compare
     * @return negative, zero, or positive comparison result
     */
    @Override
    public int compareTo(Product other) {
        // TODO Member 1: compare calcRealDiff() values.
        throw new UnsupportedOperationException("Product comparison is not implemented yet.");
    }
}
