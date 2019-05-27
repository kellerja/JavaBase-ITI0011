import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Template for HW01: Treasurehunt.
 * More information:
 * https://courses.cs.ttu.ee/pages/ITI0011:Aardejaht
 */
public class HW01 {
    /**
     * Value to return in makeMove in case
     * the cell was empty.
     */
    public static final int CELL_EMPTY = 0;

    /**
     * Value to return in makeMove in case
     * the cell contained treasure.
     */
    public static final int CELL_TREASURE = 1;

    /**
     * Value to return in makeMove in case
     * the cell does not exist.
     */

    public static final int CELL_ERROR = -1;
    /***/
    public static final int CELL_OPEN = -2;
    /***/
    public static final int CELL_TREASURE_OPEN = -3;
    /***/
    private static int[][] map;
    /***/
    public static final int CELL_TREASURE_NEAR = 11;
    /***/
    public static final int CELL_TREASURE_NEAR_OPEN = -CELL_TREASURE_NEAR;
    /***/
    public static final boolean OPEN_MAP = true;
    /***/
    private static int[][] numberedMap;
    /***/
    public static final boolean KEEP_SCORE = true;
    /***/
    private static ArrayList<int[]> sCORE = new ArrayList<>();
    /***/
    private static ArrayList<String> sCORENAME = new ArrayList<>();
    /***/
    public static final int MAGIC3 = 3;
    /***/
    public static final int NAME_LENGTH_MIN = 3;
    /***/
    public static final int NAME_LENGTH_MAX = 20;
    /***/
    public static final int SCORE_AMOUNT_MAX = 10;
    /***/
    public static final int DEFAULT_GAMEMODE = 0;
    /***/
    public static final int MM_GAMEMODE = 1;
    /***/
    public static final int WARM_COLD_GAMEMODE = 2;
    /***/
    private static int gamemode;
    /***/
    public static final int MM_ROUNDS = 5;
    /***/
    public static final int MM_BOARD_ROWS = 3;
    /***/
    public static final int MM_BOARD_COLUMNS = 3;
    /***/
    public static final int MM_BOARD_TREASURES = 3;

    /**
     * @param row - row.
     * @param col - col.
     */
    private static void openEmptyCells(int row, int col) {
        setCell(row, col, CELL_OPEN);
        if (row + 1 < map.length && map[row + 1][col] == CELL_EMPTY) {
            openEmptyCells(row + 1, col);
        } else if (row + 1 < map.length && map[row + 1][col] == CELL_TREASURE_NEAR) {
            setCell(row + 1, col, CELL_TREASURE_NEAR_OPEN);
        }
        if (row - 1 > 0 && map[row - 1][col] == CELL_EMPTY) {
            openEmptyCells(row - 1, col);
        } else if (row - 1 > 0 && map[row - 1][col] == CELL_TREASURE_NEAR) {
            setCell(row - 1, col, CELL_TREASURE_NEAR_OPEN);
        }
        if (col + 1 < map[row].length && map[row][col + 1] == CELL_EMPTY) {
            openEmptyCells(row, col + 1);
        } else if (col + 1 < map[row].length && map[row][col + 1] == CELL_TREASURE_NEAR) {
            setCell(row, col + 1, CELL_TREASURE_NEAR_OPEN);
        }
        if (col - 1 > 0 && map[row][col - 1] == CELL_EMPTY) {
            openEmptyCells(row, col - 1);
        } else if (col - 1 > 0 && map[row][col - 1] == CELL_TREASURE_NEAR) {
            setCell(row, col - 1, CELL_TREASURE_NEAR_OPEN);
        }
        if (col - 1 > 0 && row - 1 > 0 && map[row - 1][col - 1] == CELL_EMPTY) {
            openEmptyCells(row - 1, col - 1);
        } else if (col - 1 > 0 && row - 1 > 0 && map[row - 1][col - 1] == CELL_TREASURE_NEAR) {
            setCell(row - 1, col - 1, CELL_TREASURE_NEAR_OPEN);
        }
        if (col - 1 > 0 && row + 1 < map.length && map[row + 1][col - 1] == CELL_EMPTY) {
            openEmptyCells(row + 1, col - 1);
        } else if (col - 1 > 0 && row + 1 < map.length && map[row + 1][col - 1] == CELL_TREASURE_NEAR) {
            setCell(row + 1, col - 1, CELL_TREASURE_NEAR_OPEN);
        }
        if (col + 1 < map[row].length && row - 1 > 0 && map[row - 1][col + 1] == CELL_EMPTY) {
            openEmptyCells(row - 1, col + 1);
        } else if (col + 1 < map[row].length && row - 1 > 0 && map[row - 1][col + 1] == CELL_TREASURE_NEAR) {
            setCell(row - 1, col + 1, CELL_TREASURE_NEAR_OPEN);
        }
        if (col + 1 < map[row].length && row + 1 < map.length && map[row + 1][col + 1] == CELL_EMPTY) {
            openEmptyCells(row + 1, col + 1);
        } else if (col + 1 < map[row].length && row + 1 < map.length && map[row + 1][col + 1] == CELL_TREASURE_NEAR) {
            setCell(row + 1, col + 1, CELL_TREASURE_NEAR_OPEN);
        }
    }

