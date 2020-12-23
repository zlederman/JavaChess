public class MoveResult {
    private MoveType type;
    private Piece piece;

    private MoveResult(MoveType type, Piece piece){
        this.type = type;
        this.piece = piece;
    }
    public MoveResult(MoveType type){
        this(type,null);
    }

    private Piece getPiece(){
        return piece;
    }

    public MoveType getType() {
        return type;
    }
}
