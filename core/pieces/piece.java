package core.pieces;

import core.MoveType;

import java.util.ArrayList;

public interface piece {
    public ArrayList<String> getAvailableMoves(String[][] board, String coord);
    public String buildCoordString(int x, int y);
    public MoveType isValid(String[][] board, Integer[] coords);
    public boolean onBoard(int x, int y);



}
