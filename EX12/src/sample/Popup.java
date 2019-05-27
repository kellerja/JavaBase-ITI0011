package sample;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by Jaanus on 2.04.16.
 */
public class Popup {
    /***/
    private static String fileLocation;
    /***/
    @FXML
    private TextField text;
    /***/
    @FXML
    private Button submit;
    /***/
    @FXML
    private Button browseFiles;
    /***/
    private static Stage popup;
    /***/
    private static final int[] DIMENTIONS = new int[] {300, 50};

    /**
     * @param root fxml file.
     * @return file address.
     */
    public static String window(Parent root) {
        popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("File location");
        popup.setScene(new Scene(root, DIMENTIONS[0], DIMENTIONS[1]));
        popup.showAndWait();
        return fileLocation;
    }

    /***/
    public final void submitButtonPress() {
        fileLocation = text.getText();
        popup.close();
    }

    /***/
    public final void browseButtonPress() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File file = fileChooser.showOpenDialog(popup);
        if (file != null) {
            text.setText(file.getAbsolutePath());
        }
    }
}
