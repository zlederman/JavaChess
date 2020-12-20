package pieces;

import java.util.ArrayList;

public class pawn implements piece {
    public pawn() {
    }
    // TODO write en passant 
    @Override
    public ArrayList<String> getAvailableMoves(String[][] board, String coord) {
        int x = (int) coord.charAt(0) - 97;
        int y = Integer.parseInt(coord.substring(1,2));
        int suit = board[x][y].charAt(0) <= 90 && board[x][y].charAt(0) >= 65 ? 1 : -1;
        int steps[] = {-1, 1};

        ArrayList<String> moves = new ArrayList<String>();

        // a pawn can move two spaces if it is in the 2nd or 7th row. 
        //a pawn can move diagonal if there is a piece of opposing suit to capture
        //a pawn can be promoted to Queen if it gets to the other side.
        if(y < 8 && y >= 0){
            if(board[x][y + suit*1].equals("0")){
                moves.add(buildCoordString(x, y+suit*1));
            }
            if(board[x][y + suit*2].equals("0") && y == 2 || y == 6){
                moves.add(buildCoordString(x, y+suit*2));
            }
            
            if(x < 7  && x >= 1){
                if(!board[x + steps[0]][y + suit*1].equals("0") && getSuit(board[x + steps[0]][y + suit*1]) != suit){
                    moves.add(buildCoordString(x + steps[0], y+suit*1));
                }
                if(!board[x + steps[1]][y + suit*1].equals("0") && getSuit(board[x + steps[1]][y + suit*1]) != suit){
                    moves.add(buildCoordString(x + steps[1], y + suit*1));
                }
            }    
            
        }

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
    }
    private int getSuit(String piece){
        return piece.charAt(0) <= 90 && piece.charAt(0) >= 65 ? 1 : -1;
    }

  
}
