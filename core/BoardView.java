package core;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;

public class BoardView extends StackPane {
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();
    private Tile board[][] = new Tile[GameView.HEIGHT][GameView.WIDTH];


    public BoardView(){
        setPrefHeight(GameView.HEIGHT* GameView.TILE_SIZE);
        setPrefWidth(GameView.WIDTH* GameView.TILE_SIZE);
        getChildren().addAll(tileGroup,pieceGroup);
    }

    public Group getTileGroup() {
        return tileGroup;
    }

    public Group getPieceGroup() {
        return pieceGroup;
    }
}
