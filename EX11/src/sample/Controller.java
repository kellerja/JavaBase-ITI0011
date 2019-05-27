package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

/***/
public class Controller {
    /***/
    @FXML
    private Button button;
    /***/
    @FXML
    private Label label;
    /***/
    @FXML
    private TextField textField;
    /***/
    @FXML
    private Circle circle;
    /***/

    /***/
    public void buttonClick() {
        String text = textField.getText();
        String[] info = text.split("[ ]+");
        double x;
        double y;
        double r;
        circle.setVisible(false);
        label.setVisible(true);
        if (info.length == 2 + 1) {
            try {
                x = Double.parseDouble(info[0]);
                y = Double.parseDouble(info[1]);
                r = Double.parseDouble(info[2]);
                circle.setLayoutX(x);
                circle.setLayoutY(y);
                circle.setRadius(r);
                circle.setVisible(true);
                //label.setVisible(false);
            } catch (NumberFormatException e) {
            }
        }
        label.setText(text);
    }
}
