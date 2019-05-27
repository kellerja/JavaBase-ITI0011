import java.io.IOException;

/**
 * Homework 02 - Droptris AI.
 * https://courses.cs.ttu.ee/pages/ITI0011:HW02_Droptris
 */
public class HW02 {
    /***/
    private static final int COLUMN_AMOUNT = 10;
    /***/
    private static final int ROW_AMOUNT = 20;
    /***/
    private static boolean[][] map = new boolean[ROW_AMOUNT][COLUMN_AMOUNT];
    /***/
    private static int[] highestPointIndex = new int[COLUMN_AMOUNT];
    /***/
    private static final int BLOCK_O = 79;
    /***/
    private static final int BLOCK_I = 73;
    /***/
    private static final int BLOCK_J = 74;
    /***/
    private static final int BLOCK_L = 76;
    /***/
    private static final int BLOCK_T = 84;
    /***/
    private static final int BLOCK_S = 83;
    /***/
    private static final int BLOCK_Z = 90;
    /**
     * The main method. You can use this to initialize the game.
     * Tester will not execute the main method.
     * @param args Arguments from command line
     */
    public static void main(String[] args) {
        Configuration c = new Configuration(2 * 2 * 2 + 1, 0, 0);
        run(c.toString());
    }

    /**
     * Optional setup. This method will be called
     * before the game is started. You can do some
     * precalculations here if needed.
     *
     * If you don't need to precalculate anything,
     * just leave it empty.
     */
    public static void setup() {
    }
    /**
     * @return [minimum column value, maximum column value]*/
    private static int[] minMaxPoint() {
        int lowest = highestPointIndex[0];
        int highest = 0;
        for (int i = 0; i < highestPointIndex.length; i++) {
            if (highestPointIndex[i] < lowest) {
                lowest = highestPointIndex[i];
            }
            if (highestPointIndex[i] > highest) {
                highest = highestPointIndex[i];
            }
        }
        return new int[]{lowest, highest};
    }

    /**
     * @param rotation - block rotation.
     * @param width - block width.
     * @return [block index to place, block rotation]
     */
    private static int[] eval(int width, int rotation) {
        int optimalSum = (highestPointIndex[0] + highestPointIndex[1]) * width;
        int bestLocation = 0;
        int currentSum = 0;
        for (int i = 0; i < COLUMN_AMOUNT - width + 1; i++) {
            for (int j = 0; j < width; j++) {
                currentSum += highestPointIndex[i + j];
            }
            if ((currentSum) / width < optimalSum) {
                bestLocation = i;
                optimalSum = currentSum / width;
            }
            currentSum = 0;
        }
        return new int[]{bestLocation, rotation};
    }

