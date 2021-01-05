package core.engine;

public class Node {
    private Move bestMove;
    private int score;

    public Node(int Score, Move bestMove){
        this.score = Score;
        this.bestMove = bestMove;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Move getBestMove() {
        return bestMove;
    }

    public int getScore(){
        return score;

    }
}
