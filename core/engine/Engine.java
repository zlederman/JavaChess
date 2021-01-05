package core.engine;


import java.util.Arrays;

public class Engine {


     static long[] bPieces = {
        0L, 0L, 0L, 0L, 0L, 0L, 0L
    };

     static long[] wPieces = {
       0L, 0L, 0L, 0L, 0L, 0L, 0L
    };

    static  long aFile = 72340172838076673L;
     static long bFile = 144680345676153346L;

    static  long hFile = 4629771061636907072L;
    static long gFile = -9187201950435737472L;

     static long rank0 = 255L;
     static long rank1 = 65280L;

    static  long rank7 = -72057594037927936L;
     static long rank6 = 71776119061217280L;

     static long[] knightAttackSet = {
            132097L, 329730L, 659716L, 1319432L,
            2638864L, 5277728L, 10489920L, 4202624L,
            33816836L, 84410888L, 168887313L, 337774626L,
            675549252L, 1351098504L, 2685419536L, 1075871776L,
            8657110018L, 21609187333L, 43235152138L, 86470304276L,
            172940608552L, 345881217104L, 687467401376L, 275423174720L,
            2216220164608L, 5531951957248L, 11068198947328L, 22136397894656L,
            44272795789312L, 88545591578624L, 175991654752256L, 70508332728320L,
            567352362139648L, 1416179701055488L, 2833458930515968L, 5666917861031936L,
            11333835722063872L, 22667671444127744L, 45053863616577536L, 18050133178449920L,
            145242204707749888L, 362542003470204928L, 725365486212087808L, 1450730972424175616L,
            2901461944848351232L, 5802923889696702464L, -6912954987865702400L, 4620834093683179520L,
            288516257764868096L, 577032519824703488L, 1226123733198962688L, 2452247466397925376L,
            4904494932795850752L, -8637754208117850112L, 1170954182497140736L, 2341907265482653696L,
            73185692968026112L, 146372485447680000L, 293026445872070656L, 586052891744141312L,
            1172105783488282624L, 2344211566976565248L, 4616365539915202560L, -9214294468855857152L
    };

     int bMoves = 0;
     int wMoves = 0;


     String board[][];
    public Engine(String[][] board){
        this.board = board;
        for(int i  = 0; i < 8; i++){
            System.out.println(Arrays.toString(board[i]));
        }
        initBoards(board);
    }



    /*
    Move Mask Generation
    _____________________________________________________________________
    Knight Masks:
        returns a long with at most 8 set bits
        noNoWest +17, noNoEast + 15, weWeNorth +6, weWeSouth -10
        eaEaNorth +10, eaEaSouth-6, soSoEast-15, soSoWest -17
    King Masks:
        returns a long with at most 8 set bits
        No + 8, So -8 , W +1 , E -1, NW +9, NE +7
        SE -9, SW -7

    Sliding Piece Masks:
        1. File -> getVMask(int i)
            set bits in the given column excludes the piece bit
        2. Rank -> getHMask(int i)
            set bits in the given row excludes piece bit
        3. Diagonal -> getDMask(int i)
            set bits in a positive slope excludes piece bit
        4. AntiDiagonal -> getAntiDMask(int i)
            set bits in a negative slope excludes piece bit

     */

    public  long getKnightMask(int i) {
        long knight = 1L << i;
        knight |= (((1L << i) & ~(rank7 | rank6)) & ~gFile) << 17;
        knight |= (((1L << i) & ~(rank7 | rank6)) & ~aFile) << 15;

        knight |= (((1L << i) & ~(aFile | bFile)) & ~(rank7)) << 6;
        knight |= (((1L << i) & ~(gFile | hFile)) & ~(rank7)) << 10;

        knight |= (((1L << i) & ~(aFile | bFile)) & ~(rank0)) >>> 10;
        knight |= (((1L << i) & ~(gFile | hFile)) & ~(rank0)) >>> 6;

        knight |= (((1L << i) & ~(rank0 | rank1)) & ~(aFile)) >>> 17;
        knight |= (((1L << i) & ~(rank0 | rank1)) & ~(gFile)) >>> 15;

        return knight & ~(1L << i);


    }

