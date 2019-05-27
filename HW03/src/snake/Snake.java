package snake;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

/**
 * Created by Jaanus on 16.04.16.
 */
public class Snake extends Polyline {
    /***/
    private double snakeWidth = SettingsValues.currentSnakeWidth;
    /***/
    private Color snakeColor = SettingsValues.currentSnakeColor;
    /***/
    private int snakeLengthModifier = SettingsValues.currentSnakeLengthModifier;
    /***/
    private int snakeMovementRadius = SettingsValues.currentSnakeMovementRadius;
    /***/
    private double snakeTurnRadius = SettingsValues.currentSnakeTurnRadius;
    /***/
    private double snakeSpeed = SettingsValues.currentSnakeSpeed;
    /***/
    private boolean isAlive = true;
    /***/
    public int score = 0;
    /***/
    private String name = "";
    /***/
    private final int halfCircle = 180;
    /***/
    private final double roundingMistake = 10e-5;
    /***/
    private final double roundingMistakeBigger = 10e-4;
    /***/
    private final double quarter = 0.25;
    /***/
    private double tempX;
    /***/
    private double tempY;
    /***/
    private boolean up = false;
    /***/
    private boolean down = false;
    /***/
    private boolean left = false;
    /***/
    private boolean right = false;
    /***/
    private boolean buttonMove = false;
    /***/
    private final double divideForSMovement = 50;
    /***/
    private final double[] previewSnakeMoveTo = {100, 30};

    /**
     * @param x - snake head x pos.
     * @param y - snake head y pos.
     * @param direction - snake starting direction.
     */
    Snake(double x, double y, String direction) {
        setStroke(snakeColor);
        setStrokeWidth(snakeWidth);
        addPoints(x, y, direction);
    }

    /**
     * @param x - snake head x pos.
     * @param y - snake head y pos.
     * @param direction - snake starting direction.
     */
    private void addPoints(double x, double y, String direction) {
        for (int i = 0; i <= snakeLengthModifier; i++) {
            if (direction.equals("east")) {
                getPoints().addAll(new Double[]{x - i, y});
            } else if (direction.equals("south")) {
                getPoints().addAll(new Double[]{x, y + i});
            } else if (direction.equals("west")) {
                getPoints().addAll(new Double[]{x + i, y});
            } else {
                getPoints().addAll(new Double[]{x, y - i});
            }
        }
    }

    /**
     * @return circle intersections.
     */
    private double[] getCircleIntersections() {
        double x = getPoints().get(getPoints().size() - 2), y = getPoints().get(getPoints().size() - 1);
        double prevX = getPoints().get(getPoints().size()
                - (2 + 2)), prevY = getPoints().get(getPoints().size() - (2 + 1));
        double dx = x - prevX, dy = y - prevY;
        double r = snakeMovementRadius;
        double alpha = snakeTurnRadius;
        double yt = y + dy;
        double xt = x + dx;
        double r2 = Math.pow(r, 2) + Math.pow(r, 2) - 2 * r * r * Math.cos(Math.toRadians(alpha));
        double a = x, b = y, r0 = r, c = xt, d = yt, r1 = Math.sqrt(r2);
        double diam = Math.sqrt(Math.pow(c - a, 2) + Math.pow(d - b, 2));
        double si = quarter * Math.sqrt((diam + r0 + r1) * (diam + r0 - r1)
                * (diam - r0 + r1) * (-diam + r0 + r1));
        double x1 = ((a + c) / 2) + ((c - a) * (Math.pow(r0, 2) - Math.pow(r1, 2)))
                / (2 * Math.pow(diam, 2)) + 2 * ((b - d) / (Math.pow(diam, 2))) * si;
        double x2 = ((a + c) / 2) + ((c - a) * (Math.pow(r0, 2) - Math.pow(r1, 2)))
                / (2 * Math.pow(diam, 2)) - 2 * ((b - d) / (Math.pow(diam, 2))) * si;
        double y1 = ((b + d) / 2) + ((d - b) * (Math.pow(r0, 2) - Math.pow(r1, 2)))
                / (2 * Math.pow(diam, 2)) - 2 * ((a - c) / (Math.pow(diam, 2))) * si;
        double y2 = ((b + d) / 2) + ((d - b) * (Math.pow(r0, 2) - Math.pow(r1, 2)))
                / (2 * Math.pow(diam, 2)) + 2 * ((a - c) / (Math.pow(diam, 2))) * si;
        return new double[]{x1, y1, x2, y2};
    }

    /***/
    public void move() {
        if (buttonMove) {
            tempX = getPoints().get(getPoints().size() - 2);
            tempY = getPoints().get(getPoints().size() - 1);
        }
        if (left) {
            tempX -= tempX;
        }
        if (right) {
            tempX += getScene().getWidth();
        }
        if (up) {
            tempY -= tempY;
        }
        if (down) {
            tempY += getScene().getHeight();
        }
        move(tempX, tempY);
    }

