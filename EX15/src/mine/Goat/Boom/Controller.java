package mine.Goat.Boom;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jaanus on 8.04.16.
 */
public class Controller {
    /***/
    @FXML
    private ImageView goat;
    /***/
    private boolean top = false;
    /***/
    private boolean bottom = false;
    /***/
    private boolean left = false;
    /***/
    private boolean right = false;
    /***/
    private Timeline timeline;
    /***/
    @FXML
    private Pane pane;
    /***/
    private final double tick = 10;
    /***/
    private final double moveUnitAmountX = 1;
    /***/
    private final double moveUnitAmountY = 1;
    /***/
    @FXML
    private SplitMenuButton gameState;
    /***/
    private Timeline minesTimeline;
    /***/
    private final double initialMineSpawnTimer = 5000;
    /***/
    private double mineSpawnTimer = initialMineSpawnTimer;
    /***/
    private final double initialScoreValue = 0;
    /***/
    private double score = initialScoreValue;
    /***/
    @FXML
    private Label scoreLabel;
    /***/
    private final double rotationOffset = 90;
    /***/
    private boolean willMinesExplode = false;
    /***/
    private final int initialGoatLives = 3;
    /***/
    private int goatLives = initialGoatLives;
    /***/
    @FXML
    private Label goatLivesLabel;
    /***/
    private double startTimeStamp = System.currentTimeMillis();
    /***/
    @FXML
    private Label timePlayedLabel;
    /***/
    private final int secindToMillisecConversion = 1000;
    /***/
    private final int timeConversion = 60;
    /***/
    private final double minimumMineSpawnTimer = 2000;
    /***/
    private final double decreaseMineSpawnTimer = 100;
    /***/
    private final double decreaseEveryAmount = 30;

    /**
     * @param event - key event.
     */
    @FXML
    private void pressKey(KeyEvent event) {
        if (event.getSource().equals(goat)) {
            if (event.getCode() == KeyCode.W) {
                top = true;
            } else if (event.getCode() == KeyCode.S) {
                bottom = true;
            } else if (event.getCode() == KeyCode.A) {
                left = true;
            } else if (event.getCode() == KeyCode.D) {
                right = true;
            }
        }
    }

    /**
     * @param event - key event.
     */
    @FXML
    private void releasedKey(KeyEvent event) {
        if (event.getSource().equals(goat)) {
            if (event.getCode() == KeyCode.W) {
                top = false;
            } else if (event.getCode() == KeyCode.S) {
                bottom = false;
            } else if (event.getCode() == KeyCode.A) {
                left = false;
            } else if (event.getCode() == KeyCode.D) {
                right = false;
            }
        }
    }

    /***/
    public void toggleExsplodingMines() {
        willMinesExplode = !willMinesExplode;
    }

    /***/
    public void start() {
        if (gameState.getText().equals("Start")) {
            gameState.setText("Stop");
            goatLives = initialGoatLives;
            score = initialScoreValue;
            startTimeStamp = System.currentTimeMillis();
            timePlayedLabel.setText("Played: 0 ms");
            updateScore();
            updateLives();
            setTimeline();
            setMinesTimeline();
        } else {
            minesTimeline.stop();
            timeline.stop();
            gameState.setText("Start");
        }
    }

    /***/
    private void addMine() {
        Random rng = new Random();
        Mine mine = new Mine();
        mine.setX(rng.nextInt((int) (pane.getWidth() - 2 * mine.mineSizeX)) + mine.mineSizeX);
        mine.setY(rng.nextInt((int) (pane.getHeight() - 2 * mine.mineSizeY)) + mine.mineSizeY);
        if (willMinesExplode) {
            mine.setUpExplode();
        }
        pane.getChildren().add(mine);
    }

    /***/
    private void setMinesTimeline() {
        minesTimeline = new Timeline();
        minesTimeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(mineSpawnTimer), event -> {
            addMine();
        });
        minesTimeline.getKeyFrames().add(keyFrame);
        minesTimeline.play();
    }

    /***/
    private void detectMines() {
        List<Node> toRemove = new ArrayList<>();
        for (Node child: pane.getChildren()) {
            if (child instanceof Mine && goat.getBoundsInParent().intersects(child.getBoundsInParent())) {
                if (!((Mine) child).hasExploded) {
                    toRemove.add(child);
                    score += ((Mine) child).mineValue;
                } else {
                    goatLives -= ((Mine) child).damage;
                    toRemove.add(child);
                }
            }
        }
        pane.getChildren().removeAll(toRemove);
    }

    /***/
    private void updateScore() {
        scoreLabel.setText("Score: " + (int) score);
    }

    /***/
    private void updateLives() {
        goatLivesLabel.setText("Lives: " + goatLives);
    }

    /***/
    private void removeExplodedMines() {
        List<Node> toRemove = new ArrayList<>();
        for (Node child: pane.getChildren()) {
            if (child instanceof Mine && ((Mine) child).canBeRemoved) {
                toRemove.add(child);
            }
        }
        pane.getChildren().removeAll(toRemove);
    }

    /***/
    private void gameOver() {
        if (goatLives <= 0) {
            start();
        }
    }

    /***/
    private void timeStamp() {
        double time = System.currentTimeMillis();
        double deltaTime = time - startTimeStamp;
        int seconds = (int) deltaTime / secindToMillisecConversion;
        int minutes = seconds / timeConversion;
        int hours = minutes / timeConversion;
        String text = "Played: ";
        if (hours > 0) {
            text += hours + " h ";
        }
        if (minutes > 0) {
            text += (minutes % timeConversion) + " m ";
        }
        if (seconds > 0) {
            text += (seconds % timeConversion) + " s ";
        }
        text += ((int) deltaTime % secindToMillisecConversion) + " ms";
        timePlayedLabel.setText(text);
        if (seconds % timeConversion == decreaseEveryAmount) {
            minesTimeline.stop();
            if (mineSpawnTimer - decreaseMineSpawnTimer >= minimumMineSpawnTimer) {
                mineSpawnTimer -= decreaseMineSpawnTimer;
            }
            setMinesTimeline();
        }
    }

    /***/
    public void setTimeline() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(tick), event -> {
            gameOver();
            removeExplodedMines();
            regainFocus();
            double diffX = goat.getX();
            double diffY = goat.getY();
            Bounds bounds = goat.localToScene(goat.getBoundsInLocal());
            if (top && bounds.getMinY() > 0) {
                diffY -= moveUnitAmountY;
            }
            if (bottom && bounds.getMaxY() < pane.getHeight()) {
                diffY += moveUnitAmountY;
            }
            if (left && bounds.getMinX() > 0) {
                diffX -= moveUnitAmountX;
            }
            if (right && bounds.getMaxX() < pane.getWidth()) {
                diffX += moveUnitAmountX;
            }
            double newAngel = 0;
            if (diffX - goat.getX() >= 0) {
                newAngel = (Math.toDegrees(Math.atan((diffY - goat.getY())
                        / (diffX - goat.getX()))) + rotationOffset);
            } else {
                newAngel = (Math.toDegrees(Math.atan((diffY - goat.getY())
                        / (diffX - goat.getX()))) - rotationOffset);
            }
            goat.setX(diffX);
            goat.setY(diffY);
            if (!((Double) newAngel).equals(Double.NaN)) {
                goat.setRotate(newAngel);
            }
            detectMines();
            updateScore();
            updateLives();
            timeStamp();
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    /***/
    public void regainFocus() {
        goat.requestFocus();
    }
}
