package core;

public class Player {
    private int score = 0;
    private PlayerType type;

    public Player(PlayerType type){
        this.type = type;
    }

   public PlayerType getType(){
        return type;
   }

}
