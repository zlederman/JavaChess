package pieces;

import java.util.ArrayList;

public class king implements piece {

    public king() {
    };

    @Override
    public ArrayList<String> getAvailableMoves(String[][] board, String coord) {
        
    }

    @Override
    public String buildCoordString(int x, int y) {
        return Character.toString((char) (x + 97)) + Integer.toString(y);
    }
    private canCastle(String[][] board, String coord){
        //TODO
    }
    private boolean isMate(String[][] board, String coord){
        
    }


}