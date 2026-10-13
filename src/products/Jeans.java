package products;

/** Jeans product. Member 1 implements the adjustment of 0.5. */
public class Jeans extends Product {
    /**
     * Creates this product.
     * @param originalPrice original price
     * @param updatedPrice updated price
     */
    public Jeans(double originalPrice, double updatedPrice) {
        super(originalPrice, updatedPrice);
    }

    /** @return adjusted absolute price difference */
    @Override
    public double calcRealDiff() {
        // TODO Member 1: apply the 0.5 adjustment.
        throw new UnsupportedOperationException("Jeans difference is not implemented yet.");
    }
}
