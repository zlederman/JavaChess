package core;

import core.pieces.*;

import java.util.Timer;
import java.util.TimerTask;

public class BoardController {
    private BoardView boardView;
    private BoardModel boardModel;
    private Tile tileBoard[][] = new Tile[8][8];



    public BoardController(BoardView boardView, BoardModel boardModel){
        this.boardModel = boardModel;
        this.boardView = boardView;
        buildBoard(boardView);
        startGame();




    }

    public int toBoard(double pixel){
        return (int) (pixel + GameView.TILE_SIZE/2) / GameView.TILE_SIZE;
    }

    private void buildBoard(BoardView board){
        for(int i = 0; i < GameView.WIDTH; i++){
            for(int j = 0; j < GameView.HEIGHT; j++){
                Tile tile = new Tile((i + j) % 2 == 0,j,i);
                tile.autosize();
                tileBoard[j][i] = tile;
                board.getTileGroup().getChildren().add(tile);
                PieceView piece = null;
                piece = assignPieceLogic(assignTiles(i,j),j, i);
                if(piece != null ) {
                    tile.setPiece(piece);
                    boardView.getPieceGroup().getChildren().add(piece);
                }

            }
        }

    }

    private PieceView assignPieceLogic(PieceType type, int x, int y){

        if(type != null){
            PieceView p = new PieceView(type, x, y);
            p.setOnMouseReleased((e)->{
                int newX = toBoard(p.getLayoutX());
                int newY = toBoard(p.getLayoutY());

                int x0 = toBoard(p.getOldX());
                int y0 = toBoard(p.getOldY());

                switch (tryMove(p,newX,newY)){
                    case NONE :
                        p.abort();
                        break;
                    case NORMAL :
                        p.move(newX, newY);
                        tileBoard[x0][y0].setPiece(null);
                        tileBoard[newX][newY].setPiece(p);
                        boardModel.updateBoard(x0,y0,newX, newY);
                        boardModel.changeTurn();

                        break;
                    case CAPTURE:
                        //todo

                }


            });
            return p;

        }

        return null;
    }

    public MoveType tryMove(PieceView piece, int newX, int newY){
        System.out.printf("%s ",boardModel.hasPiece(newX,newY));
        System.out.printf("%s\n",boardModel.getBoard()[newX][newY]);
        System.out.printf("%s",boardModel.getCurrentTurn().toString());
        try{
            if(boardModel.hasPiece(newX,newY) || !boardModel.getCurrentTurn().equals(piece.getSuit())){
                return  core.MoveType.NONE;
            }
            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());
            System.out.printf("init location: %d , %d \n",x0, y0);
            System.out.printf("final location: %d , %d\n",newX, newY);


           return  isValid(y0, x0, newY, newX);
        }catch(Exception e){
            return core.MoveType.NONE;
        }

    }

    public void startGame(){

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(boardModel.bTurn) {

                    boardModel.makeMove();

                }
            }
        }, 0,5*1000);
// Since Java-8

    }


    public MoveType isValid(int x0, int y0, int xf, int yf){
        String boardState[][] = boardModel.getBoard();
        String piece = Character.toString(boardState[x0][y0].charAt(0)).toLowerCase();
        Integer coords[] = {x0, y0, xf, yf};
        core.MoveType isValid = MoveType.NONE;
        System.out.printf("%s",piece);
        switch (piece.charAt(0)) {
            case 'r':
                isValid = new rook().isValid(boardState, coords);
                break;
            case 'q':
                isValid = new queen().isValid(boardState, coords);
                break;
    //            case 'k':
    //                valid  = new king().isValid(coords);
            case 'n':
                isValid = new knight().isValid(boardState, coords);
                break;
            case 'b':
                isValid = new bishop().isValid(boardState, coords);
                break;
            case 'p':
                isValid = new pawn().isValid(boardState, coords);
                break;
            default:
                break;
        }

        return isValid;


    }


    private PieceType assignTiles(int row, int column){
        if(row == 7 || row == 0){
            switch(column){
                case 0:
                case 7:
                    return  row == 0 ? PieceType.bRook : PieceType.wRook;
                case 1:
                case 6:
                    return  row == 0 ? PieceType.bKnight : PieceType.wKnight;
                case 2:
                case 5:
                    return  row == 0 ? PieceType.bBishop : PieceType.wBishop;
                case 3:
                    return  row == 0 ? PieceType.bKing : PieceType.wKing;
                case 4:
                    return  row == 0 ? PieceType.bQueen : PieceType.wQueen;

            }
        }
        else if(row == 1 || row == 6){
            return row == 1 ? PieceType.bPawn  : PieceType.wPawn;
        }

        return null;
    }
}

