package snake;

import javafx.scene.Node;
import javafx.scene.shape.Circle;

/**
 * Created by Jaanus on 25.04.16.
 */
public class EscapingTreasure extends Treasure {
    /***/
    private final double escapeRadiusModifier = 4;
    /***/
    private Circle escapeBox;

    /**
     * @param x - x pos.
     * @param y - y pos.
     * @param r - radius.
     */
    EscapingTreasure(double x, double y, double r) {
        super(x, y, r);
        addCircle(x, y, r);
    }

    /**
     * @param x - x pos.
     * @param y - y pos.
     * @param r - radius.
     * @param pickupValue - add to score when picked up;
     */
    EscapingTreasure(double x, double y, double r, int pickupValue) {
        super(x, y, r, pickupValue);
        addCircle(x, y, r);
    }

    /**
     * @param x - x pos.
     * @param y - y pos.
     * @param r - radius.
     */
    private void addCircle(double x, double y, double r) {
        escapeBox = new Circle(x, y, r * escapeRadiusModifier);
    }

    /**
     * @param snake - snake.
     */
    public void move(Snake snake) {
        if (escapeBox.intersects(snake.getBoundsInLocal())) {
            double diffX = 0, diffY = 0;
            if (getCenterX() - snake.getPoints().get(snake.getPoints().size() - 2) > 0) {
                diffX += 1;
            }
            if (getCenterX() - snake.getPoints().get(snake.getPoints().size() - 2) < 0) {
                diffX -= 1;
            }
            if (getCenterY() - snake.getPoints().get(snake.getPoints().size() - 1) > 0) {
                diffY += 1;
            }
            if (getCenterY() - snake.getPoints().get(snake.getPoints().size() - 1) < 0) {
                diffY -= 1;
            }
            for (Node node: getParent().getChildrenUnmodifiable()) {
                if (node instanceof Obstacle
                        && ((Obstacle) node).contains(getCenterX() + diffX, getCenterY() + diffY)) {
                    Obstacle toCheck = (Obstacle) node;
                    setCenterX(getCenterX() - diffX);
                    setCenterY(getCenterY() - diffY);
                    escapeBox.setCenterX(getCenterX() - diffX);
                    escapeBox.setCenterY(getCenterY() - diffY);
                    return;
                }
            }
            setCenterX(getCenterX() + diffX);
            setCenterY(getCenterY() + diffY);
            escapeBox.setCenterX(getCenterX() + diffX);
            escapeBox.setCenterY(getCenterY() + diffY);
        }
    }
}
