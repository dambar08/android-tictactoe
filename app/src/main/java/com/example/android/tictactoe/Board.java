package com.example.android.tictactoe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dambar Bahadur Pun on 2019-07-03.
 */
public class Board {
    public  static final int NO_PLAYER = 0;
    public static final int PLAYER_X = 1;
    public  static final int PLAYER_O = 2;
    public int[][] board = new int[3][3];
    public Point computerMove;

    /**
     * Checks if any player has won
     * @param player X or O
     * @return true if (player) has won
     */
    public boolean hasPlayerWon(int player) {
        //diagonal checks
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == player) ||
                (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == player)) {
            return true;
        }
        //horizontal and vertical checks
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == player) ||
                    (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == player))
                return true;
        }
        return false;
    }

    /**
     * Checks if there is any space on the board
     * @return List<Point>
     */
    public List<Point> getAvailableCells() {
        List<Point> availableCells = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == NO_PLAYER)
                    availableCells.add(new Point(i, j));
            }
        }
        return availableCells;
    }

    /**
     * Places the move to the board
     * @param point x,y coordinates of the board
     * @param player
     * @return
     */
    public boolean placeAMove(Point point, int player) {
        //return false if the place is occupied
        if (board[point.x][point.y] != NO_PLAYER) {
            return false;
        }
        //returns true when the x,y coordinates of the board is free
        board[point.x][point.y] = player;
        return true;
    }

    /**
     * Recursive method of minimax algorithm
     * @param depth the depth of the Node
     * @param turn if the Node is the minimizer or maximizer
     * @return value of Node
     */
    public int minimax(int depth, int turn) {
        if (hasPlayerWon(PLAYER_X))
            return 1;
        if (hasPlayerWon(PLAYER_O))
            return -1;
        List<Point> availableCells = getAvailableCells();
        if (availableCells.isEmpty())
            return 0;

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < availableCells.size(); i++) {
            Point point = availableCells.get(i);

            if (turn == PLAYER_X) {
                placeAMove(point, PLAYER_X);
                int currentScore = minimax(depth + 1, PLAYER_O);
                max = Math.max(currentScore, max);

                if (currentScore >= 0)
                    if (depth == 0)
                        computerMove = point;

                if (currentScore == 1) {
                    board[point.x][point.y] = NO_PLAYER;
                    break;
                }

                if (i == availableCells.size() - 1 && max < 0)
                    if (depth == 0)
                        computerMove = point;

            } else if (turn == PLAYER_O) {
                placeAMove(point, PLAYER_O);
                int currentScore = minimax(depth + 1, PLAYER_X);
                min = Math.min(currentScore, min);

                if (min == -1) {
                    board[point.x][point.y] = NO_PLAYER;
                    break;
                }
            }
            board[point.x][point.y] = NO_PLAYER;
        }
        return turn == PLAYER_X ? max : min;
    }
}



