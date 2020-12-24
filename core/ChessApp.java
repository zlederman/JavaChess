package core;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
//captures
//rules
//possible move circles
//points
//bots
//winner
//side panel
//

public class ChessApp extends Application {
    private boolean wTurn = true;
    private boolean bTurn = false;

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

    private Tile board[][] = new Tile[WIDTH][HEIGHT];
    public core.board boardState = new core.board();

    private TableView tableView;

    private Parent createContent(){
        //need to clean up initialization and gui stuff later...
        BorderPane root = new BorderPane();
        StackPane pane = new StackPane();
        pane.setPrefSize(WIDTH*TILE_SIZE,HEIGHT * TILE_SIZE  );
        pane.setPrefSize(WIDTH*TILE_SIZE +250 , HEIGHT * TILE_SIZE );
        pane.autosize();
        pane.getChildren().addAll(tileGroup,pieceGroup);

        HBox hBox = new HBox();
        VBox vBox = new VBox();
        Text coordX[] = new Text[8];
        Text coordY[] = new Text[8];
        for(int i = 0; i < 8; i++){
            coordX[i] = new Text("   "+Character.toString( (char) i + 65));
        }
        for(int i = 0 ; i < 8; i++){
            coordY[i] = new Text("   "+Integer.toString(i));
        }
        hBox.setSpacing(90);

        hBox.getChildren().addAll(coordX);
        hBox.setPrefSize(WIDTH*TILE_SIZE + 300, 40);
        tableView = new TableView();
        TableColumn blackCol = new TableColumn("Black");
        blackCol.setMinWidth(150);
        blackCol.setCellFactory(new PropertyValueFactory<Integer,String>("Move"));
        TableColumn whiteCol = new TableColumn("White");
        whiteCol.setMinWidth(150);
        whiteCol.setCellFactory(new PropertyValueFactory<Integer,String>("Move"));

        tableView.getColumns().addAll(whiteCol,blackCol);

        vBox.setSpacing(90);
        vBox.getChildren().addAll(coordY);
        vBox.autosize();
        hBox.autosize();


        root.setCenter(pane);
        root.setTop(hBox);
        root.setLeft(vBox);
        // to add coordinates i need to create text nodes and relocated them
        root.setRight(tableView);

        for(int i = 0; i < WIDTH; i++){

            for(int j = 0; j < HEIGHT; j++){

                Tile tile = new Tile((i + j) % 2 == 0,j,i);
                tile.autosize();
                tileGroup.getChildren().add(tile);
                board[j][i] = tile;
                PieceView piece = null;
                piece = makePiece(map(i,j),j, i);
                if(piece != null ) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }


            }
        }

        return root;

    }
    public core.MoveType tryMove(PieceView piece, int newX, int newY){
        try{
            if(board[newX][newY].hasPiece() || !getTurn(piece.getType().pieceClass)){
                return  core.MoveType.NONE;
            }
            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());
            System.out.printf("init location: %d , %d \n",x0, y0);
            System.out.printf("final location: %d , %d\n",newX, newY);


            return  boardState.updateBoard(boardState.getBoard(),y0, x0, newY, newX);
        }catch(Exception e){
            return core.MoveType.NONE;
        }

    }


    public int toBoard(double pixel){
        return (int) (pixel + TILE_SIZE/2) / TILE_SIZE;
    }


    private PieceView makePiece(PieceType type, int x, int y){

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
                        System.out.printf("%s\n",boardState.getBoard().toString());
                        p.move(newX, newY);
                        board[x0][y0].setPiece(null);
                        board[newX][newY].setPiece(p);
                        changeTurn(type.pieceClass);
                        break;

                }

            });
            return p;

        }

        return null;

    }
    public boolean getTurn(char pieceClass){
        if(pieceClass >= 65 && pieceClass <= 90){
            return bTurn;
        }else {
            return wTurn;
        }
    }

    public void changeTurn(char pieceClass){
        if(pieceClass >= 65 && pieceClass <= 90){
            bTurn = false;
            wTurn = true;
        }else{
            wTurn = false;
            bTurn = true;
        }
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


    public PieceType map(int color, int j){
        // 0 is black 7 is white
        if(color == 7 || color == 0){
            switch(j){
                case 0:
                case 7:
                    return  color == 0 ? PieceType.bRook : PieceType.wRook;
                case 1:
                case 6:
                    return  color == 0 ? PieceType.bKnight : PieceType.wKnight;
                case 2:
                case 5:
                    return  color == 0 ? PieceType.bBishop : PieceType.wBishop;
                case 3:
                    return  color == 0 ? PieceType.bKing : PieceType.wKing;
                case 4:
                    return  color == 0 ? PieceType.bQueen : PieceType.wQueen;

            }
        }
        else if(color == 1 || color == 6){
            return color == 1 ? PieceType.bPawn  : PieceType.wPawn;
        }
        else{
            return null;
        }
        return null;

    }
}
