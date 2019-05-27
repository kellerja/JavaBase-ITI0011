import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Jaanus Keller on 26.02.16.
 */
public class EX05 {
    /**
     * @param inputFilename - name of the file with data
     * @param outputFilename - name of file where to write data with better style
     * @return count of correct lines
     */
    public static int convert(String inputFilename, String outputFilename) {
        if (inputFilename == null || outputFilename == null) {
            return 0;
        }
        String formattedText;
        String line;
        int count = 0;
        int pipeCount;
        BufferedReader reader;
        BufferedWriter writer;
        try {
            reader = Files.newBufferedReader(Paths.get(inputFilename));
            writer = Files.newBufferedWriter(Paths.get(outputFilename));
            line = reader.readLine();
            while (line != null) {
                line = line.replace("\uFEFF", "");
                formattedText = getNicelyFormattedMovie(line);
                do {
                    pipeCount = 0;
                    line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == '|') {
                            pipeCount += 1;
                        }
                    }
                } while (pipeCount != 2 + 1);
                // System.out.println(formattedText + "\n" + line);
                if (line != null && formattedText != null) {
                    formattedText += "\n\n";
                }
                if (formattedText != null) {
                    count += 1;
                    formattedText = formattedText.replaceAll("\n", System.lineSeparator());
                    writer.write(formattedText);
                }
                //System.out.print(formattedText);
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(count);
        return count;
    }
    /**
     * @param movieLine - movie data on single line
     * @return movie data with a better style
     */
    public static String getNicelyFormattedMovie(String movieLine) {
        //System.out.println(movieLine);
        if (movieLine == null) {
            return null;
        }
        //movieLine += " ";
        String formattedText;
        String[] lineSplit = movieLine.split("\\|");
        if (lineSplit.length != 2 + 2) {
            return null;
        }
        //lineSplit[2 + 1] = lineSplit[2 + 1].substring(0, lineSplit[2 + 1].length() - 1);
        String[] date = lineSplit[0].split("-");
        //System.out.println(movieLine);
        if (date.length != 2 + 1) {
            return null;
        }
        try {
            Integer.parseInt(date[2]);
            Integer.parseInt(date[1]);
            Integer.parseInt(date[0]);
            //Double.parseDouble(lineSplit[2 + 1]);
        } catch (NumberFormatException e) {
            return null;
        }
        formattedText = lineSplit[1]
                + "\nRelease date: " + date[2] + "/" + date[1] + "/" + date[0]
                + "\nDescription: " + lineSplit[2]
                + "\nAverage rating: " + lineSplit[2 + 1];
        return formattedText;
    }
    /**
     * program entry point.
     * @param args - arguments from the command line
     */
    public static void main(String[] args) {
        convert("C:\\Users\\Vahur Keller\\Desktop\\MoviesNoPipe.txt",
                "C:\\Users\\Vahur Keller\\Desktop\\MoviesOut.txt");
    }
}
