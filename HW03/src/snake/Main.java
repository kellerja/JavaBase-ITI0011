package snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Jaanus on 16.04.16.
 */
public class Main extends Application {
    /***/
    private static final int[] DIMENSIONS = new int[] {800, 600};

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent root = fxmlLoader.load();
        MenuController menu = fxmlLoader.getController();
        menu.setStage(primaryStage);
        menu.startup();
        Scene primaryScene = new Scene(root, DIMENSIONS[0], DIMENSIONS[1]);
        primaryStage.setTitle("HW03");
        primaryStage.setScene(primaryScene);
        primaryStage.show();
        SettingsValues.start();
    }

    /**
     * @param args - command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
