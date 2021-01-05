package core.engine;
public enum MoveType {
    CAPTURE('c'), NONCAPTURE('n'),CHECK('x'),NONE('0');
    char moveType;
    MoveType(char moveType){
        this.moveType = moveType;
    }

}
