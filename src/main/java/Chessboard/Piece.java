package Chessboard;

import Enums.PieceColor;
import Enums.PieceType;

public class Piece {
    private final PieceType pieceType;
    private final PieceColor pieceColor;
    private int x, y;
    private boolean canCastle;

    public Piece(int x, int y, char pieceCharacter, boolean canCastle) {
        this.x = x;
        this.y = y;
        this.canCastle = true;

        if (Character.isUpperCase(pieceCharacter)) {
            this.pieceColor = PieceColor.WHITE;
            pieceCharacter = Character.toLowerCase(pieceCharacter);
        } else {
            this.pieceColor = PieceColor.BLACK;
        }


        switch (pieceCharacter) {
            case 'k':
                this.pieceType = PieceType.KING;
                break;
            case 'q':
                this.pieceType = PieceType.QUEEN;
                break;
            case 'b':
                this.pieceType = PieceType.BISHOP;
                break;
            case 'r':
                this.pieceType = PieceType.ROOK;
                break;
            case 'p':
                this.pieceType = PieceType.PAWN;
                break;
            case 'n':
                this.pieceType = PieceType.KNIGHT;
                break;
            default:
                throw new IllegalArgumentException("Unknown Chessboard.Piece, tried to create with: " + pieceCharacter);
        }
    }

    public Piece(int x, int y, char pieceCharacter) {
        this(x, y, pieceCharacter, false);
    }

    public String toString() {
        switch (getPieceType()) {
            case KING:
                return pieceColor == PieceColor.WHITE ? "K" : "k";
            case QUEEN:
                return pieceColor == PieceColor.WHITE ? "Q" : "q";
            case BISHOP:
                return pieceColor == PieceColor.WHITE ? "B" : "b";
            case KNIGHT:
                return pieceColor == PieceColor.WHITE ? "N" : "n";
            case ROOK:
                return pieceColor == PieceColor.WHITE ? "R" : "r";
            case PAWN:
                return pieceColor == PieceColor.WHITE ? "P" : "p";
            default:
                throw new IllegalArgumentException("Illegal Chessboard.Piece: " + getPieceType());
        }
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
