package snake;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Jaanus on 23.04.16.
 */
public class HighScoreController {
    /***/
    @FXML
    private VBox vBoxOverallShowcase;
    /***/
    @FXML
    private VBox vBoxOverallFilter;
    /***/
    @FXML
    private VBox vBoxLevel1Showcase;
    /***/
    @FXML
    private VBox vBoxLevel1Filter;
    /***/
    @FXML
    private VBox vBoxLevel2Showcase;
    /***/
    @FXML
    private VBox vBoxLevel2Filter;
    /***/
    private Stage stage;

    /***/
    public void returnToMenu() {
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

    /**
     * @param stage - set stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /***/
    private void fillViews() {
        int generalCount = 0;
        int level1Count = 0;
        int level2Count = 0;
        for (String score: HighScoreKeeper.highScores) {
            generalCount++;
            String[] info = score.split(":");
            Label label = new Label();
            label.setText(generalCount + ". " + score.replace("[ ]+", " "));
            vBoxOverallShowcase.getChildren().add(label);
            if (info[0].split("[, ]+")[0].equals("Level_1")) {
                level1Count++;
                Label labelLevel1 = new Label();
                labelLevel1.setText(level1Count + ". " + score.replace("[ ]+", " "));
                vBoxLevel1Showcase.getChildren().add(labelLevel1);
            } else if (info[0].split("[, ]+")[0].equals("Level_2")) {
                level2Count++;
                Label labelLevel2 = new Label();
                labelLevel2.setText(level2Count + ". " + score.replace("[ ]+", " "));
                vBoxLevel2Showcase.getChildren().add(labelLevel2);
            }
        }
    }

    /***/
    public void startup() {
        try {
            HighScoreKeeper.readScores();
            fillViews();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
