/**
 * Created by Jaanus on 10.03.16.
 */
public class Flower {
    /***/
    private double price;
    /**
     * @param price - flower price
     */
    public Flower(double price) {
        this.price = price;
    }
    /**
     * @return flower price
     */
    public double getPrice() {
        if (price > 0) {
            return price;
        } else {
            return 0;
        }
    }
    /**
     * @return flower actual price
     */
    public double getRealPrice() {
        return price;
    }
}
