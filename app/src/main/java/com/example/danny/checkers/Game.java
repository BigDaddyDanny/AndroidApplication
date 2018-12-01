package com.example.danny.checkers;

import android.util.Log;

public class Game {

    private static final long serialVersionUID = 1L;

    private boolean playerTurn;
    private boolean AI_ACTIVATED;
    public static boolean game;
    public static String winName;

    private static Game instance;

    public static Game getInstance() {


        if(instance == null) {
            instance = new Game();
        }

        return instance;
    }
    Game(){
        winName = " ";
    }

 /*
    public void mouseClicked(MouseEvent e) {

        // Y and X differ between table array and JFrame
        // so they are switched here
        int newX = (int) ((e.getY() - 40) / Draw.WIDTH);// 40 and 10 are GUI adjustments - no need to change
        int newY = (int) ((e.getX() - 10) / Draw.WIDTH);

        //********Add way to turn off and on depending on whether AI is on or off******

        System.out.println(newX + ", " + newY);

        if(game)
            Board.getInstance().methodCaller(newX, newY);



        for (int i = 0; i < 8; i++) {
            System.out.println();
            for (int z = 0; z < 8; z++) {
                System.out.print(Board.getInstance().getTable()[i][z] + " ");
            } // end of for
        } // end of for

            }
        });
        //frame.setSize(Draw.SIZE + 18, Draw.SIZE + 47 + 50);// 47 and 18 are GUI adjustments, do not change - 50 is added to make space for turnName


*/
    public void throwError(String message) {
        Log.i("911",message);

    }

    public void winSequence(boolean player) {

        game = false;

        if (player)

           winName = "WINNER!";
        else
            winName = "TIE!";


    }// end of winSequence()


    public void changeTeam() {

        if (playerTurn)
            playerTurn = false;
        else {
            playerTurn = true;
            if(AI_ACTIVATED) {

                AI.getInstance().think();
            }

        }

    }//end of changeTeam();

    public boolean getPlayerTurn() {
        return playerTurn;
    }

    public void setAI(boolean on){
        if (on){
            AI_ACTIVATED = true;
        }else{
            AI_ACTIVATED = false;
        }


    }
}//end of class

