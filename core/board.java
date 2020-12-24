package core;

import core.pieces.*;

public class board {
    public String board[][];

    public board() {
        this.board = new String[][]{
                //a       ...                     //h
                {"R0", "n0", "B0", "K", "Q", "B1", "n1", "R1"},//1
                {"P", "P", "P", "P", "P", "P", "P", "P"},
                {"0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0"},
                {"p", "p", "p", "p", "p", "p", "p", "p"},
                {"r0", "n0", "b0", "k", "q", "b1", "n1", "r1"} //8
        };
    }

    public static boolean onBoard(int x, int y) {
        if (x < 0 || x >= 8) {
            return false;
        }
        return y >= 0 && y < 8;
    }

    public String[][] getBoard() {
        return board;
    }

    public core.MoveType updateBoard(String[][] board, int x0, int y0, int xf, int yf) {
        String piece = Character.toString(this.board[x0][y0].charAt(0)).toLowerCase();
        Integer coords[] = {x0, y0, xf, yf};
        core.MoveType isValid = MoveType.NONE;
        System.out.printf("%s",piece);
        switch (piece.charAt(0)) {
            case 'r':
                isValid = new rook().isValid(board, coords);
                break;
            case 'q':
                isValid = new queen().isValid(board, coords);
                break;
//            case 'k':
//                valid  = new king().isValid(coords);
            case 'n':
                isValid = new knight().isValid(board, coords);
                break;
            case 'b':
                isValid = new bishop().isValid(board, coords);
                break;
            case 'p':
                isValid = new pawn().isValid(board, coords);
                break;
            default:
                break;
        }

        if (isValid != MoveType.NONE) {
            //attacking
            String temp = this.board[x0][y0];
            this.board[x0][y0] = "0";
            this.board[xf][yf] = temp;


        }

        return isValid;


    }
}

