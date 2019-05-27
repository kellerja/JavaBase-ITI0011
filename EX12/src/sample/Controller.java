package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***/
public class Controller {
    /***/
    @FXML
    private StackPane scene;
    /***/
    @FXML
    private TextField text;
    /***/
    @FXML
    private Button button;
    /***/
    @FXML
    private LineChart chart;
    /***/
    private String formula = "x";
    /***/
    private String defaultFileLocation = "C:\\Users\\Vahur Keller\\Desktop\\ITI0011-2016_EX12_Data.txt";
    /***/
    private double start = 0;
    /***/
    private double end = 2;
    /***/
    private double step = 1;
    /***/
    @FXML
    private TextField startField;
    /***/
    @FXML
    private TextField endField;
    /***/
    @FXML
    private TextField stepField;
    /***/
    @FXML
    private ToggleButton showDots;

    /***/
    public final void buttonSetStartEndStep() {
        if (startField.getText() != null) {
            try {
                start = Double.parseDouble(startField.getText());
            } catch (NumberFormatException e) {
            }
        }
        if (endField.getText() != null) {
            try {
                end = Double.parseDouble(endField.getText());
            } catch (NumberFormatException e) {
            }
        }
        if (stepField.getText() != null) {
            try {
                step = Double.parseDouble(stepField.getText());
                if (step == 0) {
                    step = 1;
                }
            } catch (NumberFormatException e) {
            }
        }
        if (start > end && step > 0) {
            step = -step;
        } else if (start < end && step < 0) {
            step = -step;
        }
    }

    /***/
    public final void drawGraphFromFunction() {
        updateChart(null, false);
    }

    /***/
    public final void buttonClick() {
        String formulaa = parseFormula(text.getText().replaceAll("[ \\\\^]+", ""));
        if (formulaa != null) {
            this.formula = formulaa;
        }
    }

    /**
     * @param x coordinate to calculate y.
     * @return y value.
     */
    private double calcYValue(double x) {
        double y = 0, temp = 0;
        char eval;
        String f = formula;
        List<String> numberGroups = new ArrayList<>();
        //Pattern grouping = Pattern.compile("[0123456789]*x*[^+-][0123456789]*");
        Pattern grouping = Pattern.compile("[0123456789]*[.0123456789]*x*[^+-][0123456789]*[.0987654321]*");
        Matcher mGrouping = grouping.matcher(formula);
        while (mGrouping.find()) {
            numberGroups.add(mGrouping.group());
            f = f.replace(mGrouping.group(), "A");
        }
        int j = numberGroups.size() - 1;
        String[] t;
        for (int i = f.length() - 1; i >= 0; i--) {
            eval = f.charAt(i);
            if (eval == 'A') {
                t = numberGroups.get(j).split("x");
                if (t.length == 2) {
                    if (!t[0].equals("")) {
                        temp = Double.parseDouble(t[0]) * Math.pow(x, Double.parseDouble(t[1]));
                    } else {
                        temp = Math.pow(x, Double.parseDouble(t[1]));
                    }
                } else if (numberGroups.get(j).charAt(0) == 'x') {
                    if (numberGroups.get(j).length() > 1) {
                        temp = Math.pow(x, Double.parseDouble(t[0]));
                    } else {
                        temp = x;
                    }
                } else if (numberGroups.get(j).charAt(numberGroups.get(j).length() - 1) == 'x') {
                    temp = Double.parseDouble(t[0]) * x;
                } else {
                    temp = Double.parseDouble(t[0]);
                }
                j--;
            } else if (eval == '+') {
                y += temp;
                temp = 0;
            } else if (eval == '-') {
                y -= temp;
                temp = 0;
            }
        }
        y += temp;
        return y;
    }

    /**
     * @param formulaa of y coordinate.
     * @return formula for further use.
     */
    private String parseFormula(String formulaa) {
        Pattern p = Pattern.compile("[^1234567890.+-\\\\*/x]");
        Matcher m = p.matcher(formulaa);
        if (m.find()) {
            return null;
        }
        return formulaa;
    }

    /**
     * @param yValues for chart.
     * @param isDataChart boolean on if chart to be drawn is random func.
     */
    private void updateChart(double[] yValues, boolean isDataChart) {
        Insets margin = scene.getMargin(chart);
        scene.getChildren().remove(chart);
        XYChart.Series series = new XYChart.Series();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setForceZeroInRange(false);
        if (isDataChart) {
            for (int x = 0; x < yValues.length; x++) {
                series.getData().add(new XYChart.Data(String.format("%02d:00", x), yValues[x]));
            }
            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Time");
            yAxis.setLabel("Rating");
            chart = new LineChart(xAxis, yAxis);
        } else {
            if (step > 0) {
                for (double x = start; x <= end; x += step) {
                    series.getData().add(new XYChart.Data(x, calcYValue(x)));
                }
            } else {
                for (double x = start; x >= end; x += step) {
                    series.getData().add(new XYChart.Data(x, calcYValue(x)));
                }
            }
            NumberAxis xAxis = new NumberAxis();
            xAxis.setLabel("x");
            yAxis.setLabel("y = " + formula);
            chart = new LineChart(xAxis, yAxis);
        }
        chart.setCreateSymbols(showDots.isSelected());
        chart.getData().add(series);
        chart.setLegendVisible(false);
        scene.getChildren().add(chart);
        scene.setMargin(chart, margin);
    }

    /**
     * @param filename filename with data.
     * @return file data as String.
     * @throws IOException when unable to read file.
     */
    public static String readFile(String filename) throws IOException {
        String result = "";
        if (filename == null || filename.replaceAll("[ ]+", "").equals("")) {
            return null;
        }
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            result += line;
        }
        return result;
    }

    /**
     * @param data from file  to be made into correct format
     * @return parsed data.
     */
    private static double[] parseFileData(String data) {
        if (data == null || data.replaceAll("[ ]+", "").equals("")) {
            return null;
        }
        String[] dataArray = data.split(";");
        double[] dataToDouble = new double[dataArray.length];
        for (int i = 0; i < dataArray.length; i++) {
            try {
                dataToDouble[i] = Double.parseDouble(dataArray[i]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return dataToDouble;
    }

    /**
     * @throws IOException when unable to read popup fxml file.
     */
    public final void menuDataFile() throws IOException {
        Parent popupRoot = FXMLLoader.load(getClass().getResource("popup.fxml"));
        double[] chartInfo;
        String fileName;
        try {
            fileName = Popup.window(popupRoot);
            if (fileName != null && !fileName.equals("")) {
                chartInfo = parseFileData(readFile(fileName));
                if (chartInfo != null) {
                    updateChart(chartInfo, true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***/
    public final void menuDefaultDataFile() {
        double[] chartInfo;
        try {
            chartInfo = parseFileData(readFile(defaultFileLocation));
            if (chartInfo != null) {
                updateChart(chartInfo, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
