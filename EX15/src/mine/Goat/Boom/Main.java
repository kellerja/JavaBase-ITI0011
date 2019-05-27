package mine.Goat.Boom;
/**
 * Created by Jaanus on 8.04.16.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/***/
public class Main extends Application {
    /***/
    private static final int[] DIMENSIONS = new int[] {800, 600};

    /**
     * @param args  command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
        Scene primaryScene = new Scene(root, DIMENSIONS[0], DIMENSIONS[1]);
        primaryStage.setTitle("EX15");
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
}
