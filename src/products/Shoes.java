package products;

/**  Member 1 implements the adjustment */
public class Shoes extends Product {
    /**
     * Creates this product.
     * @param originalPrice original price
     * @param updatedPrice updated price
     */
    public Shoes(double originalPrice, double updatedPrice) {
        super(originalPrice, updatedPrice);
    }

    /** @return adjusted absolute price difference */
    @Override
    public double calcRealDiff() {
        // Member 1: apply the 0.4 adjustment
        throw new UnsupportedOperationException("Shoes difference is not implemented yet.");
    }
}
