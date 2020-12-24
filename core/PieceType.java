package core;

public enum PieceType {
  wKing('k'),wQueen('q'), wBishop( 'b'),

    wKnight('n'), wRook( 'r'), wPawn( 'p'),

    bKing('K'),bQueen('Q'), bBishop('B'),

    bKnight('N'), bRook('R'), bPawn('P');


   char pieceClass;





  PieceType(char pieceClass) {

        this.pieceClass = pieceClass;
    }

}
