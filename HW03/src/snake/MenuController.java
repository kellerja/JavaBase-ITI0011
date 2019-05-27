package snake;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Jaanus on 16.04.16.
 */
public class MenuController {
    /***/
    private Stage stage;
    /***/
    @FXML
    private Slider levelSelect;
    /***/
    @FXML
    private Label levelLabel;
    /***/
    @FXML
    private Label snakesNumberLabel;
    /***/
    @FXML
    private Slider snakesNumberScrollBar;
    /***/
    @FXML
    private TextField snake1Name;
    /***/
    @FXML
    private HBox nameHBox;
    /***/
    private Label snake2NameLabel = new Label(" Snake 2 name: ");
    /***/
    private TextField snake2Name = new TextField();

    /***/
    public void startButton() {
        if (levelSelect.getValue() == 0) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stage1.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene nextLevel = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
            Stage1 gameLevel = fxmlLoader.getController();
            gameLevel.setStage(stage);
            stage.setScene(nextLevel);
            int numberOfPlayers = (int) snakesNumberScrollBar.getValue();
            gameLevel.start(numberOfPlayers, snake1Name.getText(), snake2Name.getText());
        } else if (levelSelect.getValue() == 2) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stage2.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene nextLevel = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
            Stage2 gameLevel = fxmlLoader.getController();
            gameLevel.setStage(stage);
            stage.setScene(nextLevel);
            int numberOfPlayers = (int) snakesNumberScrollBar.getValue();
            gameLevel.start(numberOfPlayers, snake1Name.getText(), snake2Name.getText());
        }
    }

    /***/
    public void settingsButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene settings = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
        Settings settingsController = fxmlLoader.getController();
        settingsController.setStage(stage);
        settingsController.startup();
        stage.setScene(settings);
    }

    /***/
    public  void highScoresButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("highScoreView.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene highScores = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
        HighScoreController highScoreController = fxmlLoader.getController();
        highScoreController.setStage(stage);
        highScoreController.startup();
        stage.setScene(highScores);
    }

    /***/
    public void exitButton() {
    }

    /**
     * @param stage - stage the controller runs on.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /***/
    public void startup() {
        levelSelect.valueProperty().addListener(event -> {
            if (levelSelect.getValue() == 0) {
                levelLabel.setText("Level 1");
            } else if (levelSelect.getValue() == 2) {
                levelLabel.setText("Level 2");
            }
        });
        snakesNumberScrollBar.valueProperty().addListener(event -> {
            snakesNumberLabel.setText("Snakes: " + (int) snakesNumberScrollBar.getValue());
            if ((int) snakesNumberScrollBar.getValue() == 2) {
                nameHBox.getChildren().add(snake2NameLabel);
                nameHBox.getChildren().add(snake2Name);
            } else {
                nameHBox.getChildren().remove(snake2NameLabel);
                nameHBox.getChildren().remove(snake2Name);
            }
        });
    }
}