    /**
     * Makes move to cell in certain row and column
     * and returns the contents of the cell.
     * Use CELL_* constants in return.
     * @param row Row to make move to.
     * @param col Column to make move to.
     * @return Contents of the cell.
     */
    public static int makeMove(int row, int col) {
        if (map == null || row < 0 || col < 0 || row >= map.length || col >= map[row].length) {
            return CELL_ERROR;
        }
        if (map[row][col] == CELL_EMPTY || map[row][col] == CELL_OPEN) {
            if (OPEN_MAP && map[row][col] == CELL_EMPTY && gamemode != WARM_COLD_GAMEMODE) {
                openEmptyCells(row, col);
            }
            map[row][col] = CELL_OPEN;
            return CELL_EMPTY;
        } else if (map[row][col] == CELL_TREASURE_NEAR) {
            map[row][col] = CELL_TREASURE_NEAR_OPEN;
            return CELL_TREASURE_NEAR;
        } else if (map[row][col] == CELL_TREASURE_NEAR_OPEN) {
            return CELL_TREASURE_NEAR_OPEN;
        } else if (map[row][col] == CELL_TREASURE_OPEN) {
            return CELL_TREASURE_OPEN;
        } else if (map[row][col] == CELL_TREASURE) {
            map[row][col] = CELL_TREASURE_OPEN;
            return CELL_TREASURE;
        } else {
            return CELL_ERROR;
        }
    }