    public  long getHMask(int i) {

        return (255L << ((i / 8) * 8)) ;
    }

    public  long getAntiDMask(int i) {
        int rank = i / 8;
        int file = i % 8;
        long mask = 0L;
        while (rank >= 0 & file < 8) {
            mask |= 1L << (rank * 8 + file);
            rank--;
            file++;
        }
        rank = i / 8;
        file = i % 8;

        while (rank < 8 & file >= 0) {
            mask |= 1L << (rank * 8 + file);
            rank++;
            file--;
        }
        return mask & ~(1L << i);

    }

    public  long getDMask(int i) {
        int rank = i / 8;
        int file = i % 8;
        long mask = 0L;

        while (rank < 8 & file < 8) {
            mask |= 1L << (rank * 8 + file);
            rank++;
            file++;
        }
        rank = i / 8;
        file = i % 8;

        while (rank >= 0 & file >= 0) {
            mask |= 1L << (rank * 8 + file);
            rank--;
            file--;
        }

        return mask & ~(1L << i);

    }

    public  long getVMask(int i) {
        long vMask = 1L << (i % 8);
        int count = 0;
        while (count < 8) {
            vMask <<= 8;
            vMask |= 1L << (i % 8);
            count++;
        }
        vMask |= 1L << (i % 8);
        vMask &= ~(1L << i);
        return vMask;

    }

    public  long getKingMask(int i){
        long mask = 0L;
        mask |= ((1L << i) & ~(aFile | rank7 )) << 7;
        mask |= ((1L << i) & ~(gFile | rank0 )) >>> 7;

        mask |= ((1L << i) & ~(gFile | rank7 )) << 9;
        mask |= ((1L << i) & ~(aFile | rank0)) >>> 9;

        mask |= ((1L << i ) & ~(aFile)) >>> 1;
        mask |= ((1L << i ) & ~(gFile)) << 1;

        mask |= ((1L << i ) & ~(rank7)) << 8;
        mask |= ((1L << i ) & ~(rank0)) >>>  8;
        return mask;
    }


    public  long getWPawnMoves(int ind) {
        long moves = 0L;
        moves |= ((((1L << ind & ~rank7)
                >> 8)
                & ~bPieces[6])
                & ~wPieces[6]);

        moves |= (((((((rank6 & 1L << ind)
                >> 8)
                & ~(bPieces[6] | wPieces[6]))
                >> 8)
                & ~bPieces[6])
                & ~wPieces[6]));

        moves |= ((((1L << ind & ~aFile) >>> 9) & bPieces[6]) & ~wPieces[6]);
        moves |= ((((1L << ind & ~hFile) >>> 7) & bPieces[6]) & ~wPieces[6]);

        return moves;


    }


    public  long getBPawnMoves(int ind) {

        long moves = 0L;
        moves |= (((((1L << ind) & ~rank7)
                << 8)
                & ~bPieces[6])
                & ~wPieces[6]);

        moves |= (((((((rank1 & 1L << ind)
                << 8)
                & ~( bPieces[6]| wPieces[6]))
                << 8)
                & ~bPieces[6])
                & ~wPieces[6]));

        moves |= (1L << ind & ~aFile) << 9 & ~bPieces[6] & wPieces[6];
        moves |= (1L << ind & ~hFile) << 7 & ~bPieces[6] & wPieces[6];

        return moves;


    }




    /*
    Move generation for given pieces
    _____________________________________________________________________
    1. Used Setwise operations for non-sliding pieces e.g. Knight, Pawn and King
    2. Knight's masks are precomputed 4.096kB
    3. Use Obstruction Difference



     */

    public  long getQueenAttacks(int i, Suit suit) {
        long myBoard = suit.equals(Suit.BLACK) ? bPieces[6] : wPieces[6];
        return (slidingAttacks(bPieces[6] | wPieces[6], i, 0) |
                slidingAttacks(bPieces[6] | wPieces[6], i, 1) |
                slidingAttacks(bPieces[6] | wPieces[6], i, 2) |
                slidingAttacks(bPieces[6] | wPieces[6], i, 3))  & ~myBoard;

    }

