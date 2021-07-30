public class Move {
    private final int x, y, targetX, targetY;
    private final Piece pieceTaken, pieceMoved;
    private MoveType moveType;

    public Move(int x, int y, int targetX, int targetY, Piece pieceMoved, Piece pieceTaken, MoveType moveType) {
        this.x = x;
        this.y = y;
        this.targetX = targetX;
        this.targetY = targetY;
        this.pieceTaken = pieceTaken;
        this.pieceMoved = pieceMoved;
        this.moveType = moveType;
    }

    public String toString() {
        switch (moveType) {
            case CASTLING_KINGSIDE_B, CASTLING_KINGSIDE_W:
                return "O-O";
            case CASTLING_QUEENSIDE_B, CASTLING_QUEENSIDE_W:
                return "O-O-O";
            case ENPASSANT_B, ENPASSANT_W:
                return FENHelper.positionToString(x, y) + "x" + FENHelper.positionToString(targetX, targetY) + "_";
            case NORMAL:
                if (pieceTaken == null) {
                    return FENHelper.positionToString(x, y) + FENHelper.positionToString(targetX, targetY);
                } else {
                    return FENHelper.positionToString(x, y) + "x" + FENHelper.positionToString(targetX, targetY);
                }
            case PROMOTION_B:
            case PROMOTION_W:
                // TODO: Nicht unbedingt immer zu einer Queen promoted, kann auch capture sein
                if (pieceTaken != null) {
                    return FENHelper.positionToString(x, y) + "x" + FENHelper.positionToString(targetX, targetY) + "=Q";
                }
                return FENHelper.positionToString(x, y) + FENHelper.positionToString(targetX, targetY) + "=Q";
            default:
                throw new IllegalArgumentException("MoveType unbekannt: " + moveType);
        }
    }

    /**
     * Only compares x and y of the moves, not the piece nor the piece taken.
     *
     * @param m
     * @return
     */
    public boolean equals(Move m) {
        return m.getPos()[0] == x && m.getPos()[1] == y && m.getTargetPos()[0] == targetX && m.getTargetPos()[1] == targetY;
    }

    public int[] getPos() {
        return new int[]{x, y};
    }

    public int[] getTargetPos() {
        return new int[]{targetX, targetY};
    }

    public Piece getPieceTaken() {
        return pieceTaken;
    }

    public Piece getPieceMoved() {
        return pieceMoved;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }
}