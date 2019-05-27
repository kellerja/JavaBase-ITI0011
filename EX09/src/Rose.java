/**
 * Created by Jaanus on 10.03.16.
 */
public class Rose extends Flower {
    /***/
    private static final double DISCOUNT = 0.05;
    /***/
    private boolean hasThorns = false;
    /**
     * @return value of hasThorns.
     */
    public boolean hasThorns() {
        return hasThorns;
    }
    /**
     * @param hasThorns - boolean if rose has thorns.
     */
    public void setHasThorns(boolean hasThorns) {
        this.hasThorns = hasThorns;
    }
    /**
     * @param price - get price.
     */
    public Rose(double price) {
        super(price);
    }
    /**
     * @param price - get price.
     * @param thorns - if rose has thorns;
     */
    public Rose(double price, boolean thorns) {
        super(price);
        hasThorns = thorns;
    }
    /**
     * @param flowerAmount - number of roses
     * @return price of one flower considering pricing rules.
     */
    public double getPrice(int flowerAmount) {
        if (flowerAmount > 0 && super.getPrice() > 0) {
            if (flowerAmount >= 2 + 1) {
                return super.getPrice() * (1 - DISCOUNT);
            } else {
                return super.getPrice();
            }
        }
        return 0;
    }
}
