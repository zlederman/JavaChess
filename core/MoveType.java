package core;

public enum MoveType {
    NONE(-1), NORMAL(0), CAPTURE(1);
    int moveType;
    MoveType(int i){
        this.moveType = i;
    }
    public static MoveType eval(suit s1, suit s2){
        return switch (s2) {
            case NONE -> MoveType.NORMAL;
            case WHITE -> suit.WHITE.equals(s1) ? MoveType.NONE : MoveType.CAPTURE;
            case BLACK -> suit.BLACK.equals(s1) ? MoveType.NONE : MoveType.CAPTURE;
        };
    }
}
