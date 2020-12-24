package core;

public enum suit {
    BLACK('b'), WHITE('w'), NONE('x');
    char suit;
    suit(char color){
        this.suit = color;
    }

    public static suit getSuit(String pieceID) {
        char piece = pieceID.charAt(0);
        if(piece >= 65 && piece <= 90){
            return BLACK;
        }
        if(piece >= 97 && piece <= 123){
            return WHITE;
        }
        return NONE;

    }
}