    /**
     * @param endX - where to move.
     * @param endY - where to move.
     */
    public void move(double endX, double endY) {
        if (!isAlive) {
            return;
        }
        fitten();
        double x = getPoints().get(getPoints().size() - 2), y = getPoints().get(getPoints().size() - 1);
        double prevX = getPoints().get(getPoints().size()
                - (2 + 2)), prevY = getPoints().get(getPoints().size() - (2 + 1));
        double dx = endX - x, dy = endY - y;
        double edgeA = Math.sqrt(dx * dx + dy * dy);
        double edgeB = Math.sqrt(Math.pow(x - prevX, 2) + Math.pow(y - prevY, 2));
        double edgeC = Math.sqrt(Math.pow(endX - prevX, 2) + Math.pow(endY - prevY, 2));
        double alpha = Math.toDegrees(Math.acos((Math.pow(edgeA, 2) + Math.pow(edgeB, 2)
                - Math.pow(edgeC, 2)) / (2 * edgeA * edgeB)));
        double r = snakeMovementRadius;
        double speed = snakeSpeed;

        if (halfCircle - alpha <= roundingMistake || Double.isNaN(halfCircle - alpha)
                || Math.ceil(halfCircle - alpha) == halfCircle) {
            dx = x - prevX;
            dy = y - prevY;
        } else if (halfCircle - alpha < snakeTurnRadius) {
            dx = (dx * r) / edgeA;
            dy = (dy * r) / edgeA;
        } else {
            double[] circleIntersectionPoints = getCircleIntersections();
            dx = x - prevX;
            dy = y - prevY;
            if (Math.signum((x + dx - x) * (endY - y) - (y + dy - y) * (endX - x)) > 0) {
                endX = circleIntersectionPoints[0];
                endY = circleIntersectionPoints[1];
            } else {
                endX = circleIntersectionPoints[2];
                endY = circleIntersectionPoints[2 + 1];
            }
            dx = ((endX - x) * r) / Math.sqrt(Math.pow(endX - x, 2) + Math.pow(endY - y, 2));
            dy = ((endY - y) * r) / Math.sqrt(Math.pow(endX - x, 2) + Math.pow(endY - y, 2));
        }
        x += Math.cos(System.currentTimeMillis() / divideForSMovement);
        y += Math.sin(System.currentTimeMillis() / divideForSMovement);
        double[] neckPos = new double[]{x + dx * (speed - 1), y + dy * (speed - 1)};
        double[] newPos = new double[]{x + dx * speed, y + dy * speed};
        collisionDetection(newPos);
        updateSnakePoints(newPos, neckPos);
    }

    /**
     * @param newPos to add a point.
     * @param neckPos a point before head.
     */
    private void updateSnakePoints(double[] newPos, double[] neckPos) {
        getPoints().remove(0);
        getPoints().remove(0);
        getPoints().remove(getPoints().size() - 1);
        getPoints().remove(getPoints().size() - 1);
        getPoints().addAll(new Double[]{neckPos[0], neckPos[1]});
        getPoints().addAll(new Double[]{newPos[0], newPos[1]});
    }

    /**
     * @param newPos - position to check for.
     */
    private void collisionByItself(double[] newPos) {
        if (contains(newPos[0], newPos[1])) {
            isAlive = false;
        }
    }

    /***/
    private void collisionWithTreasure() {
        double headX = getPoints().get(getPoints().size() - 2);
        double headY = getPoints().get(getPoints().size() - 1);
        for (Node node: getParent().getChildrenUnmodifiable()) {
            if (node instanceof Treasure && (node.contains(headX, headY)
                    || Math.abs(((Treasure) node).getCenterX() - headX)
                    <= roundingMistakeBigger && Math.abs(((Treasure) node).getCenterY()
                    - headY) <= roundingMistakeBigger)) {
                eatTreasure((Treasure) node);
                ((Treasure) node).getEaten();
                score += ((Treasure) node).getValue();
            }
        }
    }

    /**
     * @param headPos - point to add to snake.
     */
    private void collisionWithObstacles(double[] headPos) {
        for (Node node: getParent().getChildrenUnmodifiable()) {
            if (node instanceof Obstacle && ((Obstacle) node).contains(headPos[0], headPos[1])) {
                isAlive = false;
            }
        }
    }

    /**
     * @param checkCords - coordinates to check for collision.
     */
    private void collisionWithBounds(double[] checkCords) {
        if (checkCords[0] <= 0 || checkCords[1] <= 0
                || checkCords[0] >= getScene().getWidth()
                || checkCords[1] >= getScene().getHeight()) {
            isAlive = false;
        }
    }

    /**
     * @param newPos - new position.
     */
    private void collisionDetection(double[] newPos) {
        collisionByItself(newPos);
        collisionWithTreasure();
        collisionWithBounds(newPos);
        collisionWithObstacles(newPos);
    }

