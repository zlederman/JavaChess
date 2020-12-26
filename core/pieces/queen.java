package core.pieces;

import core.MoveType;
import core.suit;

import java.util.ArrayList;

public class queen implements piece {
    public static final int COST = 9;
    public queen() {
    }



    @Override
    public ArrayList<String> getAvailableMoves(String[][] board, String coord) {
        ArrayList<String> moves = new ArrayList<String>();
        bishop b = new bishop();
        rook r = new rook();
        b.getAvailableMoves(board, coord).forEach((u) -> moves.add(u));
        r.getAvailableMoves(board, coord).forEach((u) -> moves.add(u));
        return moves;

    }

    @Override
    public String buildCoordString(int x, int y) {
        return Character.toString((char) x) + Integer.toString(y);

    }

    @Override
    public core.MoveType isValid(String[][] board, Integer[] coords) {
        int x0 = coords[0]; int y0 = coords[1];
        int xf = coords[2]; int yf = coords[3];
        suit suit0 = suit.getSuit(board[x0][y0]);
        suit suitF = suit.getSuit(board[xf][yf]);

        if(!onBoard(xf,yf)){
            return MoveType.NONE;
        }
        if(Math.abs(x0 - xf) == Math.abs(y0 - yf)){
            return MoveType.eval(suit0,suitF);
        }else if(x0 == xf || yf == y0){
            return MoveType.eval(suit0,suitF);
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