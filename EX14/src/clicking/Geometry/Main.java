package clicking.Geometry;
/**
 * Created by Jaanus on 8.04.16.
 */

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.VLineTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

/***/
public class Main extends Application {
    /***/
    Stage stage;
    /***/
    Pane layout;
    /***/
    Scene primaryScene;
    /***/
    Label label;
    /***/
    private int clickCount;
    /***/
    private final int[] dimensions = {800, 600};
    /***/
    private final double animationTime = 3000;
    /***/
    private final int pathRadiusLow = 100;
    /***/
    private final int pathRadiusHigh = 10000;
    /***/
    private boolean started = false;
    /***/
    private Label timer;
    /***/
    private final double timerStartValue = 11500;
    /***/
    private final double timerTickValue = 10;
    /***/
    private final double timerConversion = 1000;
    /***/
    private double displayTime = timerStartValue;
    /***/
    private Button start;
    /***/
    private final double delayPosChangeUpdate = 100;
    /***/
    private final double[] timerPos = {200, 0};
    /***/
    private final double[] startButtonPos = {0, 20};
    /***/
    private final double arcToMaxRadiusX = 50;
    /***/
    private final double arcToMaxRadiusY = 50;
    /***/
    private final int numberOfPaths = 6;

    /**
     * @param args - command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle("EX14");
        layout = new Pane();
        primaryScene = new Scene(layout, dimensions[0], dimensions[1]);
        primaryStage.setScene(primaryScene);
        label = new Label();
        label.setText("Score: " + clickCount);
        label.setAlignment(Pos.TOP_LEFT);
        layout.getChildren().add(label);
        timer = new Label();
        timer.setLayoutX(timerPos[0]);
        timer.setLayoutY(timerPos[1]);
        timer.setText(String.format("%.2f Sec", timerStartValue / timerConversion));
        layout.getChildren().add(timer);
        start = new Button();
        start.setText("Start");
        start.setAlignment(Pos.TOP_LEFT);
        start.setLayoutX(startButtonPos[0]);
        start.setLayoutY(startButtonPos[1]);
        start.setOnAction(event -> {
            if (!started) {
                started = true;
                timer.setText(String.format("%.2f Sec", timerStartValue / timerConversion));
                displayTime = timerStartValue;
                start.setDisable(true);
                move();
            }
        });
        layout.getChildren().add(start);
        addCircle(1, 1, 2, Color.BLUE, 1);
        primaryStage.show();
    }

    /**
     * @param rectangle - rectangle node object.
     * @param clickValue - value when clicked on.
     */
    private void addRectangle(Rectangle rectangle, int clickValue) {
        rectangle.setOnMouseClicked(event -> {
            if (started) {
                clickCount += clickValue;
                label.setText("Score: " + clickCount);
            }
        });
        layout.getChildren().add(rectangle);
    }

    /**
     * @param y - y pos.
     * @param x - x pos.
     * @param clickValue - value when clicked on.
     * @param fill - color.
     * @param r - radius.
     */
    private void addCircle(double x, double y, double r, Color fill, int clickValue) {
        Circle circle = new Circle(r);
        circle.setFill(fill);
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setOnMouseClicked(event -> {
            if (started) {
                clickCount += clickValue;
                label.setText("Score: " + clickCount);
            }
        });
        layout.getChildren().add(circle);
    }

