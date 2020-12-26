package core.pieces;

import core.MoveType;
import core.suit;

import java.util.ArrayList;



public class rook implements piece {
    public rook(){}
    public static final int COST = 5;
    @Override
    public ArrayList<String> getAvailableMoves(String[][] board, String coord) {
        int x = (int) coord.charAt(0);
        int y = Integer.parseInt(coord.substring(1,2));

        ArrayList<String> moves = new ArrayList<String>();

        for(int i = y; i < 8; i++){
            if(!board[x - 97][i].equals("0")) break;
            moves.add(buildCoordString(x, i));
        }//checks down
        for(int i = y; i >= 0; i--){
            if(!board[x - 97][i].equals("0")) break;
            moves.add(buildCoordString(x, i));
        }//checks up
        for(int i = x - 97; i >= 0; i--){
            if(!board[i][y].equals("0")) break;
            moves.add(buildCoordString(i, y));
        }//check left
        for(int i = x - 97; i < 8; i++){
            if(!board[i][y].equals("0")) break;
            moves.add(buildCoordString(i, y));
        }//checks right
        
        return moves;
    };

    @Override
    public String buildCoordString(int x, int y){
        return Character.toString((char) x) + Integer.toString(y);
        
    }




    @Override
    public MoveType isValid(String[][] board, Integer[] coords){


        int x0 = coords[0]; int y0 = coords[1];
        int xf = coords[2]; int yf = coords[3];
        suit suit0 = suit.getSuit(board[x0][y0]);
        suit suitF = suit.getSuit(board[xf][yf]);

        if(!onBoard(xf,yf)){
            return MoveType.NONE;
        }
        if(x0 == xf || y0 == yf){
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