    /***/
    private static void printMap() {
        String printMap = "";
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[map.length - 1 - i][j]) {
                    printMap += "1";
                } else {
                    printMap += ".";
                }
            }
            if (i != map.length - 1) {
                printMap += System.lineSeparator();
            }
        }
        System.out.println(printMap);
    }

    /***/
    private static void correctHeightValues() {
        int[] heights = new int[COLUMN_AMOUNT];
        boolean[] last = new boolean[COLUMN_AMOUNT];
        boolean b;
        for (int i = 0; i < map.length; i++) {
            b = true;
            for (int k = 0; k < map[i].length; k++) {
                if (!last[k]) {
                    b = false;
                }
                if (!map[i][k] && last[k]) {
                    heights[k] = i;
                }
                last[k] = map[i][k];
            }
            if (b) {
                break;
            }
        }
        highestPointIndex = heights;
    }

    /***/
    private static void removeLinesMap() {
        boolean[][] tempMap = new boolean[ROW_AMOUNT][COLUMN_AMOUNT];
        int j = 0;
        boolean removedLines = false;
        for (int i = 0; i < ROW_AMOUNT; i++) {
            if (map[i][0] && map[i][1] && map[i][2] && map[i][2 + 1]
                    && map[i][2 * 2] && map[i][2 * 2 + 1]
                    && map[i][(2 + 1) * 2] && map[i][2 * 2 * 2 - 1]
                    && map[i][2 * 2 * 2] && map[i][2 * 2 * 2 + 1]) {
                /*
                for (int n = 0; n < highestPointIndex.length; n++) {
                    highestPointIndex[n] -= 1;
                }
                */
                removedLines = true;
            } else {
                tempMap[j] = map[i];
                j++;
            }
        }
        map = tempMap;
        if (removedLines) {
            correctHeightValues();
        }
    }

    /**
     * @return best column for O block.
     */
    private static int bestOblockLocation() {
        int bestColumn = 0;
        int optimalHeight = highestPointIndex[0] + 1;
        int currentHeight;
        boolean set = false;
        for (int i = 0; i < COLUMN_AMOUNT - 1; i++) {
            currentHeight = highestPointIndex[i];
            if (highestPointIndex[i] == highestPointIndex[i + 1]) {
                if (currentHeight <= optimalHeight) {
                    bestColumn = i;
                    optimalHeight = currentHeight;
                    set = true;
                }
            }
        }
        /*
        if (!set) {
            int[] lastCase = eval(2, 0);
            bestColumn = lastCase[0];
        }
        */
        return bestColumn;
    }

    /**
     * @return [I block column placement, rotation]
     */
    private static int[] bestIblockLocation() {
        int bestColumn = 0;
        int bestRotation = 0;
        int height = highestPointIndex[0];
        int[] minMax = minMaxPoint();
        boolean set = false;
        for (int i = COLUMN_AMOUNT - 1; i >= 0; i--) {
            /*
            if (i != 0 && i < COLUMN_AMOUNT - 1) {
                if (highestPointIndex[i + 1] >= highestPointIndex[i] + 3
                        && highestPointIndex[i - 1] >= highestPointIndex[i] + 3) {
                    bestColumn = i;
                    height = highestPointIndex[i];
                    set = true;
                }
            } else if (i == 0) {
                if (highestPointIndex[i + 1] >= highestPointIndex[i] + 3) {
                    bestColumn = i;
                    height = highestPointIndex[i];
                    set = true;
                }
            } else if (i + 1 == COLUMN_AMOUNT) {
                if (highestPointIndex[i - 1] >= highestPointIndex[i] + 3) {
                    bestColumn = i;
                    height = highestPointIndex[i];
                    set = true;
                }
            }
            */
            if (i < COLUMN_AMOUNT - 2 - 1) {
                if (height < highestPointIndex[i]) {
                    continue;
                }
                /*
                System.out.println("TO CLECK FOR:" + i + " Height: " + height + "\nDETAILS "
                        + highestPointIndex[i] + " " + highestPointIndex[i + 1] + " "
                        + highestPointIndex[i + 2] + " " + highestPointIndex[i + 3]);
                */
                if (highestPointIndex[i] == highestPointIndex[i + 1]
                        && highestPointIndex[i] == highestPointIndex[i + 2]
                        && highestPointIndex[i] == highestPointIndex[i + 2 + 1]
                        && !set) {
                    bestColumn = i;
                    bestRotation = 1;
                    height = highestPointIndex[i];
                }
            }
            /*
            System.out.println("MINMAX " + minMax[0] + " HEIGHT "
                    + height + " i: " + i + " HEIGHTi: " + highestPointIndex[i]);
            */
            if (minMax[0] <= height && minMax[0] == highestPointIndex[i]) {
                bestColumn = i;
                bestRotation = 0;
                height = minMax[0];
            }
        }
        return new int[]{bestColumn, bestRotation};
    }

    /**
     * @return best J location and rotation.
     */
    private static int[] bestJblockLocation() {
        int bestColumn = 0;
        int bestRotation = 1;
        boolean set = false;
        int currentHeight;
        int optimalHeight = highestPointIndex[0] + 1;
        for (int i = 0; i < COLUMN_AMOUNT - 1; i++) {
            currentHeight = highestPointIndex[i];
            if (highestPointIndex[i] == highestPointIndex[i + 1] - 2 && currentHeight <= optimalHeight) {
                bestColumn = i;
                bestRotation = 2;
                optimalHeight = currentHeight;
                set = true;
            }
            if (i < COLUMN_AMOUNT - 2 - 1 && highestPointIndex[i] == highestPointIndex[i + 1]
                    && highestPointIndex[i] == highestPointIndex[i + 2] + 1 && currentHeight <= optimalHeight) {
                bestColumn = i;
                bestRotation = 2 + 1;
                optimalHeight = currentHeight;
                set = true;
            }
            if (i < COLUMN_AMOUNT - 2 - 1 && highestPointIndex[i] == highestPointIndex[i + 1]
                    && highestPointIndex[i] == highestPointIndex[i + 2] && currentHeight < optimalHeight) {
                bestColumn = i;
                bestRotation = 1;
                optimalHeight = currentHeight;
                set = true;
            }
            if (highestPointIndex[i] == highestPointIndex[i + 1] && currentHeight <= optimalHeight) {
                bestColumn = i;
                bestRotation = 0;
                optimalHeight = currentHeight;
                set = true;
            }
        }
        if (!set) {
            int[] lastCase = eval(2, 0);
            bestColumn = lastCase[0];
            bestRotation = lastCase[1];
        }
        return new int[] {bestColumn, bestRotation};
    }

    /**
     * @return best L block column and rotation
     */
    private static int[] bestLblockLocation() {
        int bestColumn = 0;
        int bestRotation = 2 + 1;
        boolean set = false;
        int currentHeight;
        int optimalHeight = highestPointIndex[0] + 1;
        for (int i = 0; i < COLUMN_AMOUNT - 1; i++) {
            currentHeight = highestPointIndex[i];
            if (highestPointIndex[i] == highestPointIndex[i + 1] + 2 && currentHeight <= optimalHeight) {
                bestColumn = i;
                bestRotation = 2;
                optimalHeight = currentHeight;
                set = true;
            }
            if (i < COLUMN_AMOUNT - 2 && highestPointIndex[i] == highestPointIndex[i + 1] + 1
                    && highestPointIndex[i + 1] == highestPointIndex[i + 2] && currentHeight < optimalHeight) {
                bestColumn = i;
                bestRotation = 1;
                optimalHeight = currentHeight;
                set = true;
            }
            if (i < COLUMN_AMOUNT - 2 && highestPointIndex[i] == highestPointIndex[i + 1]
                    && highestPointIndex[i] == highestPointIndex[i + 2] && currentHeight < optimalHeight) {
                bestColumn = i;
                bestRotation = 2 + 1;
                optimalHeight = currentHeight;
                set = true;
            }
            if (highestPointIndex[i] == highestPointIndex[i + 1] && currentHeight < optimalHeight) {
                bestColumn = i;
                bestRotation = 0;
                optimalHeight = currentHeight;
                set = true;
            }
        }
        if (!set) {
            int[] lastCase = eval(2, 0);
            bestColumn = lastCase[0];
            bestRotation = lastCase[1];
        }
        return new int[] {bestColumn, bestRotation};
    }

    /**
     * @return best location for T block.
     */
    private static int[] bestTblockLocation() {
        int bestColumn = 0;
        int bestRotation = 0;
        boolean set = false;
        int currentHeight;
        int optimalHeight = highestPointIndex[0] + 1;
        for (int i = 0; i < COLUMN_AMOUNT - 1; i++) {
            currentHeight = highestPointIndex[i];
            if (i < COLUMN_AMOUNT - 2 && currentHeight <= optimalHeight
                    && highestPointIndex[i] == highestPointIndex[i + 1]
                    && highestPointIndex[i] == highestPointIndex[i + 2]) {
                bestColumn = i;
                bestRotation = 0;
                set = true;
            }
            if (currentHeight < optimalHeight
                    && highestPointIndex[i] == highestPointIndex[i + 1] - 1) {
                bestColumn = i;
                bestRotation = 1;
                set = true;
            }
            if (i < COLUMN_AMOUNT - 2 && currentHeight < optimalHeight
                    && highestPointIndex[i] == highestPointIndex[i + 1] + 1
                    && highestPointIndex[i] == highestPointIndex[i + 2]) {
                bestColumn = i;
                bestRotation = 2;
                set = true;
            }
            if (currentHeight < optimalHeight
                    && highestPointIndex[i] == highestPointIndex[i + 1] + 1) {
                bestColumn = i;
                bestRotation = 2 + 1;
                set = true;
            }
        }
        if (!set) {
            int[] lastCase = eval(2, 1);
            bestColumn = lastCase[0];
            bestRotation = lastCase[1];
        }
        return new int[] {bestColumn, bestRotation};
    }

    /**
     * @return best S block column and rotation.
     */
    private static int[] bestSblockLocation() {
        int bestColumn = 0;
        int bestRotation = 0;
        boolean set = false;
        int currentHeight;
        int optimalHeight = highestPointIndex[0] + 1;
        for (int i = 0; i < COLUMN_AMOUNT - 1; i++) {
            currentHeight = highestPointIndex[i];
            if (currentHeight <= optimalHeight && highestPointIndex[i] == highestPointIndex[i + 1] + 1) {
                bestColumn = i;
                bestRotation = 1;
                set = true;
                optimalHeight = currentHeight;
            }
            if (i < COLUMN_AMOUNT - 2 && currentHeight <= optimalHeight
                    && highestPointIndex[i] == highestPointIndex[i + 1]
                    && highestPointIndex[i] == highestPointIndex[i + 2] - 1) {
                bestColumn = i;
                bestRotation = 0;
                set = true;
                optimalHeight = currentHeight;
            }
        }
        if (!set) {
            int[] data = eval(2, 1);
            bestColumn = data[0];
            bestRotation = data[1];
        }
        return new int[] {bestColumn, bestRotation};
    }

    /**
     * @return best Z block column and rotation.
     */
    private static int[] bestZblockLocation() {
        int bestColumn = 0;
        int bestRotation = 0;
        boolean set = false;
        int currentHeight;
        int optimalHeight = highestPointIndex[0] + 1;
        for (int i = 0; i < COLUMN_AMOUNT - 1; i++) {
            currentHeight = highestPointIndex[i];
            if (currentHeight <= optimalHeight && highestPointIndex[i] == highestPointIndex[i + 1] - 1) {
                bestColumn = i;
                bestRotation = 1;
                set = true;
                optimalHeight = currentHeight;
            }
            if (currentHeight < optimalHeight && i < COLUMN_AMOUNT - 2
                    && highestPointIndex[i] == highestPointIndex[i + 1] + 1
                    && highestPointIndex[i] == highestPointIndex[i + 2] + 1) {
                bestColumn = i;
                bestRotation = 0;
                set = true;
                optimalHeight = currentHeight;
            }
        }
        if (!set) {
            int[] data = eval(2, 1);
            bestColumn = data[0];
            bestRotation = data[1];
        }
        return new int[] {bestColumn, bestRotation};
    }

    /**
     * @param block - block to find position for.
     * @return [column, rotation]
     */
    private static int[] findBestLocation(int block) {
        int[] result = new int[2];
        int bestColumn = 0;
        int bestRotation = 0;
        if (block == BLOCK_O) {
            bestColumn = bestOblockLocation();
        } else if (block == BLOCK_I) {
            int[] locations = bestIblockLocation();
            bestColumn = locations[0];
            bestRotation = locations[1];
        } else if (block == BLOCK_J) {
            int[] locations = bestJblockLocation();
            bestColumn = locations[0];
            bestRotation = locations[1];
        } else if (block == BLOCK_L) {
            int[] locations = bestLblockLocation();
            bestColumn = locations[0];
            bestRotation = locations[1];
        } else if (block == BLOCK_T) {
            int[] locations = bestTblockLocation();
            bestColumn = locations[0];
            bestRotation = locations[1];
        } else if (block == BLOCK_S) {
            int[] locations = bestSblockLocation();
            bestColumn = locations[0];
            bestRotation = locations[1];
        } else if (block == BLOCK_Z) {
            int[] locations = bestZblockLocation();
            bestColumn = locations[0];
            bestRotation = locations[1];
        }
        result[0] = bestColumn;
        result[1] = bestRotation;
        return result;
    }

    /**
     * @param column where to add block.
     */
    private static void updateMapOblock(int column) {
        int highest = highestPointIndex[column];
        if (highest < highestPointIndex[column + 1]) {
            highest = highestPointIndex[column + 1];
        }
        if (highest < map.length - 1) {
            map[highest][column] = true;
            map[highest][column + 1] = true;
            map[highest + 1][column] = true;
            map[highest + 1][column + 1] = true;
        }
        highestPointIndex[column] = highest + 2;
        highestPointIndex[column + 1] = highest + 2;
    }

    /**
     * @param column - column to add block to.
     * @param rotation - block's rotation.
     */
    private static void updateMapIblock(int column, int rotation) {
        if (highestPointIndex[column] < map.length) {
            if (rotation == 1 || rotation == 2 + 1) {
                map[highestPointIndex[column]][column] = true;
                map[highestPointIndex[column]][column + 1] = true;
                map[highestPointIndex[column]][column + 2] = true;
                map[highestPointIndex[column]][column + 2 + 1] = true;
            } else if (highestPointIndex[column] - 2 - 2 < map[0].length) {
                map[highestPointIndex[column]][column] = true;
                map[highestPointIndex[column] + 1][column] = true;
                map[highestPointIndex[column] + 2][column] = true;
                map[highestPointIndex[column] + 2 + 1][column] = true;
            }
        }
        if (rotation == 0 || rotation == 2) {
            highestPointIndex[column] += 2 + 2;
        } else {
            highestPointIndex[column] += 1;
            highestPointIndex[column + 1] += 1;
            highestPointIndex[column + 2] += 1;
            highestPointIndex[column + 2 + 1] += 1;
        }
    }

    /**
     * @param column - column where to add J block.
     * @param rotation - rotation of J block.
     */
    private static void updateMapJblock(int column, int rotation) {
        if (rotation == 0) {
            int higher = highestPointIndex[column];
            if (higher < highestPointIndex[column + 1]) {
                higher = highestPointIndex[column + 1];
            }
            if (higher < map.length - 2) {
                map[higher][column] = true;
                map[higher][column + 1] = true;
                map[higher + 1][column + 1] = true;
                map[higher + 2][column + 1] = true;
            }
            highestPointIndex[column] = higher + 1;
            highestPointIndex[column + 1] = higher + 2 + 1;
        } else if (rotation == 1) {
            int higher = highestPointIndex[column];
            if (higher < highestPointIndex[column + 1]) {
                higher = highestPointIndex[column + 1];
            }
            if (higher < highestPointIndex[column + 2]) {
                higher = highestPointIndex[column + 2];
            }
            if (higher < map.length - 1) {
                map[higher][column] = true;
                map[higher + 1][column] = true;
                map[higher][column + 1] = true;
                map[higher][column + 2] = true;
            }
            highestPointIndex[column] = higher + 2;
            highestPointIndex[column + 1] = higher + 1;
            highestPointIndex[column + 2] = higher + 1;
        } else if (rotation == 2) {
            int higher = highestPointIndex[column];
            if (higher < highestPointIndex[column + 1] - 2) {
                higher = highestPointIndex[column + 1] - 2;
            }
            if (higher < map.length - 2) {
                map[higher][column] = true;
                map[higher + 1][column] = true;
                map[higher + 2][column] = true;
                map[higher + 2][column + 1] = true;
            }
            highestPointIndex[column] = higher + 2 + 1;
            highestPointIndex[column + 1] = higher + 2 + 1;
        } else if (rotation == 2 + 1) {
            int higher = highestPointIndex[column];
            if (higher < highestPointIndex[column + 1]) {
                higher = highestPointIndex[column + 1];
            }
            if (higher < highestPointIndex[column + 2] + 1) {
                higher = highestPointIndex[column + 2] + 1;
            }
            if (higher < map.length - 1) {
                map[higher][column] = true;
                map[higher][column + 1] = true;
                map[higher][column + 2] = true;
                map[higher - 1][column + 2] = true;
            }
            highestPointIndex[column] = higher + 2;
            highestPointIndex[column + 1] = higher + 2;
            highestPointIndex[column + 2] = higher + 2;
            highestPointIndex[column + 2] = higher + 1;
        }
    }

    /**
     * @param column - column of the L block.
     * @param rotation - rotation of the L block.
     */
    private static void updateMapLblock(int column, int rotation) {
        if (rotation == 0) {
            int highest = highestPointIndex[column];
            if (highest < highestPointIndex[column + 1]) {
                highest = highestPointIndex[column + 1];
            }
            if (highest < map.length - 2) {
                map[highest][column] = true;
                map[highest + 1][column] = true;
                map[highest + 2][column] = true;
                map[highest][column + 1] = true;
            }
            highestPointIndex[column] = highest + 2 + 1;
            highestPointIndex[column + 1] = highest + 1;
        } else if (rotation == 1) {
            int highest = highestPointIndex[column];
            if (highest < highestPointIndex[column + 1] - 1) {
                highest = highestPointIndex[column + 1] - 1;
            }
            if (highest < map.length - 1) {
                map[highest][column] = true;
                map[highest + 1][column] = true;
                map[highest + 1][column + 1] = true;
                map[highest + 1][column + 2] = true;
            }
            highestPointIndex[column] = highest + 2;
            highestPointIndex[column + 1] = highest + 2;
            highestPointIndex[column + 2] = highest + 2;
        } else if (rotation == 2) {
            int highest = highestPointIndex[column];
            if (highest < highestPointIndex[column + 1]) {
                highest = highestPointIndex[column + 1];
            }
            if (highest < map.length - 2 - 1) {
                map[highest][column] = true;
                map[highest][column + 1] = true;
                map[highest - 1][column + 1] = true;
                map[highest - 2][column + 1] = true;
            }
            highestPointIndex[column] = highest + 1;
            highestPointIndex[column + 1] = highest + 1;
        } else if (rotation == 2 + 1) {
            int highest = highestPointIndex[column];
            if (highest < highestPointIndex[column + 1]) {
                highest = highestPointIndex[column + 1];
            }
            if (highest < highestPointIndex[column + 2]) {
                highest = highestPointIndex[column + 2];
            }
            if (highest < map.length - 1) {
                map[highest][column] = true;
                map[highest][column + 1] = true;
                map[highest][column + 2] = true;
                map[highest + 1][column + 2] = true;
            }
            highestPointIndex[column] = highest + 1;
            highestPointIndex[column + 1] = highest + 1;
            highestPointIndex[column + 2] = highest + 2;
        }
    }

    /**
     * @param column - where to place T block.
     * @param rotation - rotation of T block.
     */
    private static void updateMapTblock(int column, int rotation) {
        if (rotation == 0) {
            int highest = highestPointIndex[column];
            if (highest < highestPointIndex[column + 1]) {
                highest = highestPointIndex[column + 1];
            }
            if (highest < highestPointIndex[column + 2]) {
                highest = highestPointIndex[column + 2];
            }
            if (highest < map.length - 1) {
                map[highest][column] = true;
                map[highest][column + 1] = true;
                map[highest + 1][column + 1] = true;
                map[highest][column + 2] = true;
            }
            highestPointIndex[column] = highest + 1;
            highestPointIndex[column + 1] = highest + 2;
            highestPointIndex[column + 2] = highest + 1;
        } else if (rotation == 1) {
            int highest = highestPointIndex[column];
            if (highest < highestPointIndex[column + 1] - 1) {
                highest = highestPointIndex[column + 1] - 1;
            }
            if (highest < map.length - 2) {
                map[highest][column] = true;
                map[highest + 1][column] = true;
                map[highest + 2][column] = true;
                map[highest + 1][column + 1] = true;
            }
            highestPointIndex[column] = highest + 2 + 1;
            highestPointIndex[column + 1] = highest + 2;
        } else if (rotation == 2) {
            int highest = highestPointIndex[column];
            if (highest < highestPointIndex[column + 1] + 1) {
                highest = highestPointIndex[column + 1] + 1;
            }
            if (highest < highestPointIndex[column + 2]) {
                highest = highestPointIndex[column];
            }
            if (highest < map.length - 1) {
                map[highest][column] = true;
                map[highest][column + 1] = true;
                map[highest - 1][column + 1] = true;
                map[highest][column + 2] = true;
            }
            highestPointIndex[column] = highest + 1;
            highestPointIndex[column + 1] = highest + 1;
            highestPointIndex[column + 2] = highest + 1;
        } else if (rotation == 2 + 1) {
            int highest = highestPointIndex[column];
            if (highest < highestPointIndex[column + 1] + 1) {
                highest = highestPointIndex[column + 1] + 1;
            }
            if (highest < map.length - 2) {
                map[highest][column] = true;
                map[highest - 1][column + 1] = true;
                map[highest][column + 1] = true;
                map[highest + 1][column + 1] = true;
            }
            highestPointIndex[column] = highest + 1;
            highestPointIndex[column + 1] = highest + 2;
        }
    }

    /**
     * @param column - column to place S block.
     * @param rotation - rotation of S block.
     */
    private static void updateMapSblock(int column, int rotation) {
        if (rotation == 0) {
            int highest = highestPointIndex[column];
            if (highest < highestPointIndex[column + 1]) {
                highest = highestPointIndex[column + 1];
            }
            if (highest < highestPointIndex[column + 2] - 1) {
                highest = highestPointIndex[column + 2] - 1;
            }
            if (highest < map.length - 2) {
                map[highest][column] = true;
                map[highest][column + 1] = true;
                map[highest + 1][column + 1] = true;
                map[highest + 1][column + 2] = true;
            }
            highestPointIndex[column] = highest + 1;
            highestPointIndex[column + 1] = highest + 2;
            highestPointIndex[column + 2] = highest + 2;
        } else if (rotation == 1) {
            int highest = highestPointIndex[column];
            if (highest < highestPointIndex[column + 1] + 1) {
                highest = highestPointIndex[column + 1] + 1;
            }
            if (highest < map.length - 2) {
                map[highest][column] = true;
                map[highest + 1][column] = true;
                map[highest][column + 1] = true;
                map[highest - 1][column + 1] = true;
            }
            highestPointIndex[column] = highest + 2;
            highestPointIndex[column + 1] = highest + 1;
        }
    }

    /**
     * @param column column to place Z block to.
     * @param rotation of the Z block.
     */
    private static void updateMapZblock(int column, int rotation) {
        if (rotation == 0) {
            int highest = highestPointIndex[column];
            if (highest < highestPointIndex[column + 1] + 1) {
                highest = highestPointIndex[column + 1] + 1;
            }
            if (highest < highestPointIndex[column + 2] + 1) {
                highest = highestPointIndex[column + 2] + 1;
            }
            if (highest < map.length) {
                map[highest][column] = true;
                map[highest][column + 1] = true;
                map[highest - 1][column + 1] = true;
                map[highest - 1][column + 2] = true;
            }
            highestPointIndex[column] = highest + 1;
            highestPointIndex[column + 1] = highest + 1;
            highestPointIndex[column + 2] = highest;
        } else if (rotation == 1) {
            int highest = highestPointIndex[column];
            if (highest < highestPointIndex[column + 1] - 1) {
                highest = highestPointIndex[column + 1] - 1;
            }
            if (highest < map.length - 2 - 1) {
                map[highest][column] = true;
                map[highest + 1][column] = true;
                map[highest + 1][column + 1] = true;
                map[highest + 2][column + 1] = true;
            }
            highestPointIndex[column] = highest + 2;
            highestPointIndex[column + 1] = highest + 2 + 1;
        }
    }

    /**
     * @param block - block to add on map.
     * @param column - column where to add map.
     * @param rotation - rotation of block.
     */
    private static void updateMap(int block, int column, int rotation) {
        if (block == BLOCK_O) {
            updateMapOblock(column);
        } else if (block == BLOCK_I) {
            updateMapIblock(column, rotation);
        } else if (block == BLOCK_J) {
            updateMapJblock(column, rotation);
        } else if (block == BLOCK_L) {
            updateMapLblock(column, rotation);
        } else if (block == BLOCK_T) {
            updateMapTblock(column, rotation);
        } else if (block == BLOCK_S) {
            updateMapSblock(column, rotation);
        } else if (block == BLOCK_Z) {
            updateMapZblock(column, rotation);
        }
        removeLinesMap();

    }
    /**
     * The method to execute your AI.
     * @param connectionString JSON-formatted connection string.
     *                         If you implement Socket connection yourself
     *                         you should use this string directly when connecting.
     *                         If you use DroptrisConnection, you can ignore that.
     * @return The final score. You should read the score from the server.
     */
    public static int run(String connectionString) {
        int scoreValue = 0;
        try {
            //DroptrisConnection conn = new DroptrisConnection("jakell", true);
            Connection conn = new Connection(connectionString);
            String line = conn.readLine();
            System.out.println(line);
            String[] info;
            int column;
            int rotation;
            int block;
            while ((line = conn.readLine()) != null) {
                //line = "vdsv:I";
                //System.out.println(line);
                info = line.replaceAll("[{}\" ]*", "").split(":");
                block = (int) info[1].charAt(0);
                System.out.println(block + " " + ((char) block));
                if (block != BLOCK_J && block != BLOCK_L
                        && block != BLOCK_T && block != BLOCK_I
                        && block != BLOCK_O && block != BLOCK_S && block != BLOCK_Z) {
                    continue;
                }
                //System.out.println(block + " " + ((char) block));
                int[] bestLocation = findBestLocation(block);
                //System.out.println(line + " " + bestLocation[0] + " " + bestLocation[1]);
                column = bestLocation[0];
                rotation = bestLocation[1];
                conn.sendAction("{\"column\": " + column + ", \"rotation\": " + rotation + "}");
                updateMap(block, column, rotation);
                /*
                System.out.println(highestPointIndex[0] + " " + highestPointIndex[1]
                        + " " + highestPointIndex[2] + " " + highestPointIndex[3]
                        + " " + highestPointIndex[4] + " " + highestPointIndex[5]
                        + " " + highestPointIndex[6] + " " + highestPointIndex[7]
                        + " " + highestPointIndex[8] + " " + highestPointIndex[9]);
                */
                printMap();
                System.out.println(conn.readScoreData());
            }
            String score = conn.readScoreData();
            System.out.println("Game over. Score: " + score);
            String[] scoreInfo = score.replaceAll("[{}\" ]*", "").split(",");
            String[] valueScorePair = scoreInfo[0].split(":");
            /*
            String[] serverMap = conn.serverMap().split(":")[1].replaceAll(", " +
                    "\"parameter\"", "").replaceAll("\\[", "").split("], ");
            for (String mapPart: serverMap) {
                System.out.println(mapPart);
            }
            */
            try {
                scoreValue = Integer.parseInt(valueScorePair[1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scoreValue;
    }
}