    /***/
    private void fitten() {
        if (getStrokeWidth() > snakeWidth) {
            setStrokeWidth(getStrokeWidth() - SettingsValues.currentSnakeFittenSpeed);
        }
    }

    /**
     * @param treasure - treasure that snake ate.
     */
    private void fatten(Treasure treasure) {
        setStrokeWidth(getStrokeWidth() + SettingsValues.currentSnakeFattenSpeed);
    }

    /***/
    private void increaseLength() {
        getPoints().addAll(new Double[] {getPoints().get(0), getPoints().get(1)});
    }

    /**
     * @param treasure - treasure that snake ate.
     */
    private void eatTreasure(Treasure treasure) {
        fatten(treasure);
        increaseLength();
    }

    /**
     * @return true if snake is alive, false if dead.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * @return - name of the snake.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name - set name to snake.
     */
    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    /**
     * @param stage - javafx stage.
     */
    private void createKeyboardHandler(Stage stage) {
        stage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.A) {
                left = true;
            }
            if (event.getCode() == KeyCode.D) {
                right = true;
            }
            if (event.getCode() == KeyCode.W) {
                up = true;
            }
            if (event.getCode() == KeyCode.S) {
                down = true;
            }
        });
        stage.getScene().setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.A) {
                left = false;
            }
            if (event.getCode() == KeyCode.D) {
                right = false;
            }
            if (event.getCode() == KeyCode.W) {
                up = false;
            }
            if (event.getCode() == KeyCode.S) {
                down = false;
            }
        });
    }

    /**
     * @param stage - javafx stage.
     */
    private void createMouseHandler(Stage stage) {
        stage.getScene().setOnMouseMoved(event -> {
            tempX = event.getX();
            tempY = event.getY();
        });
    }

    /**
     * @param stage - javafx stage.
     * @param useMouse - should mouse be used.
     */
    public void setMove(Stage stage, boolean useMouse) {
        if (useMouse) {
            createMouseHandler(stage);
        } else {
            createKeyboardHandler(stage);
        }
    }

    /**
     * @param lengthModifier - snake initial length.
     */
    public void setSnakeLengthModifier(int lengthModifier) {
        snakeLengthModifier = lengthModifier;
    }

    /**
     * @param radius - snake turn radius.
     */
    public void setSnakeTurnRadius(double radius) {
        snakeTurnRadius = radius;
    }

    /**
     * @param speed - snake speed.
     */
    public void setSnakeSpeed(double speed) {
        snakeSpeed = speed;
    }

    /***/
    public void previewMove() {
        double endX = previewSnakeMoveTo[0], endY = previewSnakeMoveTo[1];
        double x = getPoints().get(getPoints().size() - 2), y = getPoints().get(getPoints().size() - 1);
        double prevX = getPoints().get(getPoints().size()
                - (2 + 2)), prevY = getPoints().get(getPoints().size() - (2 + 1));
        double dx = endX - x, dy = endY - y;
        double edgeA = Math.sqrt(dx * dx + dy * dy);
        double edgeB = Math.sqrt(Math.pow(x - prevX, 2) + Math.pow(y - prevY, 2));
        double edgeC = Math.sqrt(Math.pow(endX - prevX, 2) + Math.pow(endY - prevY, 2));
        double alpha = Math.toDegrees(Math.acos((Math.pow(edgeA, 2) + Math.pow(edgeB, 2)
                - Math.pow(edgeC, 2)) / (2 * edgeA * edgeB)));
        double r = snakeMovementRadius;
        double speed = snakeSpeed;

        if (halfCircle - alpha <= roundingMistake || Double.isNaN(halfCircle - alpha)
                || Math.ceil(halfCircle - alpha) == halfCircle) {
            dx = x - prevX;
            dy = y - prevY;
        } else if (halfCircle - alpha < snakeTurnRadius) {
            dx = (dx * r) / edgeA;
            dy = (dy * r) / edgeA;
        } else {
            double[] circleIntersectionPoints = getCircleIntersections();
            dx = x - prevX;
            dy = y - prevY;
            if (Math.signum((x + dx - x) * (endY - y) - (y + dy - y) * (endX - x)) > 0) {
                endX = circleIntersectionPoints[0];
                endY = circleIntersectionPoints[1];
            } else {
                endX = circleIntersectionPoints[2];
                endY = circleIntersectionPoints[2 + 1];
            }
            dx = ((endX - x) * r) / Math.sqrt(Math.pow(endX - x, 2) + Math.pow(endY - y, 2));
            dy = ((endY - y) * r) / Math.sqrt(Math.pow(endX - x, 2) + Math.pow(endY - y, 2));
        }
        //x += Math.cos(System.currentTimeMillis() / 50);
        //y += Math.sin(System.currentTimeMillis() / 50);
        double[] neckPos = new double[]{x + dx * (speed - 1), y + dy * (speed - 1)};
        double[] newPos = new double[]{x + dx * speed, y + dy * speed};
        updateSnakePoints(newPos, neckPos);
    }
}
