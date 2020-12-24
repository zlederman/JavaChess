package core.pieces;
import core.MoveType;
import core.suit;

import java.util.ArrayList;

public class knight implements piece {
    public static final int COST = 3;
    public knight() {
    }

    @Override
    public ArrayList<String> getAvailableMoves(String[][] board, String coord) {
        int pair[] = {(int) coord.charAt(0)  - 97, Integer.parseInt(coord.substring(1,2))};

        ArrayList<String> moves = new ArrayList<String>();
        int[] steps = {-1, -3, 1, 3};
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




        //up 3 left 1
        //up 3 right 1
        //up 1 right 3
        //up
    }

    @Override
    public String buildCoordString(int x, int y) {
        return Character.toString((char) x) + Integer.toString(y);
    }

    @Override
    public core.MoveType isValid(String board[][], Integer[] coords) {
        int x0 = coords[0]; int y0 = coords[1];
        int xf = coords[2]; int yf = coords[3];
        suit suit0 = suit.getSuit(board[x0][y0]);
        suit suitF = suit.getSuit(board[xf][yf]);

        //either the differnece between x0 - xf == 3 and y0 - yf == 1 || x0 - xf == 1 and y0 - yf == 3
        if(!core.board.onBoard(xf,yf)) return MoveType.NONE;

        if(Math.abs(x0 - xf) == 2 && Math.abs(y0 - yf) == 1){

            return MoveType.eval(suit0,suitF);
        }
        else if(Math.abs(x0 - xf) == 1 && Math.abs(y0 - yf) == 2){
            return MoveType.eval(suit0,suitF);
        }
        return MoveType.NONE;
    };

    private boolean getSuit(String piece){
        return piece.charAt(0) <= 90 && piece.charAt(0) >= 65 ? true : false;
    }






}