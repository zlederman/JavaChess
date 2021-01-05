package core.engine;
public enum Suit {
    BLACK('b'), WHITE('w'), NONE('x');
    char suit;
    Suit(char color){
        this.suit = color;
    }

    public static Suit getSuit(String pieceID) {
        char piece = pieceID.charAt(0);
        if(piece >= 65 && piece <= 90){
            return BLACK;
        }
        if(piece >= 97 && piece <= 123){
            return WHITE;
        }
        return NONE;

    }
    public char getCase(char piece, Suit suit){
        if(suit.equals(BLACK)){
            return (char) ( piece - 32);
        }
        return piece;
    }
}
