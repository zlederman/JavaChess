package core;


public class MenuController {
    private MenuView menuView;
    private BoardView boardView;
    private PlayerType playerType = PlayerType.PLAYER;


    public MenuController(MenuView menuView){
        this.menuView = menuView;

        menuView.getNewGame().setOnMouseClicked((e)->{
            createNewGame();
        });
        menuView.getPlayerSelector().setOnAction(actionEvent -> {
            String playerChoice = menuView.getPlayerSelector().getValue();
            System.out.printf("%s",playerChoice);
            setPlayerType(playerChoice);
        });

    }

    public void createNewGame(){
       GameView game = new GameView();
       BoardModel boardModel = new BoardModel(playerType);

       BoardController boardController = new BoardController(game.getBoardView(),boardModel);
       menuView.getScene().setRoot(game);
    }

    public void setPlayerType(String playerChoice) {
        if(playerChoice.equals("Person")) playerType = PlayerType.PLAYER;
        if(playerChoice.equals("BOT")) playerType = PlayerType.BOT;


    }
}