    /***/
    private void move() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        for (Node shape: layout.getChildren()) {
            if (shape instanceof Circle) {
                Circle s = (Circle) shape;
                makePath(shape, s.getCenterX(), s.getCenterY(), s.getRadius(), s.getRadius());
                KeyFrame keyFrame = new KeyFrame(Duration.millis(animationTime),
                        e -> {
                            makePath(shape, s.getCenterX(), s.getCenterY(), s.getRadius(), s.getRadius());
                        });
                timeline.getKeyFrames().add(keyFrame);
            } else if (shape instanceof Rectangle) {
                Rectangle s = (Rectangle) shape;
                makePath(shape, s.getX(), s.getY(), s.getWidth(), s.getHeight());
                KeyFrame keyFrame = new KeyFrame(Duration.millis(animationTime),
                        event -> makePath(shape, s.getX(), s.getY(), s.getWidth(), s.getHeight()));
                timeline.getKeyFrames().add(keyFrame);
            }
        }
        timeline.play();
        if (started) {
            Timeline timeline1 = new Timeline();
            timeline1.setCycleCount(1);
            Timeline timeline2 = new Timeline();
            timeline2.setCycleCount((int) (timerStartValue / timerTickValue));
            KeyFrame keyFrameTimer = new KeyFrame(Duration.millis(timerTickValue), event -> {
                displayTime -= timerTickValue;
                timer.setText(String.format("%.2f Sec", displayTime / timerConversion));
            });
            KeyFrame keyFrame1 = new KeyFrame(Duration.millis(timerStartValue), event -> {
                timeline.stop();
                started = false;
                start.setDisable(false);
                clickCount = 0;
            });
            timeline1.getKeyFrames().add(keyFrame1);
            timeline2.getKeyFrames().add(keyFrameTimer);
            timeline2.play();
            timeline1.play();
        }
    }

    /**
     * @param x - x pos.
     * @param y - y pos.
     * @param shape - node.
     * @param minX - value to ensure shape stays in window.
     * @param minY - value to ensure shape stays in window.
     */
    private void makePath(Node shape, double x, double y, double minX, double minY) {
        int rngXvalue, rngYvalue;
        double rngAddX = minX, rngAddY = minY;
        if (layout.getWidth() <= minX + x + pathRadiusHigh
                || x - minX - pathRadiusHigh <= 0) {
            rngXvalue = (int) (layout.getWidth() - 2 * minX);
        } else {
            rngXvalue = pathRadiusHigh * 2;
            rngAddX = x - pathRadiusHigh;
        }
        if (layout.getHeight() <= minY + y + pathRadiusHigh
                || y - minY - pathRadiusHigh <= 0) {
            rngYvalue = (int) (layout.getHeight() - 2 * minY);
        } else {
            rngYvalue = pathRadiusHigh * 2;
            rngAddY = y - pathRadiusHigh;
        }
        System.out.println(x + " " + y);
        Random rng = new Random();

        Path path = new Path();
        path.getElements().add(new MoveTo(x, y));
        PathElement[] paths = new PathElement[numberOfPaths];
        paths[0] = new CubicCurveTo();
        paths[1] = new HLineTo();
        paths[2] = new VLineTo();
        paths[2 + 1] = new QuadCurveTo();
        paths[2 + 2] = new LineTo();
        paths[2 + 2 + 1] = new ArcTo();

        int[] pathMaker = new int[rng.nextInt(1) + 1];
        for (int i = 0; i < pathMaker.length; i++) {
            pathMaker[i] = rng.nextInt(paths.length);
        }

        double newX = x, newY = y;
        for (int p: pathMaker) {
            System.out.println(p);
            if (paths[p] instanceof CubicCurveTo) {
                newX = rng.nextInt(rngXvalue) + rngAddX;
                newY = rng.nextInt(rngYvalue) + rngAddY;
                path.getElements().add(new CubicCurveTo(rng.nextInt(rngXvalue) + rngAddX,
                        rng.nextInt(rngYvalue) + rngAddY,
                        rng.nextInt(rngXvalue) + rngAddX,
                        rng.nextInt(rngYvalue) + rngAddY,
                        newX,
                        newY));
            } else if (paths[p] instanceof HLineTo) {
                newX = rng.nextInt(rngXvalue) + rngAddX;
                newY = y;
                path.getElements().add(new HLineTo(newX));
            } else if (paths[p] instanceof VLineTo) {
                newX = x;
                newY = rng.nextInt(rngYvalue) + rngAddY;
                path.getElements().add(new VLineTo(newY));
            } else if (paths[p] instanceof QuadCurveTo) {
                newX = rng.nextInt(rngXvalue) + rngAddX;
                newY = rng.nextInt(rngYvalue) + rngAddY;
                path.getElements().add(new QuadCurveTo(rng.nextInt(rngXvalue) + rngAddX,
                        rng.nextInt(rngYvalue) + rngAddY,
                        newX,
                        newY));
            } else if (paths[p] instanceof LineTo) {
                newX = rng.nextInt(rngXvalue) + rngAddX;
                newY = rng.nextInt(rngYvalue) + rngAddY;
                path.getElements().add(new LineTo(newX, newY));
            } else if (paths[p] instanceof ArcTo) {
                newX = rng.nextInt(rngXvalue) + rngAddX;
                newY = rng.nextInt(rngYvalue) + rngAddY;
                ArcTo arcTo = new ArcTo();
                arcTo.setX(newX);
                arcTo.setY(newY);
                arcTo.setRadiusX(rng.nextInt(arcToMaxRadiusX));
                arcTo.setRadiusY(rng.nextInt(arcToMaxRadiusY));
                arcTo.setLargeArcFlag(false);
                double vectorX = newX - x;
                double vectorY = newY - y;
                arcTo.setSweepFlag(true);
                if (Math.abs(vectorX) > Math.abs(vectorY)) {
                    if ((vectorX < 0 && layout.getHeight() / 2 <= y)
                            || (vectorX > 0 && layout.getHeight() / 2 > y))  {
                        arcTo.setSweepFlag(false);
                    } else {
                        arcTo.setSweepFlag(true);
                    }
                } else {
                    if ((vectorY < 0 && layout.getWidth() / 2 <= x)
                            || (vectorY > 0 && layout.getWidth() / 2 > x)) {
                        arcTo.setSweepFlag(true);
                    } else {
                        arcTo.setSweepFlag(false);
                    }
                }
                path.getElements().add(arcTo);
            }
        }
        Timeline delay = new Timeline();
        delay.setCycleCount(1);
        double tempX = newX;
        double tempY = newY;
        KeyFrame delayKeyFrame = new KeyFrame(Duration.millis(animationTime - delayPosChangeUpdate),
                event -> {
            changePos(shape, tempX, tempY);
        });
        delay.getKeyFrames().add(delayKeyFrame);
        delay.play();
        makePathTransition(path, shape);
    }

    /**
     * @param newY - y pos.
     * @param newX - x pos.
     * @param shape - node to move.
     */
    private void changePos(Node shape, double newX, double newY) {
        if (shape instanceof Circle) {
            Circle c = (Circle) shape;
            c.setCenterX(newX);
            c.setCenterY(newY);
        } else if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle) shape;
            r.setX(newX);
            r.setY(newY);
        }
    }

    /**
     * @param shape - node.
     * @param path - path.
     */
    private void makePathTransition(Path path, Node shape) {
        PathTransition pathTransition = new PathTransition();
        //pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setDuration(Duration.millis(animationTime));
        pathTransition.setCycleCount(1);
        pathTransition.setNode(shape);
        pathTransition.setAutoReverse(true);
        pathTransition.setPath(path);
        pathTransition.play();
    }
}
