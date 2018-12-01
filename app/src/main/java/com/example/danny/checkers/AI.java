package com.example.danny.checkers;

import java.util.ArrayList;
import java.util.Arrays;

public class AI {

    private final int KING_VALUE = 5;
    private int turn;

    private static AI instance;

    public static AI getInstance() {

        if (instance == null)
            instance = new AI();
        return instance;
    }

    AI() {

        turn = 0;

    }

    public void think() {
        turn++;
        String[][] board = copy(Board.getInstance().getTable());

        ArrayList<String> moves = getBoardMoves(board, true);

        String[][] temp;
        String[][][] possibleBoards = new String[moves.size()][8][8];

        ArrayList<String> listOfMoves = new ArrayList<String>();
        ArrayList<String> listOfMoves2 = new ArrayList<String>();
        ArrayList<Integer> boardValues = new ArrayList<Integer>();
        ArrayList<String[][]> possB = new ArrayList<String[][]>();

        for (int i = 0; i < moves.size(); i++) {

            temp = transformBoard(board, moves.get(i));

            possibleBoards[i] = transformBoard(temp, getBestEnemyMove(temp));
            listOfMoves = getBoardMoves(possibleBoards[i], true);

            for (String m : listOfMoves) {

                listOfMoves2.add(moves.get(i) + m);
                System.out.println();
                possB.add(transformBoard(possibleBoards[i], m));

            }
        } // end of for

        for (String[][] b : possB) {

            boardValues.add(getBoardValue(b));

        }

        int index = getHighest(boardValues);
        if(index == -1){

        }
        String selectedMove = (listOfMoves2.get(index));

        for (String m : moves) {

            if (selectedMove.contains(m)) {

                selectedMove = m;

            } // end of if

        } // end of for

        // System.out.println(index + " : " + selectedMove);

        for (int i = 0; i < (selectedMove.length() - 2); i += 2) {

            Board.getInstance().AImethodCaller(Integer.parseInt(selectedMove.substring(i, i + 1)),
                    Integer.parseInt(selectedMove.substring(i + 1, i + 2)),
                    Integer.parseInt(selectedMove.substring(i + 2, i + 3)),
                    Integer.parseInt(selectedMove.substring(i + 3, i + 4)));

        } // end of for

    }// end of think()

    private String getBestEnemyMove(String[][] b) {

        ArrayList<String> moves = getBoardMoves(b, false);
        ArrayList<Integer> boardValues = new ArrayList<Integer>();
        String[][] temp;

        for (int i = 0; i < moves.size(); i++) {

            temp = transformBoard(b, moves.get(i));
            temp = transformBoard(temp, getBestMove(temp, true));
            boardValues.add(getBoardValue(temp));

        }
        int index = getLowest(boardValues);
        if(index == -1){
            Board.getInstance().checkForWin();
        }

        return moves.get(getLowest(boardValues));// return move that has lowest value, meaning it is best move for
        // player and worst move for AI

    }// end of getBestEnemyMove

    private String getBestMove(String[][] b, boolean team) {

        ArrayList<String> moves = getBoardMoves(b, team);// error

        ArrayList<Integer> boardValues = new ArrayList<Integer>();
        for (int i = 0; i < moves.size(); i++) {
            boardValues.add(getBoardValue(transformBoard(b, moves.get(i))));

        }
        int index = getLowest(boardValues);

        String bestMove = moves.get(index);

        return bestMove;
    }// end of getBestEnemyMove

    private int getBoardValue(String[][] b) {

        // Possible value adjustment ideas:
        //
        // - how close ai and opp is to getting their piece to become king

        int value = 0;

        for (int x = 0; x < 8; x++) {

            for (int y = 0; y < 8; y++) {

                if (b[x][y] == "1")
                    value++;
                if (b[x][y] == "2")
                    value--;
                if (b[x][y] == "3")
                    value += KING_VALUE;
                if (b[x][y] == "4")
                    value -= KING_VALUE;

            }
        }

        return value;
    }// end of get BoardValue

