package products;

/** Skirts product. Member 1 implements the adjustment of 0.1. */
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
        // TODO Member 1: apply the 0.1 adjustment.
        throw new UnsupportedOperationException("Skirts difference is not implemented yet.");
    }
}
