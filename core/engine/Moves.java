package core.engine;
public class Moves {
    private Move[] moves = new Move[50];
    private int size = 0;

    public Moves(){

    }
    public void insert(Move[] move){
        if(move == null){
            return;
        }
        if(this.size + move.length > moves.length){
            resize();
        }

        for (Move value : move) {

            moves[this.size] = value;
            this.size++;
        }

    }
    public void insertSingle(Move move){
        if(this.size + 1 > moves.length){
            resize();
        }

        moves[size] = move;
        this.size++;
    }
    public void resize(){
        Move[] temp = new Move[moves.length * 4];
        for(int i = 0; i < size; i++){
            temp[i] = moves[i];
        }
        this.moves = temp;
    }



    public int getSize(){
        return this.size;
    }

    public Move getMove(int i){
        return moves[i];
    }

    public static void printMoves(Moves moves){
        int numBish = 0;
        int numKing = 0;
        int numPawn = 0;
        int numKnight = 0;
        int numRook = 0;
        int numQueen = 0;
        for(int i =0; i < moves.getSize();i ++){
            if(moves.getMove(i).pieceType == 'p' || moves.getMove(i).pieceType == 'P'){
                numPawn++;
            }
            if(moves.getMove(i).pieceType == 'k' || moves.getMove(i).pieceType == 'K'){
                numKing++;
            }
            if(moves.getMove(i).pieceType == 'r' || moves.getMove(i).pieceType == 'R'){
                numRook++;
            }
            if(moves.getMove(i).pieceType == 'n' || moves.getMove(i).pieceType == 'N'){
                numKnight++;
            }
            if(moves.getMove(i).pieceType == 'b' || moves.getMove(i).pieceType == 'B'){
                numBish++;
            }
            if(moves.getMove(i).pieceType == 'q' || moves.getMove(i).pieceType == 'Q'){
                numBish++;
            }
            System.out.printf("""
                    Total Number of Moves: %d\s
                    ------------------
                     Bishop Moves: %d
                    Pawn Moves: %d
                     King Moves %d
                     Queen Moves: %d
                     Rook Moves: %d
                     Knight Moves: %d
                    """,moves.getSize(),numBish,numPawn,numKing,numQueen,numRook,numKnight);
        }
    }







}
