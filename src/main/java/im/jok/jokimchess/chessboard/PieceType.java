package im.jok.jokimchess.chessboard;

public enum PieceType {
    KING(10, 10, 10),
    QUEEN(9, 10, 12),
    BISHOP(3, 3, 2.5f),
    PAWN(1, 1, 1.2f),
    KNIGHT(3, 3.25f, 3.5f),
    ROOK(5, 5.5f, 6);

    private final float valueEarly;
    private final float valueMid;
    private final float valueEnd;

    PieceType(float valueEarly, float valueMid, float valueEnd) {
        this.valueEarly = valueEarly;
        this.valueMid = valueMid;
        this.valueEnd = valueEnd;
    }

    public float getValue(int fullmoves) {
        if (fullmoves <= 10) {
            return valueEarly;
        } else if (fullmoves <= 20) {
            return valueMid;
        } else {
            return valueEnd;
        }
    }
}
