import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Piece extends StackPane {
    private PieceType type;

    public PieceType getType() {
        return type;
    }

    public Piece(PieceType type ,int x, int y){
        this.type = type;
        relocate(x * ChessApp.TILE_SIZE,y * ChessApp.TILE_SIZE);
        Image img = new Image(ChessApp.fMap.get(type.pieceClass).toString());
        ImageView iv1 = new ImageView(img);
        iv1.setFitHeight(100);
        iv1.setFitWidth(100);


        getChildren().add(iv1);





    }
}

