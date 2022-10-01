package Chessboard;

import Enums.MoveType;

public class Move {
    private final int startX, startY, targetX, targetY;
    private final Piece pieceTaken, pieceMoved;
    private final MoveType moveType;

    public Move(int startX, int startY, int targetX, int targetY, Piece pieceMoved, Piece pieceTaken, MoveType moveType) {
        this.startX = startX;
        this.startY = startY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.pieceTaken = pieceTaken;
        this.pieceMoved = pieceMoved;
        this.moveType = moveType;
    }

    public String toString() {
        //a2f4, a5xf5, a7a8=Q, a7xb8=N, 0-0
        switch (moveType) {
            case CASTLING_KINGSIDE_B, CASTLING_KINGSIDE_W:
                return "O-O";
            case CASTLING_QUEENSIDE_B, CASTLING_QUEENSIDE_W:
                return "O-O-O";
            case ENPASSANT_B, ENPASSANT_W:
                return FENHelper.positionToString(startX, startY) + "x" + FENHelper.positionToString(targetX, targetY) + "_";
            case NORMAL:
                if (pieceTaken == null) {
                    return FENHelper.positionToString(startX, startY) + FENHelper.positionToString(targetX, targetY);
                } else {
                    return FENHelper.positionToString(startX, startY) + "x" + FENHelper.positionToString(targetX, targetY);
                }
            case PROMOTION_B:
            case PROMOTION_W:
                // TODO: Not necessarilty promoted to queen. Could also be a capture
                if (pieceTaken != null) {
                    return FENHelper.positionToString(startX, startY) + "x" + FENHelper.positionToString(targetX, targetY) + "=Q";
                }
                return FENHelper.positionToString(startX, startY) + FENHelper.positionToString(targetX, targetY) + "=Q";
            default:
                throw new IllegalArgumentException("Enums.MoveType unbekannt: " + moveType);
        }
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
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

    /**
     * Only compares x and y of the moves, not the piece nor the piece taken.
     *
     * @param m
     * @return
     */
    public boolean equals(Move m) {
        return m.getStartX() == startX && m.getStartY() == startY && m.getTargetX() == targetX && m.getTargetY() == targetY;
    }
}