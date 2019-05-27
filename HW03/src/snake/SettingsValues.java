package snake;

import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Created by Jaanus on 18.04.16.
 */
public class SettingsValues {
    /***/
    private static String settingsFileName = "C:\\Users\\Vahur Keller\\jkjava\\HW03\\src\\snake\\settings.txt";
    /***/
    private static final int DEFAULT_TICK_VALUE = 30;
    /***/
    public static int currentTickValue = DEFAULT_TICK_VALUE;
    /***/
    private static final Color DEFAULT_SNAKE_COLOR = Color.BLACK;
    /***/
    public static Color currentSnakeColor = DEFAULT_SNAKE_COLOR;
    /***/
    private static final int DEFAULT_SNAKE_WIDTH = 5;
    /***/
    public static int currentSnakeWidth = DEFAULT_SNAKE_WIDTH;
    /***/
    private static final int DEFAULT_SNAKE_LENGTH_MODIFIER = 30;
    /***/
    public static int currentSnakeLengthModifier = DEFAULT_SNAKE_LENGTH_MODIFIER;
    /***/
    private static final int DEFAULT_SNAKE_MOVEMENT_RADIUS = 1;
    /***/
    public static int currentSnakeMovementRadius = DEFAULT_SNAKE_MOVEMENT_RADIUS;
    /***/
    private static final double DEFAULT_SNAKE_TURN_RADIUS = 10;
    /***/
    public static double currentSnakeTurnRadius = DEFAULT_SNAKE_TURN_RADIUS;
    /***/
    private static final double DEFAULT_SNAKE_SPEED = 5;
    /***/
    public static double currentSnakeSpeed = DEFAULT_SNAKE_SPEED;
    /***/
    private static final double DEFAULT_SNAKE_FITTEN_SPEED = 0.5;
    /***/
    public static double currentSnakeFittenSpeed = DEFAULT_SNAKE_FITTEN_SPEED;
    /***/
    private static final double DEFAULT_SNAKE_FATTEN_SPEED = 3;
    /***/
    public static double currentSnakeFattenSpeed = DEFAULT_SNAKE_FATTEN_SPEED;

    /**
     * @throws IOException if could not locate file or could not read from file.
     */
    private static void readSettings() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(settingsFileName));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] command = line.trim().split(":");
            switch (command[0]) {
                case "currentTickValue":
                    currentTickValue = Integer.parseInt(command[1]);
                    break;
                case "currentSnakeColor":
                    currentSnakeColor = Color.web(command[1]);
                    break;
                case "currentSnakeWidth":
                    currentSnakeWidth = Integer.parseInt(command[1]);
                    break;
                case "currentSnakeLengthModifier":
                    currentSnakeLengthModifier = Integer.parseInt(command[1]);
                    break;
                case "currentSnakeMovementRadius":
                    currentSnakeMovementRadius = Integer.parseInt(command[1]);
                    break;
                case "currentSnakeTurnRadius":
                    currentSnakeTurnRadius = Double.parseDouble(command[1]);
                    break;
                case "currentSnakeSpeed":
                    currentSnakeSpeed = Double.parseDouble(command[1]);
                    break;
                case "currentSnakeFittenSpeed":
                    currentSnakeFittenSpeed = Double.parseDouble(command[1]);
                    break;
                case "currentSnakeFattenSpeed":
                    currentSnakeFattenSpeed = Double.parseDouble(command[1]);
                    break;
                default:
                    break;
            }
        }
        reader.close();
    }

    /***/
    public static void writeToFile() {
        try {
            PrintWriter printWriter = new PrintWriter(settingsFileName);
            printWriter.println("currentTickValue:" + currentTickValue);
            printWriter.println("currentSnakeColor:" + currentSnakeColor.toString());
            printWriter.println("currentSnakeWidth:" + currentSnakeWidth);
            printWriter.println("currentSnakeLengthModifier:" + currentSnakeLengthModifier);
            printWriter.println("currentSnakeMovementRadius:" + currentSnakeMovementRadius);
            printWriter.println("currentSnakeTurnRadius:" + currentSnakeTurnRadius);
            printWriter.println("currentSnakeSpeed:" + currentSnakeSpeed);
            printWriter.println("currentSnakeFittenSpeed:" + currentSnakeFittenSpeed);
            printWriter.println("currentSnakeFattenSpeed:" + currentSnakeFattenSpeed);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***/
    public static void start() {
        try {
            readSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
