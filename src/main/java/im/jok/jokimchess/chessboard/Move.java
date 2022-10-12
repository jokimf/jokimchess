package im.jok.jokimchess.chessboard;

public record Move(int startX, int startY, int targetX, int targetY, Piece pieceMoved, Piece pieceTaken,
                   MoveType moveType) {

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
                // TODO: Not necessarily promoted to queen. Could also be a capture
                if (pieceTaken != null) {
                    return FENHelper.positionToString(startX, startY) + "x" + FENHelper.positionToString(targetX, targetY) + "=" + pieceMoved.getPieceType().toString();
                }
                return FENHelper.positionToString(startX, startY) + FENHelper.positionToString(targetX, targetY) + "=Q";
            default:
                throw new IllegalArgumentException("MoveType unknown: " + moveType);
        }
    }
}