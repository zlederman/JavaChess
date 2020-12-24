package core.pieces;

import java.util.ArrayList;

public interface piece {
    public ArrayList<String> getAvailableMoves(String[][] board, String coord);
    public String buildCoordString(int x, int y);
    public  core.MoveType isValid(String[][] board, Integer[] coords);



}
