package core;

import java.util.HashMap;

public class BoardModel {
    String[][] board;
    boolean bTurn = false;
    boolean wTurn = true;
    public static HashMap fMap;
    private Player wPlayer;
    private Player bPlayer;


    static {
        HashMap<Character,String> aMap = new HashMap<>();
        aMap.put('k',"assets/imgs/WHITE/KING_WHITE.png");
        aMap.put('q',"assets/imgs/WHITE/QUEEN_WHITE.png");
        aMap.put('b',"assets/imgs/WHITE/BISHOP_WHITE.png");
        aMap.put('n',"assets/imgs/WHITE/KNIGHT_WHITE.png");
        aMap.put('r',"assets/imgs/WHITE/ROOK_WHITE.png");
        aMap.put('p',"assets/imgs/WHITE/PAWN_WHITE.png");

        aMap.put('K',"assets/imgs/BLACK/KING_BLACK.png");
        aMap.put('Q',"assets/imgs/BLACK/QUEEN_BLACK.png");
        aMap.put('B',"assets/imgs/BLACK/BISHOP_BLACK.png");
        aMap.put('N',"assets/imgs/BLACK/KNIGHT_BLACK.png");
        aMap.put('R',"assets/imgs/BLACK/ROOK_BLACK.png");
        aMap.put('P',"assets/imgs/BLACK/PAWN_BLACK.png");

        fMap = aMap;
    }

    public BoardModel(PlayerType type){
        this.bPlayer = new Player(type);
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

    public suit getCurrentTurn(){
        if(bTurn){
            return suit.BLACK;
        }else{
            return suit.WHITE;
        }

    }
    public void changeTurn(){
        boolean temp = bTurn;
        bTurn = wTurn;
        wTurn = temp;
    }

    public Player getbPlayer() {
        return bPlayer;
    }

    public Player getwPlayer() {
        return wPlayer;
    }

    public MoveType makeMove(){
        if(bPlayer.getType().equals(PlayerType.BOT)){

        }
    }


    public String[][] getBoard(){
        return this.board;
    }
    public void setBoard(int x, int y, String piece){
        board[x][y] = piece;
    }
    public void updateBoard(int x0, int y0, int newX, int newY) {
        String movedPiece = board[x0][y0];
        setBoard(x0,y0, "0");
        setBoard(newX, newY, movedPiece);
    }
    public boolean hasPiece(int x, int y){
        return !board[x][y].equals("0");
    }
}
