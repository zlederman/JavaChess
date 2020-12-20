package pieces;

import java.util.ArrayList;

public class knight implements piece {
    public static int[] steps = {-1, -3, 1, 3};
    public knight() {
    }

    @Override
    public ArrayList<String> getAvailableMoves(String[][] board, String coord) {
        int pair[] = {(int) coord.charAt(0)  - 97, Integer.parseInt(coord.substring(1,2))};  // (x, y)

        ArrayList<String> moves = new ArrayList<String>();
        
        boolean suit = board[pair[0]][pair[1]].charAt(0) <= 90 && board[pair[0]][pair[1]].charAt(0) >= 65 ? true : false;
        // if its upper case then it is true -> white; 
        for(int i  = 0; i < 2; i++){
            for(int j = 0 ; j < 4; j++){

                int t = pair[(i + 1) % 2] + steps[(j + 1) % 4];
                int u = pair[i % 2] + steps[j % 4];
                //alternates between x and y via i and goes through +/- 3 and +/- 1 via j
                if(t  < 8 && u < 8 && t >= 0 && u >= 0){
                    if(getSuit(board[t][u]) != suit){
                        moves.add(buildCoordString(t, u));
                    }//either opposite suit or is an empty space
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
    };

    private boolean getSuit(String piece){
        return piece.charAt(0) <= 90 && piece.charAt(0) >= 65 ? true : false;
    }

    

    

    
}