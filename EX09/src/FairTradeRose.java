/**
 * Created by Jaanus on 12.03.16.
 */
public class FairTradeRose extends Rose {
    /**
     * @param price - get price.
     */
    public FairTradeRose(double price) {
        super(price);
    }
    /**
     * @param price - get price.
     * @param thorns - if rose has thorns;
     */
    public FairTradeRose(double price, boolean thorns) {
        super(price, thorns);
    }
    /**
     * @return double the original price with no discounts.
     */
    public double getPrice() {
        return super.getPrice() * 2;
    }
}
