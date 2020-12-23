
public class board {
    String board[][];
    public board(){
        this.board = new String[][]{
                  //a       ...                     //h
                 {"R0", "H0","B0","K","Q","B1","H1","R1"},//1
                 {"0", "0","0","0","0","0","0","0"},
                 {"0","0","0","0","0","0","0","0"},
                 {"0","0","0","0","0","0","0","0"},
                 {"0","0","0","0","0","0","0","0"},
                 {"0","0","0","0","0","0","0","0"},
                 {"0","0","0","0","0","0","0","0"},
                 {"r0","h0","b0","k","q","b1","h1","r1"} //8
             };
    }

    public String[][] getBoard() {
        return board;
    }

    public static void main(String[] args) {
//     player bPlayer = new player(Color.BLACK, "Zach");
//     player wPlayer = new player(Color.WHITE, "Kim Jung");
     //how does the player object interact with the board object
    }

}

