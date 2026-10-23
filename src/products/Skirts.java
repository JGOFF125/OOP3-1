package products;

/** I keep the prices and adjustment for Skirts here */
public class Skirts extends Product {
    /** I use this adjustment for the price difference */
    private static final double ADJUSTMENT = 0.1;

    /**
     * I store both prices for this product
     * @param originalPrice original price
     * @param updatedPrice updated price
     */
    public Skirts(double originalPrice, double updatedPrice) {
        super(originalPrice, updatedPrice);
    }

    /**
     * I multiply the absolute price difference by this product adjustment
     * @return adjusted absolute price difference
     */
    @Override
    public double calcRealDiff() {
        // I apply the 0.1 adjustment
        return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
    }
}
