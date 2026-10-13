package products;

/** Jackets product. Member 1 implements the adjustment of 0.7. */
public class Jackets extends Product {
    /**
     * Creates this product.
     * @param originalPrice original price
     * @param updatedPrice updated price
     */
    public Jackets(double originalPrice, double updatedPrice) {
        super(originalPrice, updatedPrice);
    }

    /** @return adjusted absolute price difference */
    @Override
    public double calcRealDiff() {
        // TODO Member 1: apply the 0.7 adjustment.
        throw new UnsupportedOperationException("Jackets difference is not implemented yet.");
    }
}
