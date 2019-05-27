package snake;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by Jaanus on 16.04.16.
 */
public class Treasure extends Circle {
    /***/
    private int value = 1;

    /**
     * @param x - x pos.
     * @param y - y pos.
     * @param r - radius.
     */
    Treasure(double x, double y, double r) {
        setFill(Color.YELLOW);
        setCenterX(x);
        setCenterY(y);
        setRadius(r);
    }

    /**
     * @param x - x pos.
     * @param y - y pos.
     * @param r - radius.
     * @param pickupValue - add to score when picked up;
     */
    Treasure(double x, double y, double r, int pickupValue) {
        setFill(Color.YELLOW);
        setCenterX(x);
        setCenterY(y);
        setRadius(r);
        value = pickupValue;
    }

    /***/
    public void getEaten() {
        setVisible(false);
    }

    /**
     * @return value to add to score on pickup.
     */
    public int getValue() {
        return value;
    }
}
