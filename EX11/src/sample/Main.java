package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/***/
public class Main extends Application {

    /***/
    private final int[] dimensions = new int[]{300, 275};

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("alt.fxml"));
        primaryStage.setTitle("EX11");
        primaryStage.setScene(new Scene(root, dimensions[0], dimensions[1]));
        primaryStage.show();
    }

    /**
     * @param args - command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
