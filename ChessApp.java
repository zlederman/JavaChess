import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.HashMap;


public class ChessApp extends Application {

    public static HashMap fMap;
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



    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();
    private Tile Board[][] = new Tile[WIDTH][HEIGHT];

    private Parent createContent(){
        BorderPane root = new BorderPane();


        root.setPrefSize(WIDTH*TILE_SIZE , HEIGHT * TILE_SIZE );

        root.autosize();
        root.getChildren().addAll(tileGroup,pieceGroup);


        for(int i = 0; i < WIDTH; i++){

            for(int j = 0; j < HEIGHT; j++){

                Tile tile = new Tile((i + j) % 2 == 0,i,j);
                tileGroup.getChildren().add(tile);
                Board[i][j] = tile;
                Piece piece = null, pieceM = null;
                
                if(i == 0){
                   switch(j){
                       case 0:
                           piece = makePiece(PieceType.bRook, j, i);
                           pieceM = makePiece(PieceType.bRook, (WIDTH - 1) - j, i  );
                           break;
                       case 1:
                           piece = makePiece(PieceType.bKnight, j, i);
                           pieceM = makePiece(PieceType.bKnight,(WIDTH - 1) - j ,i );
                           break;
                       case 2:
                           piece = makePiece(PieceType.bBishop, j, i);
                           pieceM = makePiece(PieceType.bBishop, (WIDTH - 1) - j,  i);
                           break;
                       case 3:
                           piece = makePiece(PieceType.bKing, j , i);
                           break;
                       case 4:
                           piece = makePiece(PieceType.bQueen, j,i );
                           break;
                           
                   }
                }
                else if(i == 1){
                    piece = makePiece(PieceType.bPawn, j, i);
                    
                }
                else if(i == 6){
                    piece = makePiece(PieceType.wPawn, j, i);
                }else if(i == 7){
                    switch(j){
                        case 0:
                            piece = makePiece(PieceType.wRook, j, i);
                            pieceM = makePiece(PieceType.wRook, (WIDTH - 1) - j, i  );
                            break;
                        case 1:
                            piece = makePiece(PieceType.wKnight, j, i);
                            pieceM = makePiece(PieceType.wKnight,(WIDTH - 1) - j ,i );
                            break;
                        case 2:
                            piece = makePiece(PieceType.wBishop, j, i);
                            pieceM = makePiece(PieceType.wBishop, (WIDTH - 1) - j,  i);
                            break;
                        case 3:
                            piece = makePiece(PieceType.wKing, j , i);
                            break;
                        case 4:
                            piece = makePiece(PieceType.wQueen, j,i );
                            break;

                    }
                }
                if(piece != null ){
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
                if(pieceM != null){
                    tile.setPiece(pieceM);
                    pieceGroup.getChildren().add(pieceM);
                }


            }
        }

        return root;

    }

    private Piece makePiece(PieceType type, int x, int y){
        Piece p = new Piece(type, x, y);
        return p;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Scene scene = new Scene(createContent());

        primaryStage.setTitle("Chess App");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