    private ArrayList<String> getBoardMoves(String[][] b, boolean team) {

        ArrayList<String> moves = new ArrayList<String>();

        int z;
        boolean check = false;
        String oppPiece;
        String oppKing;
        String teamPiece;
        String teamKing;

        if (team) {
            z = 1;
            oppPiece = "2";
            oppKing = "4";
            teamPiece = "1";
            teamKing = "3";
        } else {
            z = -1;
            oppPiece = "1";
            oppKing = "3";
            teamPiece = "2";
            teamKing = "4";
        }

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {

                if (b[x][y] != teamPiece && b[x][y] != teamKing)
                    continue;

                check = false;
                for (int counter = 0; counter < 1; counter++) {

                    for (int i = -1; i <= 1; i += 2) {

                        if (x + z < 0 || x + z > 7 || y + i < 0 || y + i > 7) {
                            continue;
                        }

                        if (b[x + z][y + i] == "x") {
                            moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x + z)
                                    + String.valueOf(y + i));
                            // add a continue here ***
                        } // end of if regular move available

                        if (x + (z * 2) < 0 || x + (z * 2) > 7 || y + (i * 2) < 0 || y + (i * 2) > 7) {
                            continue;
                        }
                        if ((b[x + z][y + i] == oppPiece || b[x + z][y + i] == oppKing)) {

                            if (b[x + (z * 2)][y + (i * 2)] == "x") {
                                moves = getJumpMoves(b, moves, x, y, x + (z * 2), y + (i * 2),
                                        String.valueOf(x) + String.valueOf(y) + String.valueOf(x + (z * 2))
                                                + String.valueOf(y + (i * 2)));
                            } // end of if

                        } // end of if jump available

                    } // end of y for

                    if (b[x][y] == teamKing && !check) {
                        check = true;
                        counter--;
                        z = -(z);
                    } // end of if

                } // end of counter for

            } // end of inner for
        } // end of outer for

        return moves;
    }// end of checkForBoardMoves

    private ArrayList<String> getJumpMoves(String[][] b, ArrayList<String> m, int x, int y, int newX, int newY,
                                           String prefix) {

        ArrayList<String> moves = m;
        String oppPiece;
        String oppKing;
        boolean atLeastOne = false;
        boolean check;
        boolean king;
        int z;

        if (b[x][y] == "3" || b[x][y] == "4") {
            king = true;
        } else {
            king = false;
        }

        if (b[x][y] == "1" || b[x][y] == "3") {
            z = 1;
            oppPiece = "2";
            oppKing = "4";
        } else {
            z = -1;
            oppPiece = "1";
            oppKing = "3";
        }

        b = transformBoard(b, String.valueOf(x) + String.valueOf(y) + String.valueOf(newX) + String.valueOf(newY));

        x = newX;
        y = newY;

        check = false;
        for (int counter = 0; counter < 1; counter++) {

            for (int i = -1; i <= 1; i += 2) {

                if (x + z < 0 || x + z > 7 || y + i < 0 || y + i > 7) {
                    continue;
                }
                if (x + (z * 2) < 0 || x + (z * 2) > 7 || y + (i * 2) < 0 || y + (i * 2) > 7) {
                    continue;
                }

                if ((b[x + z][y + i] == oppPiece || b[x + z][y + i] == oppKing)) {

                    if (b[x + (z * 2)][y + (i * 2)] == "x") {

                        // moves.add(String.valueOf(x) + String.valueOf(y) + String.valueOf(x + (z * 2))
                        // + String.valueOf(y + (i * 2)));
                        moves = getJumpMoves(b, moves, x, y, x + (z * 2), y + (i * 2),
                                prefix + String.valueOf(x + (z * 2)) + String.valueOf(y + (i * 2)));
                        atLeastOne = true;
                    }

                }

            } // end of inner for

            if (king && !check) {
                check = true;
                z = -(z);
                counter--;
            }
        } // end of outer for

        if (!atLeastOne) {
            moves.add(prefix);
        }

        return moves;
    }// end of getJumpMoves

    private String[][] transformBoard(String[][] board, String move) {

        String[][] b = copy(board);

        for (int i = 0; i < (move.length() - 2); i += 2) {

            int oldX = Integer.parseInt(move.substring(i, i + 1));
            int oldY = Integer.parseInt(move.substring(i + 1, i + 2));
            int newX = Integer.parseInt(move.substring(i + 2, i + 3));
            int newY = Integer.parseInt(move.substring(i + 3, i + 4));

            String movingPiece = b[oldX][oldY];

            b[oldX][oldY] = "x";

            if (Math.abs(oldX - newX) == 2) {
                int deadX = (newX + oldX) / 2;
                int deadY = (newY + oldY) / 2;

                b[deadX][deadY] = "x";
            }

            if ((movingPiece == "1" && newX == 7) || (movingPiece == "2" && newX == 0)) {

                if (movingPiece == "1")
                    movingPiece = "3";
                else
                    movingPiece = "4";
            }

            b[newX][newY] = movingPiece;
        } // end of for

        return b;
    }// end of transformBoard()

    private int getHighest(ArrayList<Integer> a) {

        if(a.size() == 0){
            return -1;
        }
        int max = 0;
        int arraySpot = 0;
        boolean first = true;
        for (int i = 0; i < a.size(); i++) {

            if (first) {
                first = false;
                max = a.get(i);
                arraySpot = i;
            }

            if (a.get(i) > max) {
                max = a.get(i);
                arraySpot = i;
            }

        } // end of for

        return arraySpot;
    }// end of getHighest()

    private int getLowest(ArrayList<Integer> a) {

        if(a.size() == 0){
            return -1;
        }
        int min = 0;
        int arraySpot = 0;
        boolean first = true;
        for (int i = 0; i < a.size(); i++) {

            if (first) {
                first = false;
                min = a.get(i);
                arraySpot = i;
            }
            if (a.get(i) < min) {
                min = a.get(i);
                arraySpot = i;
            }
        } // end of for

        return arraySpot;
    }// end of getLowest()

    private String[][] copy(String[][] b) {
        String[][] newB = new String[8][];
        for (int x = 0; x < 8; x++) {
            newB[x] = Arrays.copyOf(b[x], b[x].length);
        }

        return newB;
    }

    private static void wait(int time) {

        try {
            Thread.sleep(time * 1000);
            //TimeUnit.MILLISECONDS.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }// end of wait()

}// end of class

