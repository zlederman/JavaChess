package pieces;

import java.util.ArrayList;

public class bishop implements piece {
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
    public boolean isValid(String[][] board, String iCoord, String fCoord) {
        // TODO Auto-generated method stub
        return false;
    }

}