    public  long getBishopAttacks(int i, Suit suit) {
        long myBoard = suit.equals(Suit.BLACK) ? bPieces[6] : wPieces[6];
        return (slidingAttacks(bPieces[6] | wPieces[6], i, 2) |
                slidingAttacks(bPieces[6] | wPieces[6], i, 3))& ~myBoard ;

    }

    public  long getRookAttacks(int i,Suit suit) {
        long myBoard = suit.equals(Suit.BLACK) ? bPieces[6] : wPieces[6];
        return (slidingAttacks(bPieces[6] | wPieces[6], i, 0) |
                slidingAttacks(bPieces[6] | wPieces[6], i, 1)) & ~myBoard ;
    }

    public  long getKnightAttacks(int i, Suit suit) {
        return knightAttackSet[i] & (suit.equals(Suit.BLACK) ? ~bPieces[6] : ~wPieces[6]);
    }




    public  long slidingAttacks(long occ, int i, int type) {
        long lower = 0L;
        long upper = 0L;
        long mask = 0L;
        switch (type) {

            case 0:
                mask = getVMask(i);
                lower = mask & ((1L << i) - 1);
                upper = mask & -(1L << i);

                break;
            case 1:
                mask = getHMask(i);
                lower = mask & ((1L << i) - 1);

                upper = mask & -(1L << i) & ~(1L << i);

                break;
            case 2:
                mask = getDMask(i);
                lower = mask & ((1L << i) - 1);

                upper = mask & -(1L << i) & ~(1L << i);

                break;
            case 3:
                mask = getAntiDMask(i);
                lower = mask & ((1L << i) - 1);
                upper = mask & -(1L << i);
                break;

        }


        upper &= occ;
        lower &= occ; // returns the occupancy of the lower and upper masks

        long mS1B = 0x8000000000000000L >>> Long.numberOfLeadingZeros(lower | 1); //gets the most significant 1 bit
        long lS1B = upper & -upper; //gets the least significant of the upper mask
        long odiff = 2 * lS1B - mS1B; //fills the board in with all bits between mS1B & lS1b

        return odiff & (mask); // returns the spaces you can move


    }

 /*
    Move Storage
    _____________________________________________________________________
    Each piece initializes a board that has all their possible
    moves given the state of the game.

    After the initialize step, get<piece>Moves method invokes the getMoves method and loads all the
    object data into the move array.


  */
    public   Move[] getMoves(Suit suit, int i, Move[] moveArr, long moves, char type){
        int cnt = 0;
        long attakBoard = suit.equals(Suit.BLACK) ? wPieces[6] : bPieces[6];

        while (moves != 0L && cnt < moveArr.length) {
            MoveType moveType = MoveType.NONCAPTURE;

            if (((moves & -moves) & attakBoard) != 0L) {
                moveType = MoveType.CAPTURE;
            }
            moveArr[cnt] = new Move(type,
                    moveType,
                    (short) log2((moves & -moves)),
                    (short) i);
            moves = moves & (moves - 1);
            cnt++;
        }
        return moveArr;
    }

    public  Move[] getPawnMoves(Suit suit, int i) {

        char type = suit.getCase('p', suit);
        long moves = switch (suit) {
            case BLACK -> getBPawnMoves(i);
            case WHITE -> getWPawnMoves(i);
            default -> 0L;
        };
        int len = popCount(moves);
        Move[] moveArr = new Move[len];

        return getMoves(suit, i, moveArr, moves, type);
    }

    public  Move[] getKnightMoves(Suit suit, int i) {
        char type = suit.getCase('n', suit);
        long moves = getKnightAttacks(i, suit);
        Move[] moveArr = new Move[popCount(moves)];
        return getMoves(suit, i, moveArr, moves, type);

    }

    public  Move[] getRookMoves(Suit suit, int i){
        char type = suit.getCase('r',suit);
        long moves = getRookAttacks(i, suit);

        Move[] moveArr = new Move[popCount(moves)];
        return getMoves(suit, i, moveArr, moves, type);
    }

