package im.jok.jokimchess.chessboard;

import java.util.ArrayList;
import java.util.List;

public class FENHelper {
    private static final String STANDARD_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private final String fen;
    private final List<Piece> piecesOnTheBoard = new ArrayList<>();

    public FENHelper() {
        this(STANDARD_FEN);
    }

    public FENHelper(String fen) {
        this.fen = fen;
    }

    //TODO: PGN Method
    public Board toBoard() {
        Piece[][] board = new Piece[8][8];
        boolean[] castlingOptions = this.getCastlingOptions();
        int index = 0;
        for (int end = 0; end < 8; end++) {
            for (int i = 0; i < 8; i++) {
                char pieceSymbol = fen.charAt(index);
                if (pieceSymbol >= '1' && pieceSymbol <= '8') {
                    int skip = pieceSymbol - '0';
                    for (int j = 0; j < skip; j++) {
                        board[end][i + j] = null;
                    }
                    i += skip - 1;
                } else {
                    Piece p = new Piece(end, i, pieceSymbol, false);
                    boolean isWhiteRook = pieceSymbol == 'R', isBlackRook = pieceSymbol == 'r', isWhiteKing = pieceSymbol == 'K', isBlackKing = pieceSymbol == 'k';

                    // Rook castle-ability
                    if (castlingOptions[0] && isWhiteRook && end == 7 && i == 7) {
                        p.setCastle(true);
                    } else if (castlingOptions[1] && isWhiteRook && end == 7 && i == 0) {
                        p.setCastle(true);
                    } else if (castlingOptions[2] && isBlackRook && end == 0 && i == 7) {
                        p.setCastle(true);
                    } else if (castlingOptions[3] && isBlackRook && end == 0 && i == 0) {
                        p.setCastle(true);
                    }

                    // King castle-ability
                    if ((castlingOptions[0] && isWhiteKing && end == 7 && i == 4) || (castlingOptions[1] && isWhiteKing && end == 7 && i == 4)) {
                        p.setCastle(true);
                    } else if (castlingOptions[2] && isBlackKing && end == 0 && i == 4 || castlingOptions[3] && isBlackKing && end == 0 && i == 4) {
                        p.setCastle(true);
                    }

                    piecesOnTheBoard.add(p);
                    board[end][i] = p;
                }
                index++;
            }
            index++;
        }
        return new Board(board, isWhitesTurn(), getCastlingOptions(), getEnPassantSquare(), getHalfmoveCount(), getFullmoveCount(), piecesOnTheBoard);
    }

    public String boardToFen(Board board) {
        StringBuilder fen = new StringBuilder();
        for (int row = 0; row < 8; row++) {
            int countOfEmptySpaces = 0;
            for (int index = 0; index < 8; index++) {
                // Iterate whole board, if empty, count empty spaces
                if (board.getBoard()[row][index] == null) {
                    countOfEmptySpaces++;
                } else {
                    // Chessboard.Piece hit
                    if (countOfEmptySpaces > 0) {
                        // Put number,
                        fen.append(countOfEmptySpaces);
                        countOfEmptySpaces = 0;
                    }
                    // then piece representation
                    fen.append(board.getBoard()[row][index]);
                }
            }

            if (countOfEmptySpaces > 0) {
                fen.append(countOfEmptySpaces);
            }
            // Not row 7 because it's already at the end
            if (row != 7) {
                fen.append("/");
            }
        }

        fen = new StringBuilder(board.isTurnWhite() ? fen + " w " : fen + " b ");
        char[] castlingSymbols = {'K', 'Q', 'k', 'q'};
        boolean[] castlingOptions = board.getCastlingOptions();
        boolean atLeastOne = false;
        for (int i = 0; i < castlingSymbols.length; i++) {
            if (castlingOptions[i]) {
                fen.append(castlingSymbols[i]);
                atLeastOne = true;
            }
        }
        if (!atLeastOne) {
            fen.append("- ");
        } else {
            fen.append(" ");
        }

        // En passant square
        int[] enPassantSquare = board.getEnPassantSquare();
        fen = new StringBuilder(enPassantSquare != null ? positionToString(board.getEnPassantSquare()[0], board.getEnPassantSquare()[1]) + " " : fen + "- ");

        // Chessboard.Move counts
        fen.append(board.getHalfmove()).append(" ").append(board.getFullmove());
        return fen.toString();
    }

    public static String positionToString(int x, int y) {
        return "" + (char) ('a' + y) + (8 - x);
    }

    private int getFullmoveCount() {
        return Integer.parseInt(fen.split(" ")[5]);
    }

    private int getHalfmoveCount() {
        return Integer.parseInt(fen.split(" ")[4]);
    }

    private int[] getEnPassantSquare() {
        String[] xd = fen.split(" ");
        if (xd[3].contains("-")) {
            return null;
        }
        return new int[]{8 - Character.getNumericValue(xd[3].charAt(1)), xd[3].charAt(0) - 'a'};
    }

    private boolean[] getCastlingOptions() {
        String[] xd = fen.split(" ");
        boolean[] options = new boolean[4];
        if (xd[2].contains("K")) {
            options[0] = true;
        }
        if (xd[2].contains("Q")) {
            options[1] = true;
        }
        if (xd[2].contains("k")) {
            options[2] = true;
        }
        if (xd[2].contains("q")) {
            options[3] = true;
        }
        return options;
    }

    private boolean isWhitesTurn() {
        return fen.split(" ")[1].equals("w");
    }


}