package cookieclicker;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Created by Jaanus on 3.04.16.
 */
public class Controller {
    /***/
    @FXML
    private Button clickerButton;
    /***/
    @FXML
    private Button cursorButton;
    /***/
    @FXML
    private Button infoButton;
    /***/
    @FXML
    private Label labelCookieCount;
    /***/
    @FXML
    private Label labelCursorsCount;
    /***/
    @FXML
    private Label labelClickersCount;
    /***/
    @FXML
    private ImageView cookie;
    /***/
    private long cookieCount = 0;
    /***/
    private final int cursorIncrease = 20;
    /***/
    private final int clickerIncrease = 200;
    /***/
    private int currentCursorValue = cursorIncrease;
    /***/
    private final int startingClickerValue = 100;
    /***/
    private int currentClickerValue = startingClickerValue;
    /***/
    private int cursorCount = 1;
    /***/
    private int clickerCount = 0;
    /***/
    private final int defaultClickValue = 1;
    /***/
    private final int defaultAutoValue = 1;
    /***/
    private final double defaultClickerAddCookiesDelay = 5000;
    /***/
    private final double timeDelayDecrease = 100;
    /***/
    private final double lowestTimeDelay = 1000;
    /***/
    private double currentClickerAddCookiesDelay = defaultClickerAddCookiesDelay + timeDelayDecrease;
    /***/
    private Timeline timeline = new Timeline();
    /***/
    @FXML
    private VBox vBoxBackground;

    /***/
    private void timeFunctions() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame autoAddCookies = new KeyFrame(Duration.millis(currentClickerAddCookiesDelay),
                event -> { increaseCookieCountAuto(); });
        timeline.getKeyFrames().add(autoAddCookies);
        timeline.play();
    }

    /***/
    private void changeBackground() {
        Timeline background = new Timeline();
        background.setCycleCount(Timeline.INDEFINITE);
        background.setAutoReverse(true);
        KeyValue kvBg = new KeyValue(vBoxBackground.backgroundProperty(),
                new Background(new BackgroundFill(new Color(0, 0, 0, 0),
                        new CornerRadii(0), new Insets(0, 0, 0, 0))));
        KeyFrame changeBackgroundColor = new KeyFrame(Duration.millis(currentClickerAddCookiesDelay), kvBg);
        background.getKeyFrames().add(changeBackgroundColor);
    }

    /***/
    public final void buyClicker() {
        if (cookieCount >= currentClickerValue) {
            cookieCount -= currentClickerValue;
            currentClickerValue += clickerIncrease;
            clickerCount += 1;
            decreaseTimeDelay();
            updateLabelCookieCount();
            updateLabelClickerCount();
            updateButtonLabelCursorValue();
            updateButtonLabelClickerValue();
            timeline.stop();
            timeFunctions();
        }
    }

    /***/
    private void decreaseTimeDelay() {
        if (currentClickerAddCookiesDelay >= lowestTimeDelay) {
            currentClickerAddCookiesDelay -= timeDelayDecrease;
        }
    }

    /***/
    public final void buyCursor() {
        if (cookieCount >= currentCursorValue) {
            cookieCount -= currentCursorValue;
            currentCursorValue += cursorIncrease;
            cursorCount += 1;
            updateLabelCookieCount();
            updateButtonLabelCursorValue();
            updateLabelCursorCount();
            updateButtonLabelClickerValue();
        }
    }

    /**
     * @throws IOException when unable to open new scene.
     */
    public final void showInfoBox() throws IOException {
        Parent infoStyle = FXMLLoader.load(getClass().getResource("infoBox.fxml"));
        InfoBox.showInfoBoxPopup(infoStyle);
    }

    /***/
    public final void increaseCookieCount() {
        cookieCount += defaultClickValue * cursorCount;
        updateLabelCookieCount();
        updateButtonLabelClickerValue();
        updateButtonLabelCursorValue();
    }

    /***/
    private void increaseCookieCountAuto() {
        cookieCount += defaultAutoValue * clickerCount;
        updateLabelCookieCount();
    }

    /***/
    private void updateLabelCookieCount() {
        labelCookieCount.setText("Cookies: " + cookieCount);
    }

    /***/
    private void updateLabelClickerCount() {
        labelClickersCount.setText("Clickers: " + clickerCount);
    }

    /***/
    private void updateLabelCursorCount() {
        labelCursorsCount.setText("Cursors: " + cursorCount);
    }

    /***/
    private void updateButtonLabelClickerValue() {
        if (currentClickerAddCookiesDelay > lowestTimeDelay) {
            if (!clickerButton.isVisible() && cookieCount >= currentClickerValue) {
                clickerButton.setVisible(true);
                clickerButton.setDisable(false);
            } else if (cookieCount >= currentClickerValue) {
                clickerButton.setDisable(false);
            } else if (cookieCount < currentClickerValue) {
                clickerButton.setDisable(true);
            }
            clickerButton.setText("Clicker: " + currentClickerValue);
        } else {
            clickerButton.setText("Max Clickers Amount");
            clickerButton.setDisable(true);
        }
    }

    /***/
    private void updateButtonLabelCursorValue() {
        if (!cursorButton.isVisible() && cookieCount >= currentCursorValue) {
            cursorButton.setVisible(true);
            cursorButton.setDisable(false);
        } else if (cookieCount >= currentCursorValue) {
            cursorButton.setDisable(false);
        } else if (cookieCount < currentCursorValue) {
            cursorButton.setDisable(true);
        }
        cursorButton.setText("Cursor: " + currentCursorValue);
    }
}
