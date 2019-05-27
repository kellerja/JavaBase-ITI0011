package gomoku.strategies;

import gomoku.ComputerStrategy;
import gomoku.Location;
import gomoku.SimpleBoard;

import java.util.ArrayList;
import java.util.List;

/***/
public class AlphaBetaFreshStart implements ComputerStrategy {
    /***/
    private int me;
    /***/
    private int enemy;
    /***/
    private Location bestMove;
    /***/
    private int selectedDepth = 2;
    /***/
    private boolean firstMove = true;

    /**
     * @param board - board where to search for empty spaces.
     * @return list of empty places on board.
     */
    private List<Location> possibleMoves(int[][] board) {
        List<Location> moves = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == 0) {
                    moves.add(new Location(row, col));
                }
            }
        }
        return moves;
    }

    /**
     * @param a - one int.
     * @param b - second int.
     * @return larger of twi ints.
     */
    private int max(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    /**
     * @param a - one int.
     * @param b - second int.
     * @return smaller if two ints.
     */
    private int min(int a, int b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }

    /**
     * @param count - number of similar cells in a line.
     * @param endOne - true if there is an empty space in the end.
     * @param endTwo - true if there is an empty space in the other end.
     * @param player - what cells are being evaluated.
     * @return cell value/score.
     */
    private int evaluateCellScore(int count, int player, boolean endOne, boolean endTwo) {
        final int largestUsefulLength = 5;
        final int crucialMostCrucialSituation = 10;
        final int scoreMainBase = 10;
        int makeNegative, extra = 1, crucial = 1;
        makeNegative = player == me ? 1 : -1;
        if (endOne) {
            extra++;
        }
        if (endTwo) {
            extra++;
        }
        if (count == largestUsefulLength) {
            crucial += crucialMostCrucialSituation;
        }
        if (count == largestUsefulLength - 1) {
            crucial++;
        }
        if (count == largestUsefulLength - 2 && endOne && endTwo) {
            crucial++;
        }
        return (int) Math.pow(scoreMainBase, count) * makeNegative * extra * crucial;
    }

    /**
     * @param board - board where to give value.
     * @return value of the board.
     */
    private int getScore(int[][] board) {
        final int maxUsefulLength = 5;
        int score = 0;
        //System.out.println("SCORING STARTED");
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                int pos = board[row][col];
                if (pos == SimpleBoard.EMPTY) continue;
                int count = 0, toBlock = pos == me ? enemy : me;
                boolean upOpen = false, downOpen = false, leftOpen = false, rightOpen = false;
                boolean upLeftOpen = false, upRightOpen = false, downLeftOpen = false, downRightOpen = false;
                /*Check up*/
                //System.out.println("UP CHECK STARTING FROM POSITION: (" + col + ", " + row + ")");
                for (int y = row; y >= row - maxUsefulLength; y--) {
                    if (y == row - maxUsefulLength && y >= 0 && board[y][col] != toBlock) {
                        upOpen = true;
                    } else if (y >= 0 && pos == board[y][col]) {
                        count++;
                    } else {
                        if (y >= 0 && board[y][col] != toBlock) upOpen = true;
                        break;
                    }
                }
                //System.out.println("UP CHECKED FROM POSITION: (" + col + ", " + row + ")");
                /*Check down*/
                //System.out.println("DOWN CHECK STARTING FROM POSITION: (" + col + ", " + row + ")");
                for (int y = row + 1; y <= row + maxUsefulLength; y++) {
                    if (y == row + maxUsefulLength && y < board.length && board[y][col] != toBlock) {
                        downOpen = true;
                    } else if (y < board.length && pos == board[y][col]) {
                        count++;
                    } else {
                        if (y < board.length && board[y][col] != toBlock) downOpen = true;
                        break;
                    }
                }
                //System.out.println("DOWN CHECKED FROM POSITION: (" + col + ", " + row + ")");
                //EVALUATE
                if (!(count < maxUsefulLength && !upOpen && !downOpen)) {
                    score += evaluateCellScore(count, pos, upOpen, downOpen);
                }
                //System.out.println("UP-DOWN SCORE: " + score);
                count = 0;
                /*Check left*/
                //System.out.println("LEFT CHECK STARTING FROM POSITION: (" + col + ", " + row + ")");
                for (int x = col; x >= col - maxUsefulLength; x--) {
                    if (x == col - maxUsefulLength && x >= 0 && board[row][x] != toBlock) {
                        leftOpen = true;
                    } else if (x >= 0 && pos == board[row][x]) {
                        count++;
                    } else {
                        if (x >= 0 && board[row][x] != toBlock) leftOpen = true;
                        break;
                    }
                }
                //System.out.println("LEFT CHECKED FROM POSITION: (" + col + ", " + row + ")");
                /*Check right*/
                //System.out.println("RIGHT CHECK STARTING FROM POSITION: (" + col + ", " + row + ")");
                for (int x = col + 1; x <= col + maxUsefulLength; x++) {
                    if (x == col + maxUsefulLength && x < board[0].length && board[row][x] != toBlock) {
                        rightOpen = true;
                    } else if (x < board[0].length && pos == board[row][x]) {
                        count++;
                    } else {
                        if (x < board[0].length && board[row][x] != toBlock) rightOpen = true;
                        break;
                    }
                }
                //System.out.println("RIGHT CHECKED FROM POSITION: (" + col + ", " + row + ")");
                //EVALUATE
                if (!(count < maxUsefulLength && !leftOpen && !rightOpen)) {
                    score += evaluateCellScore(count, pos, leftOpen, rightOpen);
                }
                //System.out.println("LEFT-RIGHT SCORE: " + score);
                count = 0;
                /*Check up-left*/
                //System.out.println("UP-LEFT CHECK STARTING FROM POSITION: (" + col + ", " + row + ")");
                for (int y = row, x = col; y >= row - maxUsefulLength; y--, x--) {
                    if (y == row - maxUsefulLength && y >= 0 && x >= 0 && board[y][x] != toBlock) {
                        upLeftOpen = true;
                    } else if (x >= 0 && y >= 0 && pos == board[y][x]) {
                        count++;
                    } else {
                        if (y >= 0 && x >= 0 && board[y][x] != toBlock) upLeftOpen = true;
                        break;
                    }
                }
                //System.out.println("UP-LEFT CHECKED FROM POSITION: (" + col + ", " + row + ")");
                /*Check down-right*/
                //System.out.println("DOWN-RIGHT CHECK STARTING FROM POSITION: (" + col + ", " + row + ")");
                for (int y = row + 1, x = col + 1; y <= row + maxUsefulLength; y++, x++) {
                    if (y == row + maxUsefulLength
                            && y < board.length && x < board[0].length && board[y][x] != toBlock) {
                        downRightOpen = true;
                    } else if (y < board.length && x < board[0].length && pos == board[y][x]) {
                        count++;
                    } else {
                        if (y < board.length && x < board[0].length && board[y][x] != toBlock) downRightOpen = true;
                        break;
                    }
                }
                //System.out.println("DOWN-RIGHT CHECKED FROM POSITION: (" + col + ", " + row + ")");
                //EVALUATE
                if (!(count < maxUsefulLength && !upLeftOpen && !downRightOpen)) {
                    score += evaluateCellScore(count, pos, upLeftOpen, downRightOpen);
                }
                //System.out.println("UP-LEFT-DOWN-RIGHT SCORE: " + score);
                count = 0;
                /*Check up-right*/
                //System.out.println("UP-RIGHT CHECK STARTING FROM POSITION: (" + col + ", " + row + ")");
                for (int y = row, x = col; y >= row - maxUsefulLength; y--, x++) {
                    if (y == row - maxUsefulLength && y >= 0 && x < board[0].length && board[y][x] != toBlock) {
                        upRightOpen = true;
                    } else if (y >= 0 && x < board[0].length && pos == board[y][x]) {
                        count++;
                    } else {
                        if (y >= 0 && x < board[0].length && board[y][x] != toBlock) upRightOpen = true;
                        break;
                    }
                }
                //System.out.println("UP-RIGHT CHECKED FROM POSITION: (" + col + ", " + row + ")");
                /*Check down-left*/
                //System.out.println("DOWN-LEFT CHECK STARTING FROM POSITION: (" + col + ", " + row + ")");
                for (int y = row + 1, x = col - 1; y <= row + maxUsefulLength; y++, x--) {
                    if (y == row + maxUsefulLength && y < board.length && x >= 0 && board[y][x] != toBlock) {
                        downLeftOpen = true;
                    } else if (y < board.length && x >= 0 && pos == board[y][x]) {
                        count++;
                    } else {
                        if (y < board.length && x >= 0 && board[y][x] != toBlock) downLeftOpen = true;
                        break;
                    }
                }
                //System.out.println("DOWN-LEFT CHECKED FROM POSITION: (" + col + ", " + row + ")");
                //EVALUATE
                if (!(count < maxUsefulLength && !upRightOpen && !downLeftOpen)) {
                    score += evaluateCellScore(count, pos, upRightOpen, downLeftOpen);
                }
                //System.out.println("UP-RIGHT-DOWN-LEFT SCORE: " + score);
                /*Add Score for blocking enemy*/
                count = 0;
                upOpen = false;
                downOpen = false;
                /*Check up*/
                //System.out.println("UP CHECK STARTING FROM POSITION: (" + col + ", " + row + ")");
                for (int y = row - 1; y >= row - maxUsefulLength - 1; y--) {
                    if (y == row - maxUsefulLength - 1 && y >= 0 && board[y][col] == SimpleBoard.EMPTY) {
                        upOpen = true;
                    } else if (y >= 0 && toBlock == board[y][col]) {
                        count++;
                    } else {
                        if (y >= 0 && board[y][col] == SimpleBoard.EMPTY) upOpen = true;
                        break;
                    }
                }
                //EVALUATE
                if (!(count < maxUsefulLength - 1 && !upOpen)) {
                    score += evaluateCellScore(count, pos, upOpen, false);
                }
                count = 0;
                /*Check down*/
                //System.out.println("DOWN CHECK STARTING FROM POSITION: (" + col + ", " + row + ")");
                for (int y = row + 1; y <= row + maxUsefulLength + 1; y++) {
                    if (y == row + maxUsefulLength + 1 && y < board.length && board[y][col] == SimpleBoard.EMPTY) {
                        downOpen = true;
                    } else if (y < board.length && toBlock == board[y][col]) {
                        count++;
                    } else {
                        if (y < board.length && board[y][col] == SimpleBoard.EMPTY) downOpen = true;
                        break;
                    }
                }
                //System.out.println("DOWN CHECKED FROM POSITION: (" + col + ", " + row + ")");
                //EVALUATE
                if (!(count < maxUsefulLength - 1 && !downOpen)) {
                    score += evaluateCellScore(count, pos, false, downOpen);
                }
                /*Check left*/
                count = 0;
                leftOpen = false;
                rightOpen = false;
                //System.out.println("LEFT CHECK STARTING FROM POSITION: (" + col + ", " + row + ")");
                for (int x = col - 1; x >= col - maxUsefulLength - 1; x--) {
                    if (x == col - maxUsefulLength - 1 && x >= 0 && board[row][x] == SimpleBoard.EMPTY) {
                        leftOpen = true;
                    } else if (x >= 0 && toBlock == board[row][x]) {
                        count++;
                    } else {
                        if (x >= 0 && board[row][x] == SimpleBoard.EMPTY) leftOpen = true;
                        break;
                    }
                }
                //System.out.println("LEFT CHECKED FROM POSITION: (" + col + ", " + row + ")");
                //EVALUATE
                if (!(count < maxUsefulLength - 1 && !leftOpen)) {
                    score += evaluateCellScore(count, pos, leftOpen, false);
                }
                /*Check right*/
                count = 0;
                //System.out.println("RIGHT CHECK STARTING FROM POSITION: (" + col + ", " + row + ")");
                for (int x = col + 1; x <= col + maxUsefulLength + 1; x++) {
                    if (x == col + maxUsefulLength + 1 && x < board[0].length && board[row][x] == SimpleBoard.EMPTY) {
                        rightOpen = true;
                    } else if (x < board[0].length && toBlock == board[row][x]) {
                        count++;
                    } else {
                        if (x < board[0].length && board[row][x] == SimpleBoard.EMPTY) rightOpen = true;
                        break;
                    }
                }
                //System.out.println("RIGHT CHECKED FROM POSITION: (" + col + ", " + row + ")");
                //EVALUATE
                if (!(count < maxUsefulLength - 1 && !rightOpen)) {
                    score += evaluateCellScore(count, pos, false, rightOpen);
                }
                //System.out.println("LEFT-RIGHT SCORE: " + score);
                count = 0;
                upLeftOpen = false;
                downRightOpen = false;
                /*Check up-left*/
                //System.out.println("UP-LEFT CHECK STARTING FROM POSITION: (" + col + ", " + row + ")");
                for (int y = row - 1, x = col - 1; y >= row - maxUsefulLength - 1; y--, x--) {
                    if (y == row - maxUsefulLength - 1 && y >= 0 && x >= 0 && board[y][x] == SimpleBoard.EMPTY) {
                        upLeftOpen = true;
                    } else if (x >= 0 && y >= 0 && toBlock == board[y][x]) {
                        count++;
                    } else {
                        if (y >= 0 && x >= 0 && board[y][x] != SimpleBoard.EMPTY) upLeftOpen = true;
                        break;
                    }
                }
                //System.out.println("UP-LEFT CHECKED FROM POSITION: (" + col + ", " + row + ")");
                //EVALUATE
                if (!(count < maxUsefulLength - 1 && !upLeftOpen)) {
                    score += evaluateCellScore(count, pos, upLeftOpen, false);
                }
                /*Check down-right*/
                count = 0;
                //System.out.println("DOWN-RIGHT CHECK STARTING FROM POSITION: (" + col + ", " + row + ")");
                for (int y = row + 1, x = col + 1; y <= row + maxUsefulLength + 1; y++, x++) {
                    if (y == row + maxUsefulLength + 1
                            && y < board.length
                            && x < board[0].length
                            && board[y][x] == SimpleBoard.EMPTY) {
                        downRightOpen = true;
                    } else if (y < board.length && x < board[0].length && toBlock == board[y][x]) {
                        count++;
                    } else {
                        if (y < board.length && x < board[0].length
                                && board[y][x] == SimpleBoard.EMPTY) downRightOpen = true;
                        break;
                    }
                }
                //System.out.println("DOWN-RIGHT CHECKED FROM POSITION: (" + col + ", " + row + ")");
                //EVALUATE
                if (!(count < maxUsefulLength - 1 && !downRightOpen)) {
                    score += evaluateCellScore(count, pos, false, downRightOpen);
                }
                //System.out.println("UP-LEFT-DOWN-RIGHT SCORE: " + score);
                upRightOpen = false;
                downLeftOpen = false;
                count = 0;
                /*Check up-right*/
                //System.out.println("UP-RIGHT CHECK STARTING FROM POSITION: (" + col + ", " + row + ")");
                for (int y = row - 1, x = col + 1; y >= row - maxUsefulLength - 1; y--, x++) {
                    if (y == row - maxUsefulLength - 1 && y >= 0
                            && x < board[0].length && board[y][x] == SimpleBoard.EMPTY) {
                        upRightOpen = true;
                    } else if (y >= 0 && x < board[0].length && toBlock == board[y][x]) {
                        count++;
                    } else {
                        if (y >= 0 && x < board[0].length && board[y][x] == SimpleBoard.EMPTY) upRightOpen = true;
                        break;
                    }
                }
                //System.out.println("UP-RIGHT CHECKED FROM POSITION: (" + col + ", " + row + ")");
                //EVALUATE
                if (!(count < maxUsefulLength - 1 && !upRightOpen)) {
                    score += evaluateCellScore(count, pos, upRightOpen, false);
                }
                /*Check down-left*/
                count = 0;
                //System.out.println("DOWN-LEFT CHECK STARTING FROM POSITION: (" + col + ", " + row + ")");
                for (int y = row + 1, x = col - 1; y <= row + maxUsefulLength + 1; y++, x--) {
                    if (y == row + maxUsefulLength + 1 && y < board.length
                            && x >= 0 && board[y][x] == SimpleBoard.EMPTY) {
                        downLeftOpen = true;
                    } else if (y < board.length && x >= 0 && toBlock == board[y][x]) {
                        count++;
                    } else {
                        if (y < board.length && x >= 0 && board[y][x] == SimpleBoard.EMPTY) downLeftOpen = true;
                        break;
                    }
                }
                //System.out.println("DOWN-LEFT CHECKED FROM POSITION: (" + col + ", " + row + ")");
                //EVALUATE
                if (!(count < maxUsefulLength - 1 && !downLeftOpen)) {
                    score += evaluateCellScore(count, pos, false, downLeftOpen);
                }
            }
        }
        //System.out.println("SCORING ENDED WITH SCORE: " + score);
        return score;
    }

    /**
     * @param player - player who is evaluated.
     * @param a - alpha.
     * @param b - beta.
     * @param board - game board.
     * @param depth - depth of the minimax algorithm.
     * @return best move score.
     */
    private int alphabeta(int[][] board, int depth, int a, int b, int player) {
        if (depth == 0) {
            return getScore(board);
        }
        List<Location> moves = possibleMoves(board);
        if (player == me) {
            int highScore = Integer.MIN_VALUE;
            for (Location move: moves) {
                board[move.getRow()][move.getColumn()] = me;
                a = max(a, alphabeta(board, depth - 1, a, b, enemy));
                board[move.getRow()][move.getColumn()] = SimpleBoard.EMPTY;
                if (depth == selectedDepth && a > highScore) {
                    highScore = a;
                    bestMove = move;
                }
                if (a >= b) break;
            }
            return a;
        } else {
            for (Location move: moves) {
                board[move.getRow()][move.getColumn()] = enemy;
                b = min(b, alphabeta(board, depth - 1, a, b, me));
                board[move.getRow()][move.getColumn()] = SimpleBoard.EMPTY;
                if (a >= b) break;
            }
            return b;
        }
    }

    @Override
    public Location getMove(SimpleBoard board, int player) {
        if (firstMove && player == SimpleBoard.PLAYER_WHITE) {
            firstMove = false;
            return new Location(board.getHeight() / 2, board.getWidth() / 2);
        } else if (firstMove && player == SimpleBoard.PLAYER_BLACK) {
            firstMove = false;
            List<Location> moves = possibleMoves(board.getBoard());
            return moves.get(moves.size() / 2 + board.getWidth() / 2);
        }
        int[][] b = board.getBoard();
        me = player;
        enemy = player == SimpleBoard.PLAYER_WHITE ? SimpleBoard.PLAYER_BLACK : SimpleBoard.PLAYER_WHITE;
        bestMove = null;
        alphabeta(b, selectedDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, player);
        return bestMove;
    }

    @Override
    public String getName() {
        return "AlphaBetaNewStart";
    }
}