    public  Move[] getBishopMoves(Suit suit, int i){
        char type = suit.getCase('b',suit);
        long moves = getBishopAttacks(i, suit);
        Move[] moveArr = new Move[popCount(moves)];
        return getMoves(suit, i, moveArr, moves, type);
    }

    public  Move[] getQueenMoves(Suit suit, int i){
        char type = suit.getCase('q',suit);
        long moves = getQueenAttacks(i, suit);
        Move[] moveArr = new Move[popCount(moves)];
        return getMoves(suit, i, moveArr, moves, type);
    }

    public  Move[] getKingMoves(Suit suit, int i){
        Move[] moves = new Move[1];
        long kingAttacks = getKingMask(i);
        if(suit.equals(Suit.BLACK)){
            kingAttacks &= ~bPieces[6];

        }else{
            kingAttacks &= ~wPieces[6];
        }
        if(kingAttacks == 0L){
            return null;
        }
        return getMoves(suit,i,moves,kingAttacks,suit.getCase('k',suit));

    }

    public  Moves aggregateMoves(Suit suit){
        long[] pieces;
        Moves moves = new Moves();

        if(suit.equals(Suit.BLACK)){
            pieces = bPieces.clone();
        }else{
          pieces = wPieces.clone();
        }
        for(int i = 0;  i < 6; i++){

            switch(i){
                case 0:
                    while(pieces[i] != 0L){
                        long x = pieces[i] & -pieces[i];
                        moves.insert(getPawnMoves(suit, log2(x)));
                        pieces[i] &= (pieces[i] - 1);
                    }
                    break;
                case 1:
                    while(pieces[i] != 0L){
                        long x = pieces[i] & -pieces[i];
                        moves.insert(getRookMoves(suit, log2(x)));
                        pieces[i] &= (pieces[i] - 1);
                    }
                    break;
                case 2:
                    while(pieces[i] != 0L){
                        long x = pieces[i] & -pieces[i];
                        moves.insert(getKnightMoves(suit, log2(x)));
                        pieces[i] &= (pieces[i] - 1);
                    }
                    break;
                case 3:
                    while(pieces[i] != 0L){
                        long x = pieces[i] & -pieces[i];
                        moves.insert(getBishopMoves(suit, log2(x)));
                        pieces[i] &= (pieces[i] - 1);
                    }
                    break;
                case 4:
                    while(pieces[i] != 0L){
                        long x = pieces[i] & -pieces[i];
                        moves.insert(getQueenMoves(suit, log2(x)));
                        pieces[i] &= (pieces[i] - 1);
                    }
                    break;
                case 5:
                while(pieces[i] != 0L){
                    long x = pieces[i] & -pieces[i];
                    moves.insert(getKingMoves(suit, log2(x)));
                    pieces[i] &= (pieces[i] - 1);
                }
                break;
            }

        }
        switch(suit){
            case BLACK:
                bMoves = moves.getSize();
                break;
            case WHITE:
                wMoves = moves.getSize();
                break;
        }
        return moves;
    }






  /*
    Search, Evaluation, Make & unMake & perft
    ______________________________________________________________________________________

  */

    public  int eval(Suit suit){
        int pieceDiffs = 200 * (popCount(bPieces[5]) - popCount(wPieces[5]))
                        + 9 * (popCount(bPieces[4]) - popCount(wPieces[4]))
                        + 5 * (popCount(bPieces[1]) - popCount(wPieces[1]))
                        + 3 * ((popCount(bPieces[2]) - popCount(wPieces[2])) -
                                (popCount(bPieces[3]) - popCount(wPieces[3])))

                        + (popCount(bPieces[0]) - popCount(wPieces[0]));

        int freedom = 10 * (bMoves - wMoves);
        int minimax = suit.equals(Suit.BLACK) ? 10 : -10;
        return  (pieceDiffs + freedom) * minimax;
    } // I want to add how many moves there are, pawn structure and some other stuff


