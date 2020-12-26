package core.pieces;

import core.MoveType;
import core.suit;

import java.util.ArrayList;

public class bishop implements piece {
    public static final int COST = 3;
    public bishop() {
    };

    @Override
    public ArrayList<String> getAvailableMoves(String[][] board, String coord) {
        int x = (int) coord.charAt(0);
        int y = Integer.parseInt(coord.substring(1,2));
        ArrayList<String> moves = new ArrayList<String>();
        
        for(int i = 0; i < 8; i++){
            int xT = (x + i) - 97;
            int yT = y - i;

            moves.add(buildCoordString(xT, yT));
            if(xT >= 8 || yT <= 0 || !board[xT][yT].equals("0")) break;

        }// +x -y
        for(int i = 0; i < 8; i++){
            int xT = (x - i) - 97;
            int yT = y + i;

            moves.add(buildCoordString(xT, yT));
    
            if(xT <=  0 || yT >= 8 || !board[xT][yT].equals("0")) break;

        }// -x +y
        for(int i = 0; i < 8; i++){
            int xT = (x + i) - 97;
            int yT = y + i;

            moves.add(buildCoordString(xT, yT));
            if(xT >= 8 || yT >= 8 || !board[xT][yT].equals("0")) break;

        }//+x +y
        for(int i = 0; i < 8; i++){
            int xT = (x - i) - 97;
            int yT = y - i;

            moves.add(buildCoordString(xT, yT));
            if(xT <= 0 || yT <= 0 || !board[xT][yT].equals("0")) break;

        }// -x -y
        return moves;
    }

    @Override
    public String buildCoordString(int x, int y) {
        return Character.toString((char) (x + 97)) + Integer.toString(y); //the x value comes in as an index;
        
    }

    @Override
    public MoveType isValid(String[][] board, Integer[] coords) {
        int x0 = coords[0]; int y0 = coords[1];
        int xf = coords[2]; int yf = coords[3];

        suit suit0 = suit.getSuit(board[x0][y0]);
        suit suitF = suit.getSuit(board[xf][yf]);
        System.out.printf("%s , %s",suit0.toString(), suitF.toString());
        if(!onBoard(xf,yf)){

            return MoveType.NONE;
        }
        if(Math.abs(xf - x0) == Math.abs(yf - y0)){

           return  MoveType.eval(suit0, suitF);
        }
        return MoveType.NONE;
    }

    @Override
    public  boolean onBoard(int x, int y) {
        if (x < 0 || x >= 8) {
            return false;
        }
        return y >= 0 && y < 8;
    }

}