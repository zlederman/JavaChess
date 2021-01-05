package core;

import core.engine.Move;
import core.engine.MoveType;
import core.engine.Suit;
import javafx.application.Platform;

public class BoardController {
    private BoardView boardView;
    private BoardModel boardModel;
    private Tile tileBoard[][] = new Tile[8][8];



    public BoardController(BoardView boardView, BoardModel boardModel){
        this.boardModel = boardModel;
        this.boardView = boardView;
        buildBoard(boardView);

    }

    public synchronized void setTileBoard(int row, int col, PieceView p){
        tileBoard[row][col].setPiece(p);
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
                piece = assignPieceLogic(assignTiles(j,i),j, i);
                if(piece != null) {
                    tile.setPiece(piece);
                    boardView.getPieceGroup().getChildren().add(piece);
                }

            }
        }

    }

    public void assignMouseHandler(PieceView p){
        p.setOnMouseReleased((e)->{
            int newX = toBoard(p.getLayoutX());
            int newY = toBoard(p.getLayoutY());

            int x0 = toBoard(p.getOldX());
            int y0 = toBoard(p.getOldY());

            switch (tryMove(p, newX, newY)) {
                case NONE -> p.abort();
                case NONCAPTURE -> {
                    updateView(x0, y0, newX, newY, p);
                    updateModel(x0, y0, newX, newY);
                }

                case CAPTURE -> {
                    updateCapture(newX,newY);
                    updateView(x0, y0, newX, newY, p);
                    updateModel(x0, y0, newX, newY);
                }
            }
        });
    }

    private PieceView assignPieceLogic(PieceType type, int row, int col){
        if(type != null){
            PieceView p = new PieceView(type, col, row);
            assignMouseHandler(p);
            return p;
        }

        return null;
    }

    public synchronized void updateView(int x0, int y0, int newX, int newY, PieceView p){
        try{
            p.move(newX,newY);
            tileBoard[y0][x0].setPiece(null);
            tileBoard[newY][newX].setPiece(p);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public synchronized void updateModel(int x0, int y0, int newX, int newY){
        boardModel.updateBoard(x0,y0,newX, newY);
        boardModel.changeTurn();
    }

    public MoveType tryMove(PieceView piece, int newX, int newY){

        try{
            if(boardModel.hasPiece(newX,newY) || !boardModel.getCurrentTurn().equals(piece.getSuit())){
                return  MoveType.NONE;
            }
            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());

           return  getMoveType(x0, y0, newX, newY);
        }catch(Exception e){
            e.printStackTrace();
            return MoveType.NONE;
        }

    }

    public void updateCapture(int x,int y){
        try{

            boardView.getPieceGroup().getChildren().remove(tileBoard[y][x].getPiece());
            tileBoard[x][y].setPiece(null);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

public void startGame(){

        Thread t1  = new Thread(() -> {

            while(true){
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }

                if(boardModel.getCurrentTurn().equals(Suit.BLACK)){
                    Move botMove =  boardModel.makeMove();
                    int x0 = botMove.src  % 8;
                    int y0 = botMove.src / 8;
                    int yf = botMove.dest / 8;
                    int xf = botMove.dest % 8;
                    Platform.runLater(()->{

                        if(botMove.moveType.equals(MoveType.CAPTURE)){
                            updateCapture(xf,yf);
                        }
                        updateView(x0, y0, xf, yf,tileBoard[y0][x0].getPiece());


                    });
                    updateModel(x0, y0, xf, yf);


                }


            }

        });
    t1.start();

}
    public void printTileBoard(){
        for(int i = 0; i < 8; i++){
            System.out.printf("ROW %d: ",i);
            for(int j = 0 ; j < 8; j ++){
                if(tileBoard[i][j].hasPiece()){
                    System.out.printf(" %s ",tileBoard[i][j].getPiece().getType().toString());
                }
                else{
                    System.out.print("   .   ");
                }
            }
            System.out.println();
        }
    }
    public MoveType getMoveType(int x0, int y0, int xf, int yf){
        return boardModel.validateMove(x0, y0, xf, yf);
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

