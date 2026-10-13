package products;

/**  Member 1 implements the adjustments */
public class Skirts extends Product {
    /**
     * Creates this product.
     * @param originalPrice original price
     * @param updatedPrice updated price
     */
    public Skirts(double originalPrice, double updatedPrice) {
        super(originalPrice, updatedPrice);
    }

    /** @return adjusted absolute price difference */
    @Override
    public double calcRealDiff() {
        //Member 1: apply the 0.1 adjustment
        throw new UnsupportedOperationException("Skirts difference is not implemented yet.");
    }
}
