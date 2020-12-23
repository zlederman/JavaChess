import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Piece extends StackPane {
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

    public Piece(PieceType type ,int x, int y) {
        this.type = type;
        move(x,y);
        Image img = new Image(ChessApp.fMap.get(type.pieceClass).toString());
        ImageView iv1 = new ImageView(img);
        iv1.setFitHeight(100);
        iv1.setFitWidth(100);


        getChildren().add(iv1);
        setOnMousePressed((e)->{
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
            System.out.printf("mouse location %d, %d\n", toBoard(mouseX), toBoard(mouseY));
        });

        setOnMouseDragged((e) ->  {
            relocate(e.getSceneX() - mouseX + oldX,e.getSceneY() - mouseY + oldY);
        });



    }
    public void move(int x, int y){
        this.oldX = x * ChessApp.TILE_SIZE;
        this.oldY = y * ChessApp.TILE_SIZE;
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
        return (int) (pixel + ChessApp.TILE_SIZE/2) / ChessApp.TILE_SIZE;
    }

}

