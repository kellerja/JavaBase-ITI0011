package snake;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Jaanus on 23.04.16.
 */
public class HighScoreKeeper {
    /***/
    private static String highScoresFile = "C:\\Users\\Vahur Keller\\jkjava\\HW03\\src\\snake\\highscores.txt";
    /***/
    public static SortedSet<String> highScores = new TreeSet<>(new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            String s1 = (String) o1;
            String s2 = (String) o2;
            String[] i1 = s1.split(":");
            String[] i2 = s2.split(":");
            if (Integer.parseInt(i1[1]) <= Integer.parseInt(i2[1])) {
                return 1;
            } else {
                return -1;
            }
        }
    });

    /**
     * @throws IOException if could not locate file or could not read from file.
     */
    public static void readScores() throws IOException {
        highScores = new TreeSet<>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                String s1 = (String) o1;
                String s2 = (String) o2;
                String[] i1 = s1.split(":");
                String[] i2 = s2.split(":");
                if (Integer.parseInt(i1[1]) <= Integer.parseInt(i2[1])) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        BufferedReader reader = new BufferedReader(new FileReader(highScoresFile));
        String line;
        while ((line = reader.readLine()) != null) {
            highScores.add(line);
        }
        reader.close();
    }

    /***/
    public static void clearHighScores() {
        try {
            PrintWriter printWriter = new PrintWriter(highScoresFile);
            printWriter.close();
            readScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param settings - options the game was played with.
     */
    public static void writeToFile(String settings) {
        try {
            readScores();
            highScores.add(settings.replace("[ ]+", " "));
            PrintWriter printWriter = new PrintWriter(highScoresFile);
            for (String value: highScores) {
                printWriter.print(value + System.lineSeparator());
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***/
    public static void start() {
        try {
            readScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
