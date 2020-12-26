package core;

import javafx.scene.layout.BorderPane;

public class GameView extends BorderPane {
    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    private BoardView boardView;


    public GameView(){
        setPrefHeight(800);
        setPrefWidth(800);
        this.boardView = new BoardView();
        setCenter(boardView);



    }
    public BoardView getBoardView(){
        return boardView;
    }

}
