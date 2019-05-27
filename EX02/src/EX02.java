public class EX02 {

    /**
     * Constant.
     * Every 03 days, feed wormz.
     */
    public static final int WORM_FEEDING_DAY = 3;

    /**
     * Constant.
     * Every 5 days, bathe in sand.
     */
    public static final int BATHING_DAY = 5;

    /**
     * Constant.
     * Total number of days for which instructions are needed.
     */
    public static final int NUMBER_OF_DAYS = 30;

    /**
     * Entry point of the program.
     *
     * @param args Arguments from command line.
     */
    public static void main(String[] args) {
        for (int day = 1; day <= NUMBER_OF_DAYS; day++) {
            System.out.println(getInstructionForCurrentDay(day));
        }
    }

    /**
     * Return instruction for given day.
     *
     * @param currentDay number of day to print instructions for.
     */
    public static String getInstructionForCurrentDay(int currentDay) {
        String result = "give fruit and water";
        if (currentDay <= 0) {
            return "Can't fly back in time";
        } else if (currentDay % WORM_FEEDING_DAY == 0 && currentDay % BATHING_DAY == 0){
            result = "glide in wind";
        } else if (currentDay % BATHING_DAY == 0){
            result = "bathe in sand";
        } else if (currentDay % WORM_FEEDING_DAY == 0){
            result = "feed worms";
        }
        return "Day " + currentDay + " : " + result;
    }
}
