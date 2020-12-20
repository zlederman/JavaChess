package pieces;

import java.util.ArrayList;

public class queen implements piece {
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
    public boolean isValid(String[][] board, String iCoord, String fCoor) {
        // TODO Auto-generated method stub
        return false;
    };

    

}