package products;

/** TShirts product. Member 1 implements the adjustment of 0.2. */
public class TShirts extends Product {
    /**
     * Creates this product.
     * @param originalPrice original price
     * @param updatedPrice updated price
     */
    public TShirts(double originalPrice, double updatedPrice) {
        super(originalPrice, updatedPrice);
    }

    /** @return adjusted absolute price difference */
    @Override
    public double calcRealDiff() {
        // TODO Member 1: apply the 0.2 adjustment.
        throw new UnsupportedOperationException("TShirts difference is not implemented yet.");
    }
}
