package core;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
    private PieceView piece;

    public boolean hasPiece(){
        return piece != null;

    }
    public PieceView getPiece(){
        return piece;

    }
    public void setPiece(PieceView p){
        this.piece = p;
    }




    public Tile(boolean light, int x, int y){
        setWidth(ChessApp.TILE_SIZE);
        setHeight(ChessApp.TILE_SIZE);

        relocate(x * ChessApp.TILE_SIZE, y * ChessApp.TILE_SIZE);
        setFill(light ? Color.valueOf("d7ceb2") : Color.valueOf("4c5760"));
    }

}
