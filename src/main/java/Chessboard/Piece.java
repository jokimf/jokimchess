package Chessboard;

import Enums.PieceColor;
import Enums.PieceType;

public class Piece {
    private final PieceType pieceType;
    private final PieceColor pieceColor;
    private int x, y;
    private boolean canCastle;

    public Piece(int targetX, int targetY, PieceType pieceType, PieceColor pieceColor) {
        this.x = targetX;
        this.y = targetY;
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
    }

    public Piece(int x, int y, char pieceCharacter, boolean canCastle) {
        this.x = x;
        this.y = y;
        this.canCastle = canCastle;

        if (Character.isUpperCase(pieceCharacter)) {
            this.pieceColor = PieceColor.WHITE;
            pieceCharacter = Character.toLowerCase(pieceCharacter);
        } else {
            this.pieceColor = PieceColor.BLACK;
        }


        switch (pieceCharacter) {
            case 'k' -> this.pieceType = PieceType.KING;
            case 'q' -> this.pieceType = PieceType.QUEEN;
            case 'b' -> this.pieceType = PieceType.BISHOP;
            case 'r' -> this.pieceType = PieceType.ROOK;
            case 'p' -> this.pieceType = PieceType.PAWN;
            case 'n' -> this.pieceType = PieceType.KNIGHT;
            default -> throw new IllegalArgumentException("Unknown Chessboard.Piece, tried to create with: " + pieceCharacter);
        }
    }

    public String toString() {
        return switch (pieceType) {
            case KING -> pieceColor == PieceColor.WHITE ? "K" : "k";
            case QUEEN -> pieceColor == PieceColor.WHITE ? "Q" : "q";
            case BISHOP -> pieceColor == PieceColor.WHITE ? "B" : "b";
            case KNIGHT -> pieceColor == PieceColor.WHITE ? "N" : "n";
            case ROOK -> pieceColor == PieceColor.WHITE ? "R" : "r";
            case PAWN -> pieceColor == PieceColor.WHITE ? "P" : "p";
        };
    }

    public boolean canCastle() {
        return canCastle;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setCastle(boolean b) {
        this.canCastle = b;
    }
}
