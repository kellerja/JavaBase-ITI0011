package snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Created by Jaanus on 16.04.16.
 */
public class Settings {
    /***/
    private Stage stage;
    /***/
    @FXML
    private Label errorSnakeLengthModifier;
    /***/
    @FXML
    private Label errorSnakeWidth;
    /***/
    @FXML
    private Label labelSnakeTurnAngleValue;
    /***/
    @FXML
    private Label errorSnakeTurnAngle;
    /***/
    @FXML
    private Label labelSnakeSpeedValue;
    /***/
    @FXML
    private Label errorSnakeSpeed;
    /***/
    @FXML
    private Label errorSnakeColor;
    /***/
    @FXML
    private Label errorPreview;
    /***/
    @FXML
    private TextField fieldSnakeLengthModifier;
    /***/
    @FXML
    private TextField fieldSnakeWidth;
    /***/
    @FXML
    private Slider sliderTurnAngle;
    /***/
    @FXML
    private Slider sliderSnakeSpeed;
    /***/
    @FXML
    private ColorPicker colorPicker;
    /***/
    @FXML
    private Pane preview;
    /***/
    private Snake previewSnake;
    /***/
    private Timeline timeline;
    /***/
    private final double[] previewSnakePos = {75, 10};

    /**
     * @param stage - stage,
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /***/
    public void cancel() {
        timeline.stop();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene menu = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
        MenuController menuController = fxmlLoader.getController();
        menuController.setStage(stage);
        menuController.startup();
        stage.setScene(menu);
    }

    /***/
    public void clearHighScores() {
        HighScoreKeeper.clearHighScores();
    }

    /***/
    public void apply() {
        SettingsValues.currentSnakeColor = colorPicker.getValue();
        SettingsValues.currentSnakeTurnRadius = sliderTurnAngle.getValue();
        SettingsValues.currentSnakeSpeed = sliderSnakeSpeed.getValue();
        SettingsValues.currentSnakeLengthModifier = Integer.parseInt(fieldSnakeLengthModifier.getText());
        SettingsValues.currentSnakeWidth = Integer.parseInt(fieldSnakeWidth.getText());
        SettingsValues.writeToFile();
    }

    /***/
    public void startup() {
        fieldSnakeLengthModifier.setText(Integer.toString(SettingsValues.currentSnakeLengthModifier));
        fieldSnakeWidth.setText(Integer.toString(SettingsValues.currentSnakeWidth));
        sliderTurnAngle.setValue(SettingsValues.currentSnakeTurnRadius);
        labelSnakeTurnAngleValue.setText(String.format("%.2f", SettingsValues.currentSnakeTurnRadius));
        sliderSnakeSpeed.setValue(SettingsValues.currentSnakeSpeed);
        labelSnakeSpeedValue.setText(String.format("%.2f", SettingsValues.currentSnakeSpeed));
        colorPicker.setValue(SettingsValues.currentSnakeColor);
        previewSnake = new Snake(previewSnakePos[0], previewSnakePos[1], "west");
        preview.getChildren().add(previewSnake);
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(SettingsValues.currentTickValue), event -> {
            previewSnake.previewMove();
        }));
        timeline.play();
        fieldSnakeLengthModifier.textProperty().addListener(event -> {
            timeline.stop();
            previewSnake.setSnakeLengthModifier(Integer.parseInt(fieldSnakeLengthModifier.getText()));
            timeline.play();
        });
        sliderTurnAngle.valueProperty().addListener(event -> {
            labelSnakeTurnAngleValue.setText(String.format("%.2f", sliderTurnAngle.getValue()));
            timeline.stop();
            previewSnake.setSnakeTurnRadius(sliderTurnAngle.getValue());
            timeline.play();
        });
        sliderSnakeSpeed.valueProperty().addListener(event -> {
            labelSnakeSpeedValue.setText(String.format("%.2f", sliderSnakeSpeed.getValue()));
            timeline.stop();
            previewSnake.setSnakeSpeed(sliderSnakeSpeed.getValue());
            timeline.play();
        });
        colorPicker.valueProperty().addListener(event -> {
            timeline.stop();
            previewSnake.setStroke(colorPicker.getValue());
            timeline.play();
        });
        fieldSnakeWidth.textProperty().addListener(event -> {
            timeline.stop();
            previewSnake.setStrokeWidth(Integer.parseInt(fieldSnakeWidth.getText()));
            timeline.play();
        });
    }
}
