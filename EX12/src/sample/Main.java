package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/***/
public class Main extends Application {
    /***/
    private static final int[] DIMENSIONS = new int[] {800, 600};

    @Override
    public final void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("EX12");
        primaryStage.setScene(new Scene(root, DIMENSIONS[0], DIMENSIONS[1]));
        primaryStage.show();
    }

    /**
     * @param args - command line argumwnts.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
