package products;

/** I keep the prices and adjustment for Jeans here */
public class Jeans extends Product {
    /** I use this adjustment for the price difference */
    private static final double ADJUSTMENT = 0.5;

    /**
     * I store both prices for this product
     * @param originalPrice original price
     * @param updatedPrice updated price
     */
    public Jeans(double originalPrice, double updatedPrice) {
        super(originalPrice, updatedPrice);
    }

    /**
     * I multiply the absolute price difference by this product adjustment
     * @return adjusted absolute price difference
     */
    @Override
    public double calcRealDiff() {
        // I apply the 0.5 adjustment
        return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
    }
}
