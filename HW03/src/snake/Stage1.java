package snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jaanus on 16.04.16.
 */
public class Stage1 {
    /***/
    private Stage stage;
    /***/
    @FXML
    private Label labelGameOver;
    /***/
    @FXML
    private Label labelScore;
    /***/
    private int score = 0;
    /***/
    @FXML
    private Pane backGround;
    /***/
    private Timeline timeline;
    /***/
    private long timeStamp;
    /***/
    private final int treasurePlacementLimitor = 5;
    /***/
    private final int justNegativeValue = -2;
    /***/
    private final int[] startingSnake2Pos = {600, 100};
    /***/
    private final int timeModula = 300;
    /***/
    private final int timeResult = 10;
    /***/
    private final int treasureDissapearTime = 2500;
    /***/
    private final int millisToSec = 1000;
    /***/
    private final int timeBeforeExit = 3000;
    /***/
    private EscapingTreasure escapingTreasure;

    /**
     * @param stage - stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /***/
    private void relocateTreasures() {
        List<Treasure> toRemove = new ArrayList<>();
        for (Node node: backGround.getChildren()) {
            if (node instanceof Treasure) {
                toRemove.add((Treasure) node);
            }
        }
        backGround.getChildren().removeAll(toRemove);
        Random rng = new Random();
        Treasure treasure = new Treasure(rng.nextInt((int) stage.getScene().getWidth()
                - 2 * treasurePlacementLimitor) + treasurePlacementLimitor,
                rng.nextInt((int) stage.getScene().getHeight()
                        - treasurePlacementLimitor * 2) + treasurePlacementLimitor, 2 + 2);
        treasure.visibleProperty().addListener(event -> {
            relocateTreasures();
        });
        backGround.getChildren().add(treasure);
        Treasure negativeTreasure = new Treasure(rng.nextInt((int) stage.getScene().getWidth()
                - 2 * treasurePlacementLimitor) + treasurePlacementLimitor,
                rng.nextInt((int) stage.getScene().getHeight()
                        - 2 * treasurePlacementLimitor) + treasurePlacementLimitor, 2 * 2 + 2 + 1, justNegativeValue);
        negativeTreasure.setFill(Color.RED);
        negativeTreasure.visibleProperty().addListener(event -> {
            relocateTreasures();
        });
        backGround.getChildren().add(negativeTreasure);
        escapingTreasure = new EscapingTreasure(rng.nextInt((int) stage.getScene().getWidth()
                - 2 * treasurePlacementLimitor) + treasurePlacementLimitor,
                rng.nextInt((int) stage.getScene().getHeight()
                        - 2 * treasurePlacementLimitor) + treasurePlacementLimitor, 2 + 2 + 2);
        escapingTreasure.setFill(Color.BLUE);
        escapingTreasure.visibleProperty().addListener(event -> {
            relocateTreasures();
        });
        backGround.getChildren().add(escapingTreasure);
    }

    /***/
    private void removeTreasure() {
        List<Treasure> toRemove = new ArrayList<>();
        int count = 0;
        for (Node node: backGround.getChildren()) {
            if (node instanceof Treasure && !((Treasure) node).isVisible()) {
                toRemove.add((Treasure) node);
            } else if (node instanceof Treasure) {
                count++;
            }
        }
        backGround.getChildren().removeAll(toRemove);
        if (count <= 1) {
            relocateTreasures();
        }
    }

