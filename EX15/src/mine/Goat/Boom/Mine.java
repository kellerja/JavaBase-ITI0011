package mine.Goat.Boom;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Jaanus on 15.04.16.
 */
public class Mine extends ImageView {
    /***/
    String minePicture = "C:\\Users\\Vahur Keller\\jkjava\\EX15\\src\\mine\\Goat\\Boom\\mine_armed.png";
    /***/
    String explosionPicture = "C:\\Users\\Vahur Keller\\jkjava\\EX15\\src\\mine\\Goat\\Boom\\explosion.png";
    /***/
    final double mineSizeX = 60;
    /***/
    final double mineSizeY = 60;
    /***/
    final double explosionSizeX = 2 * mineSizeX;
    /***/
    final double explosionSizeY = 2 * mineSizeY;
    /***/
    double mineValue = 1;
    /***/
    final double initialExplosionTimer = 3000;
    /***/
    double explosionTimer = initialExplosionTimer;
    /***/
    boolean hasExploded = false;
    /***/
    final double removeMineAfter = 2500;
    /***/
    boolean canBeRemoved = false;
    /***/
    final int damage = 1;

    /***/
    Mine() {
        try {
            setImage(new Image(new FileInputStream(minePicture), mineSizeX, mineSizeY, true, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * @param value - mine value.
     */
    Mine(double value) {
        try {
            setImage(new Image(new FileInputStream(minePicture), mineSizeX, mineSizeY, true, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mineValue = value;
    }
    /***/
    private void explode() {
        hasExploded = true;
        try {
            setImage(new Image(new FileInputStream(explosionPicture), explosionSizeX, explosionSizeY, true, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        removeMinePrep();
    }

    /***/
    private void removeMinePrep() {
        Timeline remove = new Timeline();
        remove.setCycleCount(1);
        remove.getKeyFrames().add(new KeyFrame(Duration.millis(removeMineAfter), event -> canBeRemoved = true));
        remove.play();
    }

    /***/
    public void setUpExplode() {
        Timeline explodeTimer = new Timeline();
        explodeTimer.setCycleCount(1);
        KeyFrame onExplosion = new KeyFrame(Duration.millis(explosionTimer), event -> explode());
        explodeTimer.getKeyFrames().add(onExplosion);
        explodeTimer.play();
    }
}
