package core;

import core.engine.Engine;
import core.engine.Move;
import core.engine.MoveType;
import core.engine.Suit;

import java.util.Arrays;
import java.util.HashMap;

public class BoardModel {
    String[][] board;
    boolean bTurn = false;
    boolean wTurn = true;
    public static HashMap fMap;
    private Engine engine;



    static {
        HashMap<Character, String> aMap = new HashMap<>();
        aMap.put('k', "assets/imgs/WHITE/KING_WHITE.png");
        aMap.put('q', "assets/imgs/WHITE/QUEEN_WHITE.png");
        aMap.put('b', "assets/imgs/WHITE/BISHOP_WHITE.png");
        aMap.put('n', "assets/imgs/WHITE/KNIGHT_WHITE.png");
        aMap.put('r', "assets/imgs/WHITE/ROOK_WHITE.png");
        aMap.put('p', "assets/imgs/WHITE/PAWN_WHITE.png");

        aMap.put('K', "assets/imgs/BLACK/KING_BLACK.png");
        aMap.put('Q', "assets/imgs/BLACK/QUEEN_BLACK.png");
        aMap.put('B', "assets/imgs/BLACK/BISHOP_BLACK.png");
        aMap.put('N', "assets/imgs/BLACK/KNIGHT_BLACK.png");
        aMap.put('R', "assets/imgs/BLACK/ROOK_BLACK.png");
        aMap.put('P', "assets/imgs/BLACK/PAWN_BLACK.png");

        fMap = aMap;
    }

    public BoardModel(PlayerType type) {

        this.board = new String[][]{

                {"R", "N", "B", "K", "Q", "B", "N", "R"},//1
                {"P", "P", "P", "P", "P", "P", "P", "P"},
                {"0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0"},
                {"p", "p", "p", "p", "p", "p", "p", "p"},
                {"r", "n", "b", "k", "q", "b", "n", "r"} //8
        };
        this.engine = new Engine(board);

    }

    public synchronized Suit getCurrentTurn() {
        if (bTurn) {
            return Suit.BLACK;
        } else {
            return Suit.WHITE;
        }

    }

    public synchronized void changeTurn() {
        boolean temp = bTurn;
        bTurn = wTurn;
        wTurn = temp;
    }
    public synchronized void printBoard(){
        for(int i = 0;  i< 8; i++){
            System.out.println(Arrays.toString(board[i]));
        }
    }


    public synchronized Move makeMove(){
        int depth = 4;
        Move[] bestMoves = new Move[depth + 1];
        engine.search(Integer.MIN_VALUE,Integer.MAX_VALUE, depth,true,bestMoves);


        return bestMoves[depth];
    }

    public synchronized MoveType validateMove(int x0, int y0, int newX, int newY){
        Suit suit = board[y0][x0].charAt(0) < 90 ? Suit.BLACK : Suit.WHITE;
        int startIndex = y0 * 8 + x0;
        int endIndex  = newY * 8 + newX;
        char piece = board[y0][x0].charAt(0);
        long moves = 0L;
        switch(piece){
            case 'p':
                moves = engine.getWPawnMoves(startIndex);
                break;
            case 'P':
                moves = engine.getBPawnMoves(startIndex);
                break;
            case 'r':
            case 'R':
                moves = engine.getRookAttacks(startIndex, suit);
                break;
            case 'k':
            case 'K':
                moves = engine.getKingMask(startIndex);
                break;
            case 'n':
            case 'N':
                moves = engine.getKnightAttacks(startIndex, suit);
                break;
            case 'b':
            case 'B':
                moves = engine.getBishopAttacks(startIndex, suit);
                break;
            case 'Q':
            case 'q':
                moves = engine.getQueenAttacks(startIndex, suit);
                break;
        }

        if(engine.isValid(moves, endIndex)){
            return engine.getType(moves & 1L << endIndex, suit);
        }
        return MoveType.NONE;

    }

    public synchronized String[][] getBoard() {
        return this.board;
    }

    public synchronized void setBoard(int x, int y, String piece) {
        this.board[y][x] = piece;
    }

    public synchronized void updateBoard(int x0, int y0, int newX, int newY) {
        String movedPiece = board[y0][x0];
        setBoard(x0, y0, "0");
        setBoard(newX, newY, movedPiece);
        engine.resetBoards();
        engine.initBoards(board);


    }

    public synchronized boolean hasPiece(int x, int y) {
        return !board[y][x].equals("0") && Suit.getSuit(board[y][x]).equals(getCurrentTurn());
    }




}