    public  int make(Move move, Suit suit){
        if(suit.equals(Suit.BLACK)){
            long toFro = (1L << move.src | 1L << move.dest);
            Engine.bPieces[move.getIndex()] ^= toFro;
            Engine.bPieces[6] ^= toFro;


            if(move.moveType.equals(MoveType.CAPTURE)){
                int index = findBoard(move.dest, Suit.WHITE);
                Engine.wPieces[index] ^= (1L << move.dest);
                Engine.wPieces[6] ^= (1L << move.dest);

                return index;
            }
        }else{
            long toFro = (1L << move.src | 1L << move.dest);
            Engine.wPieces[move.getIndex()] ^= toFro;
            Engine.wPieces[6] ^= toFro;


            if(move.moveType.equals(MoveType.CAPTURE)){
                int index = findBoard(move.dest, Suit.BLACK);
                Engine.bPieces[index] ^= (1L << move.dest);
                Engine.bPieces[6] ^= (1L << move.dest);

                return index;

            }

        }
        return 6; // returns the total pieces (implies no capture)
    }

    public  void unMake(Move move, Suit suit,int captIndex){
        if(suit.equals(Suit.BLACK)){
            long tofro = (1L << move.src | 1L << move.dest);
            Engine.bPieces[move.getIndex()] ^= tofro;
            Engine.bPieces[6] ^= tofro;


            if(move.moveType.equals(MoveType.CAPTURE)){
                Engine.wPieces[captIndex] ^= (1L << move.dest);
                Engine.wPieces[6] |= Engine.wPieces[captIndex];
            }

        }else{
            long tofro = (1L << move.src | 1L << move.dest);
            Engine.wPieces[move.getIndex()] ^= tofro;
            Engine.wPieces[6] ^= tofro;

            if(move.moveType.equals(MoveType.CAPTURE)){
                Engine.bPieces[captIndex] ^= (1L << move.dest);
                Engine.bPieces[6] |= Engine.bPieces[captIndex];
            }
        }
    }

    public int search(int alpha, int beta, int depth,boolean maximizer, Move[] bestMoves){
        if(gameOver() || depth == 0){

            return eval(maximizer ? Suit.BLACK : Suit.WHITE);
        }
        if(maximizer){
            int maxEval = Integer.MIN_VALUE;
            Moves children = aggregateMoves(Suit.BLACK);

            for(int i = 0; i < children.getSize(); i++){
                Move move = children.getMove(i);
                int captIndex =  make(move,Suit.BLACK);
                int eval = search(alpha, beta, depth - 1, false, bestMoves);
                unMake(move, Suit.BLACK, captIndex);
                maxEval = Math.max(eval, maxEval);

                alpha = Math.max(maxEval, alpha);
                if(eval >= alpha){
                    bestMoves[depth] = move;
                }
                if(beta <= alpha){
                    break;
                }
            }
            return maxEval;
        }else{
            int minEval = Integer.MAX_VALUE;
            Moves children = aggregateMoves(Suit.WHITE);

            for(int i = 0; i < children.getSize(); i++){
                Move move = children.getMove(i);
                int captIndex =  make(move,Suit.WHITE);
                int eval = search(alpha, beta, depth -1, true,bestMoves);

                unMake(move, Suit.WHITE, captIndex);

                minEval = Math.min(eval, minEval);

                beta = Math.min(minEval, beta);
                if(eval <= beta){
                    bestMoves[depth] = move;
                }
                if(beta <= alpha){
                    break;
                }

            }
            return minEval;
        }

    }

    public  int perft(int depth, Suit suit){
        Moves moves;
        int nMoves;
        int nodes = 0;
        if(depth == 0){
            return 1;
        }
        moves = aggregateMoves(suit);

        nMoves = moves.getSize();
        for(int i = 0; i < nMoves; i++){
            Move move = moves.getMove(i);

            int ind = make(move,suit);
            nodes += perft(depth - 1, suit.equals(Suit.BLACK) ? Suit.WHITE : Suit.BLACK);
            unMake(move,suit, ind);


        }
        return nodes;


    }

 /*
     Helper Functions
 ______________________________________________________________________________________
  */

    public  int findBoard(int dest, Suit suit){
       if(suit.equals(Suit.BLACK)){
           for(int i = 0; i < bPieces.length - 1; i++){
               if((bPieces[i] & (1L << dest)) != 0L){
                   return i;
               }
           }
       }else{
           for(int i = 0; i < wPieces.length - 1; i++){
               if((wPieces[i] & (1L << dest)) != 0L){
                   return i;
               }
           }

       }
       return 6;
    }

