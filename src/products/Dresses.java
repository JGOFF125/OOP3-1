package products;

/**  Member 1 implements the adjustment */
public class Dresses extends Product {
    /**
     * Creates this product.
     * @param originalPrice original price
     * @param updatedPrice updated price
     */
    public Dresses(double originalPrice, double updatedPrice) {
        super(originalPrice, updatedPrice);
    }

    /** @return adjusted absolute price difference */
    @Override
    public double calcRealDiff() {
        // Member 1: apply the 0.6 adjustment
        throw new UnsupportedOperationException("Dresses difference is not implemented yet.");
    }
}