    /***/
    private static void numberEmptyCells() {

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == CELL_EMPTY) {
                    int treasuresNearCell = 0;
                    for (int i = -1; i <= 1; i++) {
                        for (int i2 = -1; i2 <= 1; i2++) {
                            if (row + i < map.length
                                    && row + i >= 0
                                    && col + i2 < map[row].length
                                    && col + i2 >= 0
                                    && map[row + i][col + i2] == CELL_TREASURE) {
                                treasuresNearCell += 1;
                            }
                        }
                    }
                    numberedMap[row][col] = treasuresNearCell;
                }
            }
        }
    }

    /**
     * @param treasures - number of treasures.
     * @return array of treasure locations.
     */
    private static int[][] findTreasures(int treasures) {
        int[][] treasurePlaces = new int[treasures][2];
        int treasureNumber = 0;
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == CELL_TREASURE) {
                    treasurePlaces[treasureNumber][0] = row;
                    treasurePlaces[treasureNumber][1] = col;
                    treasureNumber += 1;
                }
            }
        }
        return treasurePlaces;
    }

    /**
     * @param treasures - number of treasures in map
     */
    private static void numberWholeMap(int treasures) {
        int[][] treasurePlaces = findTreasures(treasures);
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                int minDistance = map.length;
                if (map[row][col] == CELL_EMPTY) {
                    for (int i = 0; i < treasurePlaces.length; i++) {
                        int distance = (int) Math.sqrt((row - treasurePlaces[i][0]) * (row - treasurePlaces[i][0])
                                + (col - treasurePlaces[i][1]) * (col - treasurePlaces[i][1]));
                        if (distance < minDistance) {
                            minDistance = distance;
                        }
                    }
                    numberedMap[row][col] = minDistance;
                }
            }
        }
    }

    /***/
    private static void markAroundTreasure() {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == CELL_TREASURE) {
                    for (int i = -1; i <= 1; i++) {
                        for (int i2 = -1; i2 <= 1; i2++) {
                            if (row + i < map.length
                                    && row + i >= 0
                                    && col + i2 < map[row].length
                                    && col + i2 >= 0
                                    && map[row + i][col + i2] == CELL_EMPTY) {
                                setCell(row + i, col + i2, CELL_TREASURE_NEAR);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates a map with certain measures and treasures.
     * As this is a static method which doesn't return anything (void),
     * you should store the created map internally.
     * This means you can choose your own implementation of how to store
     * the map.
     * The treasures should be put on the map randomly using setCell method.
     * @param height Height of the map.
     * @param width Width of the map.
     * @param treasures The number of treasures on the map.
     * @return Whether map was created.
     */
    public static boolean createMap(int height, int width, int treasures) {
        if (height < 0 || width < 0 || treasures > height * width || treasures <= 0) {
            return false;
        }
        map = new int[height][width];
        Random rn = new Random();
        int row;
        int col;
        for (int i = 0; i < treasures; i++) {
            do {
                row = rn.nextInt(map.length);
                col = rn.nextInt(map[row].length);
            } while (map[row][col] == CELL_TREASURE);
            setCell(row, col, CELL_TREASURE);
        }
        if (OPEN_MAP) {
            numberedMap = new int[height][width];
            if (gamemode != WARM_COLD_GAMEMODE) {
                numberEmptyCells();
                markAroundTreasure();
            } else {
                numberWholeMap(treasures);
            }
        }
        return true;
    }

    /**
     * Sets the cell value for the active map (created earlier using
     * createMap method).
     * This method is required to test certain maps
     * @param row Row index.
     * @param col Column index.
     * @param cellContents The value of the cell.
     * @return Whether the cell value was set.
     */
    public static boolean setCell(int row, int col, int cellContents) {
        if (map == null || row < 0 || col < 0
                || map.length < row || map[row].length < col) {
            return false;
        }
        map[row][col] = cellContents;
        return true;
    }
    /**
     * @param input - String input to be made into int array.
     * @return int array or null if conditions are incorrect.
     */
    private static int[] intFromInput(String input) {
        String[] inputArray = input.replaceAll(" ", "").split(",");
        int[] intInput;
        if (inputArray.length != MAGIC3 && inputArray.length != 2) {
            return null;
        } else if (inputArray.length == MAGIC3) {
            intInput = new int[MAGIC3];
            try {
                intInput[0] = Integer.parseInt(inputArray[0]);
                intInput[1] = Integer.parseInt(inputArray[1]);
                intInput[2] = Integer.parseInt(inputArray[2]);
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            intInput = new int[2];
            try {
                intInput[0] = Integer.parseInt(inputArray[0]);
                intInput[1] = Integer.parseInt(inputArray[1]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return intInput;
    }
    /**
     * @return int array [rows in map, columns in map, amount of treasures]
     */
    private static int[] startInput() {
        String input;
        boolean mapCreated;
        int[] intArray;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.print("Sisesta M (ridade arv),"
                    + " N (veergude arv), X (aarete arv): ");
            input = scan.nextLine();
            intArray = intFromInput(input);
        } while (intArray == null || intArray.length != MAGIC3);
        mapCreated = createMap(intArray[0], intArray[1], intArray[2]);
        if (mapCreated) {
            System.out.println("\nEdukat kaevamist!");
            printMap();
            return intArray;
        }
        return null;
    }
    /***/
    private static void printMap() {
        String printableMap = "\n";
        for (int i = 0; i < map.length; i++) {
            for (int i2 = 0; i2 < map[i].length; i2++) {
                if (map[i][i2] == CELL_OPEN) {
                    printableMap += ' ';
                } else if (map[i][i2] == CELL_EMPTY) {
                    printableMap += '.';
                } else if (map[i][i2] == CELL_TREASURE) {
                    printableMap += '.';
                } else if (map[i][i2] == CELL_TREASURE_OPEN) {
                    printableMap += '+';
                } else if (map[i][i2] == CELL_TREASURE_NEAR) {
                    printableMap += '.';
                }
            }
            if (i != map.length - 1) {
                printableMap += "\n";
            }
        }
        System.out.println(printableMap);
    }
    /***/
    private static void printNumberedMap() {
        String printableMap = "\n";
        for (int i = 0; i < map.length; i++) {
            for (int i2 = 0; i2 < map[i].length; i2++) {
                if (map[i][i2] == CELL_OPEN) {
                    if (gamemode == WARM_COLD_GAMEMODE) {
                        printableMap += numberedMap[i][i2];
                    } else {
                        printableMap += ' ';
                    }
                } else if (map[i][i2] == CELL_EMPTY) {
                    printableMap += '.';
                } else if (map[i][i2] == CELL_TREASURE) {
                    printableMap += '.';
                } else if (map[i][i2] == CELL_TREASURE_OPEN) {
                    printableMap += '+';
                } else if (map[i][i2] == CELL_TREASURE_NEAR) {
                    if (gamemode == WARM_COLD_GAMEMODE) {
                        printableMap += numberedMap[i][i2];
                    } else {
                        printableMap += '.';
                    }
                } else if (map[i][i2] == CELL_TREASURE_NEAR_OPEN) {
                    printableMap += numberedMap[i][i2];
                }
            }
            if (i != map.length - 1) {
                printableMap += "\n";
            }
        }
        System.out.println(printableMap);
    }

    /**
     * @param board - board
     */
    private static void printScore(int[] board) {
        String printableScore = "";
        int numberOfScore = 0;
        if (gamemode == DEFAULT_GAMEMODE) {
            System.out.println("Edetabel seadetega " + board[0] + 'x' + board[1] + ", " + board[2]);
        } else if (gamemode == MM_GAMEMODE) {
            System.out.println("MM tulemuste edetabel");
        }
        for (int i = 0; i < sCORE.size(); i++) {
            if (sCORE.get(i)[0] == board[0]
                    && sCORE.get(i)[1] == board[1]
                    && sCORE.get(i)[2] == board[2]
                    && sCORE.get(i)[MAGIC3 + 1] == gamemode) {
                numberOfScore += 1;
                if (numberOfScore >= SCORE_AMOUNT_MAX) {
                    printableScore += numberOfScore + ". " + sCORENAME.get(i) + "\t" + sCORE.get(i)[MAGIC3] + "\n";
                } else {
                    printableScore += numberOfScore + ".  " + sCORENAME.get(i) + "\t" + sCORE.get(i)[MAGIC3] + "\n";
                }
            }
        }
        /*System.out.println("DEBUG: size: " + sCORE.size());*/
        System.out.print(printableScore);
    }

    /**
     * @param board - board
     */
    private static void clearScore(int[] board) {
        ArrayList<Integer> toRemove = new ArrayList<>();
        for (int i = sCORE.size() - 1; i >= 0; i--) {
            if (sCORE.get(i)[0] == board[0]
                    && sCORE.get(i)[1] == board[1]
                    && sCORE.get(i)[2] == board[2]
                    && sCORE.get(i)[MAGIC3 + 1] == gamemode) {
                toRemove.add(i);
            }
        }
        for (int i = 0; i < toRemove.size(); i++) {
            /*System.out.println("DEBUG: " + toRemove.get(i));*/
            int remove = toRemove.get(i);
            sCORE.remove(remove);
            sCORENAME.remove(remove);
        }
        if (gamemode == DEFAULT_GAMEMODE) {
            System.out.println("Edetabel seadetega " + board[0] + "x" + board[1] + ", " + board[2] + " puhastatud");
        } else if (gamemode == MM_GAMEMODE) {
            System.out.println("MM'i edetabel puhastatud");
        }
    }
    /**
    * @param score - kaevamiste arv.
    * @param board - game x and y.
    */
    private static void scoring(int score, int[] board)  {
        int[] entry = new int[MAGIC3 + 2];
        String input;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.print("Sisesta nimi: ");
            input = scan.nextLine();
        } while (input.length() < NAME_LENGTH_MIN || input.length() > NAME_LENGTH_MAX);
        int amountOfScores = 0;
        /*System.out.println("DEBUG: " + sCORE + " ");*/
        if (sCORE.size() == 0) {
            entry[0] = board[0];
            entry[1] = board[1];
            entry[2] = board[2];
            entry[MAGIC3] = score;
            entry[MAGIC3 + 1] = gamemode;
            sCORE.add(0, entry);
            sCORENAME.add(0, input);
            /*System.out.println("DEBUG: size 0 " + sCORE.get(0)[0]);*/
        } else {
            /*System.out.println("DEBUG: else " + sCORE.get(0)[0] + ' '
                    + sCORE.get(0)[1] + ' ' + sCORE.get(0)[2] + ' '
                    + sCORE.get(0)[MAGIC3] + ' ' + sCORE.size());*/
            for (int i = 0; i < sCORE.size(); i++) {
                /*System.out.println("DEBUG PRINT: "
                        + sCORE.get(i)[0] + ' '
                        + sCORE.get(i)[1] + ' '
                        + sCORE.get(i)[2] + ' '
                        + sCORE.get(i)[MAGIC3] + ' '
                        + sCORE.size());*/
                if (sCORE.get(i)[0] == board[0]
                        && sCORE.get(i)[1] == board[1]
                        && sCORE.get(i)[2] == board[2]
                        && sCORE.get(i)[MAGIC3 + 1] == gamemode) {
                    if (sCORE.get(i)[MAGIC3] > score) {
                        entry[0] = board[0];
                        entry[1] = board[1];
                        entry[2] = board[2];
                        entry[MAGIC3] = score;
                        entry[MAGIC3 + 1] = gamemode;
                        sCORE.add(i, entry);
                        sCORENAME.add(i, input);
                        /*System.out.println("DEBUG: IF COND." + sCORE.get(i)[0] + ' ' + sCORE.size());*/
                        break;
                    } else if (sCORE.get(i)[MAGIC3] == score) {
                        entry[0] = board[0];
                        entry[1] = board[1];
                        entry[2] = board[2];
                        entry[MAGIC3] = score;
                        entry[MAGIC3 + 1] = gamemode;
                        sCORE.add(entry);
                        sCORENAME.add(input);
                        /*System.out.println("DEBUG: IF COND." + sCORE.get(i)[0] + ' ' + sCORE.size());*/
                        break;
                    }
                } else {
                    entry[0] = board[0];
                    entry[1] = board[1];
                    entry[2] = board[2];
                    entry[MAGIC3] = score;
                    entry[MAGIC3 + 1] = gamemode;
                    sCORE.add(entry);
                    sCORENAME.add(input);
                    /*System.out.println("DEBUG: IF COND." + sCORE.get(i)[0] + ' ' + sCORE.size());*/
                    break;
                    }
                }
            }
        /*Remove last entry if more than 10 entries for specific settings are in the score list.*/
        for (int i = 0; i < sCORE.size(); i++) {
            if (sCORE.get(i)[0] == board[0]
                    && sCORE.get(i)[1] == board[1]
                    && sCORE.get(i)[2] == board[2]
                    && sCORE.get(i)[MAGIC3 + 1] == gamemode) {
                amountOfScores += 1;
                if (amountOfScores > SCORE_AMOUNT_MAX
                        && sCORE.size() > 0
                        && (i + 1 >= sCORE.size()
                        || (sCORE.get(i + 1)[0] != board[0]
                        && sCORE.get(i + 1)[1] != board[1]
                        && sCORE.get(i + 1)[2] != board[2]
                        && sCORE.get(i + 1)[MAGIC3 + 1] != gamemode))) {
                    sCORENAME.remove(i);
                    sCORE.remove(i);
                    break;
                }
            }
        }
    }

    /**
     * Takes care of the turns.
     * @param intArray - array from intToInput()
     * @return score value. only necessary for MM.
     */
    private static int tryInput(int[] intArray) {
        String input;
        boolean printAare;
        Scanner scan = new Scanner(System.in);
        int aardeidLeida = intArray[2];
        int kaevamisi = 0;
        /**/
        while (aardeidLeida > 0) {
            printAare = false;
            System.out.print("kaevamisi: " + kaevamisi
                    + ", aardeid jäänud: " + aardeidLeida);
            int[] checkInMap = null;
            /**/
            if (KEEP_SCORE) {
                do {
                    System.out.print("\nkäik (rida, veerg) või käsk: ");
                    input = scan.nextLine().toLowerCase();
                    if (input.equals("edetabel")) {
                        System.out.println(' ');
                        printScore(intArray);
                        continue;
                    } else if (input.equals("puhasta")) {
                        System.out.println(' ');
                        clearScore(intArray);
                        continue;
                    }
                    checkInMap = intFromInput(input);
                } while (checkInMap == null || checkInMap.length != 2);
                /**/
            } else {
                do {
                    System.out.print("Mida kaevame (rida, veerg): ");
                    input = scan.nextLine();
                    checkInMap = intFromInput(input);
                } while (checkInMap == null || checkInMap.length != 2);
            }
            int move = makeMove(checkInMap[0], checkInMap[1]);
            if (move == CELL_TREASURE) {
                printAare = true;
                aardeidLeida -= 1;
            }
            if (move != CELL_ERROR) {
                kaevamisi += 1;
            }
            if (OPEN_MAP) {
                printNumberedMap();
            } else if (gamemode == WARM_COLD_GAMEMODE) {
                printNumberedMap();
            } else {
                printMap();
            }
            if (printAare) {
                System.out.println("AARE!\n");
            }
        }
        if (gamemode == DEFAULT_GAMEMODE) {
            System.out.println("Mäng läbi! Kokku tehti "
                    + kaevamisi + " kaevamist.");
        }
        if (KEEP_SCORE && gamemode != MM_GAMEMODE) {
            scoring(kaevamisi, intArray);
        }
        return kaevamisi;
    }
    /**
     * Call functions for one try of the game.
     */
    private static void core() {
        if (gamemode == MM_GAMEMODE) {
            int row = MM_BOARD_ROWS;
            int col = MM_BOARD_COLUMNS;
            int treasureAmount = MM_BOARD_TREASURES;
            int score = 0;
            int[] intArray = new int[MAGIC3];
            System.out.print("Osalete MM-il.\n"
                    + "Teil on vaja mängida " + MM_ROUNDS + " järjestikust raundi.\n"
                    + "Edu!\n");
            for (int i = 1; i <= MM_ROUNDS; i++) {
                intArray[0] = row * i;
                intArray[1] = col * i;
                intArray[2] = treasureAmount + i;
                createMap(intArray[0], intArray[1], intArray[2]);
                printMap();
                score += tryInput(intArray);
                System.out.print("\nRound " + i + " läbi.\n"
                        + "Hetkel kokku " + score + " kaevamist.\n"
                        + "Algab round " + (i + 1) + " platsiga " + (row * (i + 1)) + "x" + (col * (i + 1)) + "\n");
            }
            scoring(score, intArray);
        } else {
            int[] intArray;
            do {
                intArray = startInput();
            } while (intArray == null);
            tryInput(intArray);
        }
    }

    /**
     * The main method, which is the entry point of the program.
     * @param args Arguments from the command line
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String input = "jah";
        String gamemodeString;
        while (input.equals("jah")) {
            /*Gamemode choice*/
            do {
                System.out.print("Mis gamemode soovid mängida? (tavaline, MM, soe-külm): ");
                gamemodeString = scan.nextLine().toLowerCase();
                System.out.println(' ');
                if (gamemodeString.equals("tavaline")) {
                    gamemode = DEFAULT_GAMEMODE;
                } else if (gamemodeString.equals("mm")) {
                    gamemode = MM_GAMEMODE;
                } else if (gamemodeString.equals("soe-külm")) {
                    gamemode = WARM_COLD_GAMEMODE;
                } else {
                    gamemodeString = null;
                }
            } while (gamemodeString == null);
            /*Enter to game*/
            core();
            /*Replay option*/
            do {
                System.out.print("\nKas soovid veel mängida? ");
                input = scan.nextLine();
                input = input.toLowerCase();
            } while (!input.equals("jah") && !input.equals("ei"));
        }
    }
}
