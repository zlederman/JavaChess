package pieces;

import java.util.*;



public class rook implements piece {
    public rook(){}

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
    public boolean isValid(String[][] board, String iCoord, String fCoor) {
        // TODO Auto-generated method stub
        return false;
    }

    
}