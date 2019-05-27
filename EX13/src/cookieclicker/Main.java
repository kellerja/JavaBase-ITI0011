package cookieclicker;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by Jaanus on 3.04.16.
 */
public class Main extends Application {
    /***/
    private static final int[] DIMENSIONS = new int[] {800, 600};
    /***/
    private Scene scene;
    /***/
    private final int initalBgChangeValue = 5000;
    /***/
    private final Color initialColor = Color.WHITE;
    /***/
    private final Color endColor = Color.BLACK;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gameView.fxml"));
        primaryStage.setTitle("EX13");
        Scene primaryScene = new Scene(root, DIMENSIONS[0], DIMENSIONS[1]);
        primaryStage.setScene(primaryScene);
        primaryStage.setResizable(false);
        primaryScene.setFill(initialColor);
        scene = primaryScene;
        changeBackground();
        primaryStage.show();
    }

    /***/
    private void changeBackground() {
        Timeline background = new Timeline();
        background.setCycleCount(Timeline.INDEFINITE);
        background.setAutoReverse(true);
        KeyValue kvBg = new KeyValue(scene.fillProperty(), endColor);
        KeyFrame changeBackgroundColor = new KeyFrame(Duration.millis(initalBgChangeValue), kvBg);
        background.getKeyFrames().add(changeBackgroundColor);
        background.play();
    }

    /**
     * @param args - command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
