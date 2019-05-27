package cookieclicker;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Jaanus on 6.04.16.
 */
public class InfoBox {
    /***/
    private static Stage infoBox;
    /***/
    private static final int[] DIMENSIONS = new int[] {300, 200};
    /***/
    @FXML
    private Label infoLabel;


    /**
     * @param info stylesheet.
     */
    public static void showInfoBoxPopup(Parent info) {
        infoBox = new Stage();
        infoBox.setTitle("Info");
        infoBox.setScene(new Scene(info, DIMENSIONS[0], DIMENSIONS[1]));
        infoBox.initModality(Modality.APPLICATION_MODAL);
        infoBox.setResizable(false);
        infoBox.show();
    }

    /***/
    public final void closeScene() {
        infoBox.close();
    }
}