    /**
     * @param snakes - number of players.
     * @param name1 - name of 1. snake.
     * @param name2 - name of 2. snake.
     */
    public void start(int snakes, String name1, String name2) {
        Snake snake2 = new Snake(startingSnake2Pos[0], startingSnake2Pos[1], "south");
        if (snakes == 2) {
            backGround.getChildren().add(snake2);
            snake2.setMove(stage, false);
            snake2.setName(name2);
        }

        Snake snake = new Snake(startingSnake2Pos[1], startingSnake2Pos[1], "west");
        backGround.getChildren().add(snake);
        snake.setMove(stage, true);
        snake.setName(name1);

        Random rng = new Random();

        escapingTreasure = new EscapingTreasure(rng.nextInt((int) stage.getScene().getWidth()
                - 2 * treasurePlacementLimitor) + treasurePlacementLimitor,
                rng.nextInt((int) stage.getScene().getHeight()
                        - 2 * treasurePlacementLimitor) + treasurePlacementLimitor, 2 + 2 + 2);
        escapingTreasure.setFill(Color.BLUE);
        escapingTreasure.visibleProperty().addListener(event -> {
            relocateTreasures();
        });
        backGround.getChildren().add(escapingTreasure);

        Treasure treasure = new Treasure(rng.nextInt((int) stage.getScene().getWidth()
                - 2 * treasurePlacementLimitor) + treasurePlacementLimitor,
                rng.nextInt((int) stage.getScene().getHeight()
                        - 2 * treasurePlacementLimitor) + treasurePlacementLimitor, 2 + 2);
        treasure.visibleProperty().addListener(event -> {
            relocateTreasures();
        });
        backGround.getChildren().add(treasure);

        Treasure negativeTreasure = new Treasure(rng.nextInt((int) stage.getScene().getWidth()
                - 2 * treasurePlacementLimitor) + treasurePlacementLimitor,
                rng.nextInt((int) stage.getScene().getHeight()
                        - 2 * treasurePlacementLimitor) + treasurePlacementLimitor, 2 * 2 + 2 + 1, justNegativeValue);
        negativeTreasure.setFill(Color.RED);
        negativeTreasure.visibleProperty().addListener(event -> {
            relocateTreasures();
        });
        backGround.getChildren().add(negativeTreasure);


        timeStamp = System.currentTimeMillis();
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(SettingsValues.currentTickValue), event -> {
            addTimedMine();
            snake.move();
            if (snakes == 2) {
                snake2.move();
                escapingTreasure.move(snake2);
            }
            escapingTreasure.move(snake);
            removeTreasure();
            updateScore();
            isGameOver();
        }));
        timeline.play();
    }

    /***/
    private void addTimedMine() {
        if (((System.currentTimeMillis() - timeStamp)) % timeModula <= timeResult) {
            Random rng = new Random();
            Treasure treasure = new Treasure(rng.nextInt((int) stage.getScene().getWidth()
                    - 2 * treasurePlacementLimitor) + treasurePlacementLimitor,
                    rng.nextInt((int) stage.getScene().getWidth()
                            - 2 * treasurePlacementLimitor) + treasurePlacementLimitor,
                    2 + 2 + 1, rng.nextInt(2 + 2 + 1));
            treasure.setFill(Color.GREEN);
            backGround.getChildren().add(treasure);
            Timeline removeMineIn = new Timeline();
            removeMineIn.setCycleCount(1);
            removeMineIn.getKeyFrames().add(new KeyFrame(Duration.millis(treasureDissapearTime), event -> {
                treasure.getEaten();
            }));
            removeMineIn.play();
        }
    }

    /***/
    private void updateScore() {
        score = 0;
        for (Node node: backGround.getChildren()) {
            if (node instanceof Snake) {
                score += ((Snake) node).score;
            }
        }
        labelScore.setText("Score: " + score);
    }

    /***/
    private void isGameOver() {
        int numberOfSnakes = 0;
        int numberOfDeadSnakes = 0;
        for (Node node: backGround.getChildren()) {
            if (node instanceof Snake) {
                numberOfSnakes += 1;
                if (!((Snake) node).isAlive()) {
                    numberOfDeadSnakes += 1;
                }
            }
        }
        if (numberOfSnakes == numberOfDeadSnakes) {
            gameOver();
        }
    }

    /**
     * @return number of snakes.
     */
    private int countSnakes() {
        int count = 0;
        for (Node node: backGround.getChildren()) {
            if (node instanceof Snake) {
                count++;
            }
        }
        return count;
    }

    /**
     * @return names of the snakes.
     */
    private String getSnakesNames() {
        String names = "";
        for (Node node: backGround.getChildren()) {
            if (node instanceof Snake) {
                if (!((Snake) node).getName().equals("")) {
                    names += ((Snake) node).getName() + ", ";
                }
            }
        }
        return names;
    }

    /***/
    private void gameOver() {
        timeline.stop();
        labelGameOver.setVisible(true);
        long endTime = System.currentTimeMillis();
        HighScoreKeeper.writeToFile("Level_1, " + countSnakes() + " snakes, "
                + getSnakesNames() + "started " + (timeStamp / millisToSec) + ", ended "
                + (endTime / millisToSec) + ", lasting " + ((endTime - timeStamp) / millisToSec)
                + " seconds " + ":" + score);
        Timeline gameOverMenu = new Timeline();
        gameOverMenu.setCycleCount(1);
        gameOverMenu.getKeyFrames().add(new KeyFrame(Duration.millis(timeBeforeExit), event -> {
            returnToMenu();
        }));
        gameOverMenu.play();
    }

    /***/
    private void returnToMenu() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene menuController = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
        MenuController menu = fxmlLoader.getController();
        menu.setStage(stage);
        menu.startup();
        stage.setScene(menuController);
    }
}
