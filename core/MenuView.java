package core;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

public class MenuView extends AnchorPane {
    private Button newGame;
//    private Button notReady;
    private static final ObservableList<String> playerChoices = FXCollections.observableArrayList(
            "Person",
                "BOT"
    );
    private final ComboBox<String> playerSelector;

    public MenuView(){
        this.newGame = new Button("New Game");
//        this.notReady = new Button("not Ready");
        this.playerSelector = new ComboBox<>(playerChoices);
        playerSelector.setTranslateX(100);
        playerSelector.setLayoutY(100);

        getChildren().addAll(newGame,playerSelector);
    }

    public Button getNewGame() {
        return this.newGame;
    }
    public ComboBox<String> getPlayerSelector(){
        return playerSelector;
    }

}
