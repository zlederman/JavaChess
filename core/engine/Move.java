package  core.engine;
public class Move {
    public char pieceType;
    public MoveType moveType;
    public short dest;
    public short src;

    public Move(char type, MoveType moveType, short dest, short src ){
        this.pieceType = type;
        this.moveType = moveType;
        this.dest = dest;
        this.src = src;
    }



    public static void printMove(Move move){
        System.out.printf("Piece: %c\n ________ \n Move type: %s \n Source: (%d , %d) \n Destination: (%d , %d)",
                move.pieceType,
                move.moveType,
                move.src / 8,move.src % 8,
                move.dest / 8, move.dest % 8);
        System.out.println();

    }

    public int getIndex(){
        return switch (this.pieceType) {
            case 'P', 'p' -> 0;
            case 'R', 'r' -> 1;
            case 'N', 'n' -> 2;
            case 'b', 'B' -> 3;
            case 'Q', 'q' -> 4;
            case 'K', 'k' -> 5;
            default -> 6;
        };
    }

    public int score(){
        int score = 0;
        if((pieceType == 'p' || pieceType == 'P') && moveType.equals(MoveType.CAPTURE)){
            score += 5;
            return score;
        }
        if((pieceType == 'k' || pieceType == 'K') && moveType.equals(MoveType.CAPTURE)){
            score += 4;
            return score;
        }
        if(pieceType == 'q'|| pieceType == 'Q'){
            score += 4;
        }
        if(pieceType == 'r' || pieceType == 'R'){
            score += 3;
        }
        if(pieceType == 'n' || pieceType == 'N'){
            score += 3;
        }
        if(pieceType == 'b' || pieceType == 'B'){
            score += 3;
        }
        if(pieceType == 'X' || pieceType == 'x'){
            return  Integer.MAX_VALUE;
        }
        if(moveType.equals(MoveType.CAPTURE)){
            score += 7;
        }
        if(moveType.equals(MoveType.CHECK)){
            score += 6;
        }

        return score;
    }


}

