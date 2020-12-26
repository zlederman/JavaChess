package core;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class PieceView extends StackPane {
    private PieceType type;
    private double mouseX; private double mouseY;
    private double oldX; private double oldY;
    public PieceType getType() {
        return type;
    }

    public double getOldX(){
        return oldX;
    }
    public double getOldY(){
        return oldY;
    }

    public PieceView(PieceType type , int x, int y) {
        this.type = type;
        move(x,y);
        Image img = new Image(BoardModel.fMap.get(type.pieceClass).toString());
        ImageView iv1 = new ImageView(img);
        iv1.setFitHeight(100);
        iv1.setFitWidth(100);


        getChildren().add(iv1);
        setOnMousePressed((e)->{
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();

        });

        setOnMouseDragged((e) ->  {
            relocate(e.getSceneX() - mouseX + oldX,e.getSceneY() - mouseY + oldY);
        });



    }
    public void move(int x, int y){
        this.oldX = x * GameView.TILE_SIZE;
        this.oldY = y * GameView.TILE_SIZE;
        relocate(this.oldX, this.oldY);
    }
    public void abort(){
        relocate(this.oldX, this.oldY);
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public int toBoard(double pixel){
        return (int) (pixel + GameView.TILE_SIZE/2) / GameView.TILE_SIZE;
    }

    public suit getSuit(){
        return suit.getSuit(Character.toString(type.pieceClass));
    }


}

