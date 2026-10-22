package products;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;import products.*;

class ProductTest {

    private static final double DELTA = 0.000001;

    @Test
    @DisplayName("Jeans uses an adjustment of 0.5")
    void jeansCalculatesRealDifference() {
        Product product = new Jeans(10.00, 20.00);
        assertEquals(5.00, product.calcRealDiff(), DELTA);
    }

    @Test
    @DisplayName("TShirts uses an adjustment of 0.2")
    void tShirtsCalculatesRealDifference() {
        Product product = new TShirts(10.00, 20.00);
        assertEquals(2.00, product.calcRealDiff(), DELTA);
    }

    @Test
    @DisplayName("Skirts uses an adjustment of 0.1")
    void skirtsCalculateRealDifference() {
        Product product = new Skirts(10.00, 20.00);
        assertEquals(1.00, product.calcRealDiff(), DELTA);
    }

    @Test
    @DisplayName("Dresses uses an adjustment of 0.6")
    void dressesCalculateRealDifference() {
        Product product = new Dresses(10.00, 20.00);
        assertEquals(6.00, product.calcRealDiff(), DELTA);
    }

    @Test
    @DisplayName("Shorts uses an adjustment of 0.3")
    void shortsCalculateRealDifference() {
        Product product = new Shorts(5.25, 23.36);
        assertEquals(5.433, product.calcRealDiff(), DELTA);
    }

    @Test
    @DisplayName("Jackets uses an adjustment of 0.7")
    void jacketsCalculateRealDifference() {
        Product product = new Jackets(10.00, 20.00);
        assertEquals(7.00, product.calcRealDiff(), DELTA);
    }

    @Test
    @DisplayName("Shoes uses an adjustment of 0.4")
    void shoesCalculateRealDifference() {
        Product product = new Shoes(10.00, 20.00);
        assertEquals(4.00, product.calcRealDiff(), DELTA);
    }

    @Test
    @DisplayName("Real difference is absolute when updated price is lower")
    void calculationUsesAbsoluteDifference() {
        Product lowerUpdatedPrice = new Jeans(20.00, 10.00);
        Product higherUpdatedPrice = new Jeans(10.00, 20.00);

        assertEquals(lowerUpdatedPrice.calcRealDiff(),
                     higherUpdatedPrice.calcRealDiff(), DELTA);
    }

    @Test
    @DisplayName("Equal prices produce a zero difference")
    void equalPricesProduceZeroDifference() {
        Product product = new Shoes(25.00, 25.00);
        assertEquals(0.00, product.calcRealDiff(), DELTA);
    }

    @Test
    @DisplayName("compareTo compares calculated real differences")
    void compareToUsesRealDifference() {
        Product smaller = new Jeans(10.00, 20.00);  // 5.0
        Product larger = new Jackets(10.00, 20.00); // 7.0
        Product equal = new Jeans(20.00, 10.00);    // 5.0

        assertTrue(smaller.compareTo(larger) < 0);
        assertTrue(larger.compareTo(smaller) > 0);
        assertEquals(0, smaller.compareTo(equal));
    }

    @Test
    @DisplayName("compareTo is antisymmetric")
    void compareToIsAntisymmetric() {
        Product first = new Shorts(5.25, 23.36);
        Product second = new Shoes(10.00, 30.00);

        int firstToSecond = Integer.signum(first.compareTo(second));
        int secondToFirst = Integer.signum(second.compareTo(first));

        assertEquals(-firstToSecond, secondToFirst);
    }

    @Test
    @DisplayName("OriginalPriceComparator compares original prices only")
    void originalPriceComparatorUsesOriginalPrice() {
        Product lower = new Jeans(10.00, 100.00);
        Product higher = new Shoes(20.00, 1.00);
        Comparator<Product> comparator = new OriginalPriceComparator();

        assertTrue(comparator.compare(lower, higher) < 0);
        assertTrue(comparator.compare(higher, lower) > 0);
        assertEquals(0, comparator.compare(lower, new Jackets(10.00, 500.00)));
    }

    @Test
    @DisplayName("UpdatedPriceComparator compares updated prices only")
    void updatedPriceComparatorUsesUpdatedPrice() {
        Product lower = new Jeans(100.00, 10.00);
        Product higher = new Shoes(1.00, 20.00);
        Comparator<Product> comparator = new UpdatedPriceComparator();

        assertTrue(comparator.compare(lower, higher) < 0);
        assertTrue(comparator.compare(higher, lower) > 0);
        assertEquals(0, comparator.compare(lower, new Jackets(500.00, 10.00)));
    }

    @Test
    @DisplayName("Negative original prices are rejected")
    void negativeOriginalPriceIsRejected() {
        assertThrows(IllegalArgumentException.class,
                     () -> new Jeans(-1.00, 10.00));
    }

    @Test
    @DisplayName("Negative updated prices are rejected")
    void negativeUpdatedPriceIsRejected() {
        assertThrows(IllegalArgumentException.class,
                     () -> new Jeans(10.00, -1.00));
    }

    @Test
    @DisplayName("Non-finite prices are rejected")
    void nonFinitePricesAreRejected() {
        assertThrows(IllegalArgumentException.class,
                     () -> new Jeans(Double.NaN, 10.00));
        assertThrows(IllegalArgumentException.class,
                     () -> new Jeans(10.00, Double.POSITIVE_INFINITY));
    }

    @Test
    @DisplayName("Comparing with null throws NullPointerException")
    void compareToRejectsNull() {
        Product product = new Jeans(10.00, 20.00);
        assertThrows(NullPointerException.class,
                     () -> product.compareTo(null));
    }
}
