import java.util.Arrays;
import java.util.HashMap;

/**
 * The farmer who does some hard work and earns money.
 */
public class Farmer {
    /***/
    private String name;
    /***/
    private HashMap<Integer, Integer> sellingResults;
    /***/
    private final int days = 7;

    /**
     * Constructor.
     * @param name - farmer name
     * @param pastWeekSales - sales of the past week.
     *                      Key 1 indicates Monday, 7 indicates Sunday.
     */
    public Farmer(String name, HashMap<Integer, Integer> pastWeekSales) {
        this.name = name;
        this.sellingResults = pastWeekSales;
    }


    /**
     * Gets the name of the farmer.
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get selling results for the last week.
     * @return HashMap, see constructor for description.
     */
    public HashMap<Integer, Integer> getSellingResults() {
        return sellingResults;
    }

    /**
     * Converts earned money HashMap to int array, which contains only amounts earned in the same order
     * as they were earned.
     * @param sellingResultsHashMap - earnings hashmap of the past week,
     *                              see constructor for description.
     * @return - array of ints of earned money (with length of 7) or null.
     */
    public int[] sortTheMess(HashMap<Integer, Integer> sellingResultsHashMap) {
        if (sellingResultsHashMap == null || sellingResultsHashMap.size() != days) return null;
        int[] resultsArray = new int[sellingResultsHashMap.size()];
        int idx = 0;
        for (Integer entry: sellingResultsHashMap.values()) {
            resultsArray[idx++] = entry;
        }
        return resultsArray;
    }

    /**
     * Puts earned money amounts into order.
     * Notice, this is a static method which means
     * this is a general method, not connected to the farmer instance.
     * @param messyArray - unsorted array of ints
     * @return - sorted array of ints
     */
    public static int[] orderEarnings(int[] messyArray) {
        if (messyArray == null) return null;
        Arrays.sort(messyArray);
        return messyArray;
    }

    /**
     * Finds the max value from the earnings.
     * @param numArray - sorted or not sorted array of ints
     * @return - max earning during the past week.
     * 0 in the case max cannot be found.
     */
    public static int findTheMax(int[] numArray) {
        if (numArray == null) return 0;
        int max = 0;
        for (int entry: numArray) {
            if (entry > max) {
                max = entry;
            }
        }
        return max;
    }

    /**
     * Finds average value from the earnings.
     * @param numArray - sorted or not sorted array of ints
     * @return - avg earning of the past week.
     * 0 in the case average cannot be found.
     */
    public static double findAvg(int[] numArray) {
        if (numArray == null || numArray.length == 0) return 0;
        double sum = 0;
        for (int entry: numArray) {
            sum += entry;
        }
        return sum / numArray.length;
    }

    /**
     * Puts final statistics into HashMap ("Avg":123.0, "Max":600.0).
     * @param avg - avg of earnings
     * @param max - max of earnings
     * @return - HashMap of final stats with keys "Avg" and "Max".
     */
    public static HashMap<String, Double> getFinalStats(double avg, int max) {
        HashMap<String, Double> finalStats = new HashMap<>();
        finalStats.put("Avg", avg);
        finalStats.put("Max", (double) max);
        return finalStats;
    }

    /**
     * Compares sales of the farmer with another farmer's sales
     * on a specific day.
     * @param day - number of weekday
     * @param strangerSales - other farmer earnings
     * @return - true if calling farmer sales were bigger. false otherwise.
     */
    public boolean compareSales(int day, HashMap<Integer, Integer> strangerSales) {
        if (day > days || day < 1 || strangerSales == null
                || sellingResults == null || sellingResults.size() < day
                || strangerSales.size() < day) {
            return false;
        }
        return strangerSales.get(day) < sellingResults.get(day);
    }

    /**
     * Compares the first and the second half of the week based on the sells
     * if Sunday would be a free day
     * (compares mon, tue, wed sum to thu, fri, sat sum).
     * @param salesArr - sells array
     * @return - true if during the first half of the week earned
     * as much as on the second half
     */
    public boolean checkSalesIfSundayIsFree(int[] salesArr) {
        if (salesArr == null || salesArr.length != days - 1
                || salesArr[0] + salesArr[1] + salesArr[2]
                < salesArr[2 + 1] + salesArr[2 + 2] + salesArr[2 + 2 + 1]) {
            return false;
        }
        return true;
    }

    /**
     * Sums sales recursively.
     * No loops here!
     * @param salesArr - sales array of ints
     * @param length - length of array (the part to use).
     *               This can be smaller than salesArr.length
     * @return - sum of earnings
     */
    public int salesSum(int[] salesArr, int length) {
        if (length == 0 || salesArr == null || salesArr.length < length) return 0;
        return salesArr[length - 1] + salesSum(salesArr, length - 1);
    }

    /**
     * To string method override.
     * @return preformatted string ("Farmer (farmeri nimi) teenis kokku (kokku teenitud summa) €")
     */
    @Override
    public String toString() {
        return "Farmer " + name + " teenis kokku "
                + salesSum(sortTheMess(getSellingResults()), getSellingResults().size()) + " €";
    }
}
