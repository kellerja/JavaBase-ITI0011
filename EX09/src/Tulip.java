/**
 * Created by Jaanus on 10.03.16.
 */
public class Tulip extends Flower {
    /***/
    private static final double DISCOUNT = 0.10;
    /***/
    private String color = null;
    /**
     * @param price - get price.
     */
    public Tulip(double price) {
        super(price);
    }
    /**
     * @param price - set price.
     * @param color - set color.
     */
    public Tulip(double price, String color) {
        super(price);
        this.color = color;
    }
    /**
     * @return tulip's color.
     */
    public String getColor() {
        return color;
    }
    /**
     * @param flowerAmount - number of tulips
     * @return price of one flower considering pricing rules.
     */
    public double getPrice(int flowerAmount) {
        if (flowerAmount > 0 && super.getPrice() > 0) {
            if (flowerAmount >= 2 + 2 + 1) {
                return super.getPrice() * (1 - DISCOUNT);
            } else {
                return super.getPrice();
            }
        }
        return 0;
    }
}
