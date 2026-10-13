package products;

/** Shorts product. Member 1 implements the adjustment of 0.3. */
public class Shorts extends Product {
    /**
     * Creates this product.
     * @param originalPrice original price
     * @param updatedPrice updated price
     */
    public Shorts(double originalPrice, double updatedPrice) {
        super(originalPrice, updatedPrice);
    }

    /** @return adjusted absolute price difference */
    @Override
    public double calcRealDiff() {
        // TODO Member 1: apply the 0.3 adjustment.
        throw new UnsupportedOperationException("Shorts difference is not implemented yet.");
    }
}