    public String[][] getBoard(){
        return board;
    }

    public  void initBoards(String[][] board) {
        for (int i = 0; i < 64; i++) {
            switch (board[i / 8][i % 8]) {
                case "P":
                    Engine.bPieces[0] |= getLong(i);
                    break;
                case "p":
                    Engine.wPieces[0] |= getLong(i);
                    break;
                case "r":
                    Engine.wPieces[1] |= getLong(i);
                    break;
                case "R":
                    Engine.bPieces[1] |= getLong(i);
                    break;
                case "k":
                    Engine.wPieces[5] |= getLong(i);
                    break;
                case "K":
                    Engine.bPieces[5] |= getLong(i);
                    break;
                case "n":
                    Engine.wPieces[2] |= getLong(i);
                    break;
                case "N":
                    Engine.bPieces[2] |= getLong(i);
                    break;
                case "B":
                    Engine.bPieces[3] |= getLong(i);
                    break;
                case "b":
                    Engine.wPieces[3] |= getLong(i);
                    break;
                case "Q":
                    Engine.bPieces[4] |= getLong(i);
                    break;
                case "q":
                    Engine.wPieces[4] |= getLong(i);
                    break;
            }

        }
        for(int i = 0; i < 6; i++){
            Engine.wPieces[6] |= Engine.wPieces[i];
        }
        for(int i = 0; i < 6; i++){
            Engine.bPieces[6] |= Engine.bPieces[i];
        }

    }

    public  void resetBoards() {
        for(int i = 0; i < wPieces.length; i++){
            Engine.wPieces[i] = 0L;
        }
        for(int i = 0; i < bPieces.length; i++){
            Engine.bPieces[i] = 0L;
        }
    }


    public  void printLong(long board) {
        char[][] table = new char[8][8];

        String binStr = Long.toUnsignedString(board, 2);
        while (binStr.length() < 64) {
            binStr = "0" + binStr;

        }

        int i = 0;
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                table[j][k] = binStr.charAt(i++);
            }
        }
        for (char[] m : table) {
            for (char n : m) {

                System.out.printf("%c ", n);
            }
            System.out.println();
        }
        System.out.println();


    }

    public  void buildFile() {
        long aFile = 2L;
        aFile <<= 8;
        for (int i = 0; i < 8; i++) {
            aFile |= 2L;
            aFile <<= 8;

        }
        aFile |= 2L;


    }

    public  long getLong(int i) {
        return 1L << i;
    }

    public int countSetBits(long table) {
        int c = 0;
        for (int i = 0; table >= 0; i++) {
            table &= table - 1;
            c++;
        }
        return c;
    }


    public  int popCount(long x) {
        int count = 0;
        while (x > 0) {
            count++;
            x &= x - 1; // reset LS1B
        }
        return count;

    }


    public  int log2(long x) {
        return 63 - Long.numberOfLeadingZeros(x);
    }


    public  int[] getPawns(long pawns) {
        int pawnArr[] = new int[popCount(pawns)];
        int count = 0;
        while (pawns != 0L) {
            pawnArr[count] = log2(pawns & -pawns);
            count++;
            pawns = (pawns & (pawns - 1));

        }
        return pawnArr;
    }

    public boolean isValid(long moves, int to){
        return (moves & 1L << to) != 0L;
    }

    public  boolean gameOver(){
        //TODO
        return false;
    }

    public MoveType getType(long attack, Suit suit){
        if(suit.equals(Suit.BLACK)){
            long isAttack = attack & wPieces[6];
            if(isAttack != 0L){
                return MoveType.CAPTURE;
            }else{
                return MoveType.NONCAPTURE;
            }
        }
        else{
            long isAttack = attack & bPieces[6];
            if(isAttack != 0L){
                return MoveType.CAPTURE;
            }else{
                return MoveType.NONCAPTURE;
            }
        }
    }



    /*
    Main Method - Testing and other crap
    _____________________________________________________________________
     */













}
