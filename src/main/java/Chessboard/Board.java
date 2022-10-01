package Chessboard;

import Enums.GameState;
import Enums.PieceColor;
import Enums.PieceType;

import java.util.*;
import java.util.stream.Collectors;

import static Enums.MoveType.*;

public class Board {
    public final Piece[][] board;
    private boolean turnWhite;
    private boolean[] castlingOptions;
    private int[] enPassantSquare;
    private int halfmove, fullmove, halfmovesBeforeUndo;
    private final List<Piece> piecesOnTheBoard;
    private final Stack<Move> moveArchive;

    public Board(Piece[][] board, boolean turnWhite, boolean[] castlingOptions, int[] enPassantSquare, int halfmove, int fullmove, List<Piece> piecesOnTheBoard) {
        this.board = board;
        this.turnWhite = turnWhite;
        this.castlingOptions = castlingOptions;
        this.enPassantSquare = enPassantSquare;
        this.halfmove = halfmove;
        this.fullmove = fullmove;
        this.halfmovesBeforeUndo = 0;
        this.piecesOnTheBoard = piecesOnTheBoard;
        this.moveArchive = new Stack<>();
    }

    public void playMove(Move m) {
        moveArchive.add(m);
        Piece piece = m.getPieceMoved(), pieceTaken = m.getPieceTaken();
        int targetX = m.getTargetX(), targetY = m.getTargetY(), startX = m.getStartX(), startY = m.getStartY();
        boolean pieceIsWhite = piece.getPieceColor() == PieceColor.WHITE;
        // TODO: Add JUMPSTART movetype to set enPassantSquare
        // TODO: King loses castling rights on move
        // TODO: allPiecesOnTheBoard refresh on capture
        // TODO: castling options fix
        switch (m.getMoveType()) {
            case NORMAL:
                board[targetX][targetY] = piece;
                board[startX][startY] = null;
                piece.setPosition(targetX, targetY);
                if (pieceTaken != null) {
                    piecesOnTheBoard.remove(pieceTaken);
                }
                break;
            case ENPASSANT_W:
                piece.setPosition(targetX, targetY);
                piecesOnTheBoard.remove(getPieceAt(targetX + 1, targetY));
                board[targetX][targetY] = piece;
                board[startX][startY] = null;
                board[enPassantSquare[0] + 1][enPassantSquare[1]] = null;
                break;
            case ENPASSANT_B:
                piece.setPosition(targetX, targetY);
                piecesOnTheBoard.remove(getPieceAt(targetX - 1, targetY));
                board[targetX][targetY] = piece;
                board[startX][startY] = null;
                board[enPassantSquare[0] - 1][enPassantSquare[1]] = null;
                break;
            case PROMOTION_W, PROMOTION_B:
                System.out.println(pieceTaken);
                if (pieceTaken != null) {
                    piecesOnTheBoard.remove(pieceTaken);
                }
                piecesOnTheBoard.remove(piece); // Remove pawn
                Piece newQueen = new Piece(targetX, targetY, piece.getPieceColor() == PieceColor.WHITE ? 'Q' : 'q', false);
                board[targetX][targetY] = newQueen;
                board[startX][startY] = null;
                piecesOnTheBoard.add(newQueen);
                break;
            case CASTLING_KINGSIDE_W:
                board[targetX][targetY] = piece;
                board[startX][startY] = null;
                piece.setPosition(targetX, targetY);
                Piece rookA8 = this.getPieceAt(7, 7);
                board[7][7] = null;
                board[targetX][targetY - 1] = rookA8;
                rookA8.setPosition(targetX, targetY - 1);
                rookA8.setCastle(false);
                castlingOptions[0] = false;
                castlingOptions[1] = false;
                break;
            case CASTLING_QUEENSIDE_W:
                Piece rookA1 = this.getPieceAt(7, 0);
                board[targetX][targetY] = piece;
                board[startX][startY] = null;
                board[7][0] = null;
                board[targetX][targetY + 1] = rookA1;
                piece.setPosition(targetX, targetY);
                rookA1.setPosition(targetX, targetY + 1);
                rookA1.setCastle(false);
                castlingOptions[0] = false;
                castlingOptions[1] = false;
                break;
            case CASTLING_KINGSIDE_B:
                Piece rookH8 = this.getPieceAt(0, 7);
                board[targetX][targetY] = piece;
                board[startX][startY] = null;
                board[targetX][targetY + 1] = null;
                board[targetX][targetY - 1] = rookH8;
                piece.setPosition(targetX, targetY);
                rookH8.setPosition(targetX, targetY - 1);
                rookH8.setCastle(false);
                castlingOptions[2] = false;
                castlingOptions[3] = false;
                break;
            case CASTLING_QUEENSIDE_B:
                Piece rookH1 = this.getPieceAt(0, 0);
                board[targetX][targetY] = piece;
                board[startX][startY] = null;
                board[targetX][targetY - 2] = null;
                board[targetX][targetY + 1] = rookH1;
                piece.setPosition(targetX, targetY);
                rookH1.setPosition(targetX, targetY + 1);
                rookH1.setCastle(false);
                castlingOptions[2] = false;
                castlingOptions[3] = false;
                break;
        }
        if (!turnWhite) {
            fullmove++;
        }
        if (piece.getPieceType() == PieceType.PAWN || pieceTaken != null) {
            halfmovesBeforeUndo = halfmove;
            halfmove = -1;
        }
        halfmove++;
        halfmovesBeforeUndo = 0;
        this.turnWhite = !turnWhite;
    }

    public void undoLastMove() {
        Move m = moveArchive.pop();
        int targetX = m.getTargetX(), targetY = m.getTargetY(), startX = m.getStartX(), startY = m.getStartY();
        Piece pieceMoved = m.getPieceMoved(), pieceTaken = m.getPieceTaken();
        switch (m.getMoveType()) {
            case NORMAL:
                board[targetX][targetY] = pieceTaken;
                board[startX][startY] = pieceMoved;
                if (pieceTaken != null) {
                    pieceTaken.setPosition(targetX, targetY);
                    piecesOnTheBoard.add(pieceTaken);
                }
                pieceMoved.setPosition(startX, startY);

                break;
            case ENPASSANT_B:
                board[targetX][targetY] = null;
                board[startX][startY] = pieceMoved;
                board[enPassantSquare[0] - 1][enPassantSquare[1]] = pieceTaken;
                pieceMoved.setPosition(startX, startY);
                piecesOnTheBoard.add(pieceTaken);
                break;
            case ENPASSANT_W:
                board[targetX][targetY] = null;
                board[startX][startY] = pieceMoved;
                board[enPassantSquare[0] + 1][enPassantSquare[1]] = pieceTaken;
                pieceMoved.setPosition(startX, startY);
                piecesOnTheBoard.add(pieceTaken);
                break;
            case PROMOTION_W, PROMOTION_B:
                piecesOnTheBoard.remove(getPieceAt(targetX, targetY)); // Remove new quuen
                piecesOnTheBoard.add(pieceMoved);
                board[startX][startY] = pieceMoved;
                board[targetX][targetY] = pieceTaken;
                if (pieceTaken != null) {
                    piecesOnTheBoard.add(pieceTaken);
                }
                break;
            case CASTLING_KINGSIDE_W:
                board[targetX][targetY] = null;
                board[startX][startY] = pieceMoved;
                Piece rookH1 = getPieceAt(7, 5);
                rookH1.setPosition(7, 7);
                rookH1.setCastle(true);
                board[7][7] = rookH1;
                board[7][5] = null;
                castlingOptions[0] = true;
                castlingOptions[1] = true;
                pieceMoved.setPosition(startX, startY);
                break;
            case CASTLING_QUEENSIDE_W:
                board[targetX][targetY] = null;
                board[startX][startY] = pieceMoved;
                Piece rookA1 = getPieceAt(7, 3);
                rookA1.setPosition(7, 0);
                rookA1.setCastle(true);
                board[7][0] = rookA1;
                board[7][3] = null;
                castlingOptions[1] = true;
                castlingOptions[0] = true;
                pieceMoved.setPosition(startX, startY);
                break;
            case CASTLING_KINGSIDE_B:
                board[targetX][targetY] = null;
                board[startX][startY] = pieceMoved;
                Piece rookH8 = getPieceAt(0, 5);
                rookH8.setPosition(0, 7);
                rookH8.setCastle(true);
                board[0][7] = rookH8;
                board[0][5] = null;
                castlingOptions[2] = true;
                castlingOptions[3] = true;
                pieceMoved.setPosition(startX, startY);
                break;
            case CASTLING_QUEENSIDE_B:
                board[0][2] = null;
                board[0][4] = pieceMoved;
                Piece rookA8 = getPieceAt(0, 3);
                rookA8.setPosition(0, 0);
                rookA8.setCastle(true);
                board[0][0] = rookA8;
                board[0][3] = null;
                castlingOptions[2] = true;
                castlingOptions[3] = true;
                pieceMoved.setPosition(0, 4);
                break;
            default:
                throw new IllegalArgumentException("Unknown MoveType: " + m.getMoveType().toString());
        }
        if (turnWhite) {
            fullmove--;
        }
        halfmove = halfmovesBeforeUndo > 0 ? halfmovesBeforeUndo : halfmove - 1;
        this.turnWhite = !turnWhite;
    }

    public List<Move> allPossibleMoves(boolean checkTest) {
        PieceColor activeColor = turnWhite ? PieceColor.WHITE : PieceColor.BLACK;
        List<Move> moves = new ArrayList<>();
        List<Piece> filtered = piecesOnTheBoard.stream().filter(p -> p.getPieceColor() == activeColor).collect(Collectors.toList());

        for (Piece p : filtered) {
            moves.addAll(availableMovesForPiece(p, checkTest));
        }
        return moves;
    }


    private List<Move> availableMovesForPiece(Piece piece, boolean checkTest) {
        List<Move> moves = new ArrayList<>();
        PieceType pieceTypeOnOldPos = piece.getPieceType();
        PieceColor pieceColorOnOldPos = piece.getPieceColor();
        int x = piece.getX(), y = piece.getY();

        // Is it the players turn?
        if (turnWhite && pieceColorOnOldPos == PieceColor.BLACK || !turnWhite && pieceColorOnOldPos == PieceColor.WHITE) {
            throw new IllegalArgumentException(turnWhite ? "Trying to do a BLACK move while WHITE is active" : "Trying to do a WHITE move while BLACK is active");
        }

        // Infinite mover
        final int[] up = {-1, 0}, down = {1, 0}, left = {0, -1}, right = {0, 1}, upleft = {-1, -1}, upright = {-1, 1}, downleft = {1, -1}, downright = {1, 1};
        switch (pieceTypeOnOldPos) {
            case BISHOP:
                generateInfiniteMoverMoves(moves, piece, x, y, new int[][]{upleft, upright, downleft, downright});
                break;
            case ROOK:
                generateInfiniteMoverMoves(moves, piece, x, y, new int[][]{left, right, up, down});
                break;
            case QUEEN:
                generateInfiniteMoverMoves(moves, piece, x, y, new int[][]{left, right, up, down, upleft, upright, downleft, downright});
                break;
            // Normal mover
            case KNIGHT:
                int[][] knightmoves = {new int[]{x + 1, y + 2}, new int[]{x + 2, y + 1}, new int[]{x + 2, y - 1}, new int[]{x + 1, y - 2}, new int[]{x - 1, y + 2},
                        new int[]{x - 2, y + 1}, new int[]{x - 2, y - 1}, new int[]{x - 1, y - 2}};

                for (int[] jump : knightmoves) {
                    Piece targetPiece = getPieceAt(jump[0], jump[1]);
                    if (targetPiece != null) {
                        PieceColor targetPieceColor = targetPiece.getPieceColor();
                        // Hits same color
                        if (turnWhite && targetPieceColor == PieceColor.WHITE || !turnWhite && targetPieceColor == PieceColor.BLACK) {
                            continue;
                        }
                    }
                    moves.add(new Move(x, y, jump[0], jump[1], piece, targetPiece, NORMAL));
                }
                break;
            case KING:
                int[][] kingmoves = {new int[]{x, y + 1}, new int[]{x, y - 1}, new int[]{x + 1, y}, new int[]{x + 1, y + 1}, new int[]{x + 1, y - 1},
                        new int[]{x - 1, y}, new int[]{x - 1, y + 1}, new int[]{x - 1, y - 1}};

                for (int[] shuffle : kingmoves) {
                    Piece targetPiece = getPieceAt(shuffle[0], shuffle[1]);
                    if (targetPiece != null) {
                        PieceColor targetPieceColor = targetPiece.getPieceColor();
                        if (turnWhite && targetPieceColor == PieceColor.WHITE || !turnWhite && targetPieceColor == PieceColor.BLACK) {
                            continue;
                        }
                    }
                    moves.add(new Move(x, y, shuffle[0], shuffle[1], piece, targetPiece, NORMAL));
                }

                // Castling
                // TODO: Hier fehlt noch, dass der King eventuell im Schach steht
                boolean canCastle = piece.canCastle();
                if (canCastle && turnWhite) {
                    if (x == 7 && y == 4 && getPieceAt(7, 5) == null && getPieceAt(7, 6) == null && getPieceAt(7, 7) != null && getPieceAt(7, 7).canCastle()) {
                        moves.add(new Move(x, y, 7, 6, piece, null, CASTLING_KINGSIDE_W));
                    }
                    if (x == 7 && y == 4 && getPieceAt(7, 1) == null && getPieceAt(7, 2) == null && getPieceAt(7, 3) == null && getPieceAt(7, 0) != null && getPieceAt(7, 0).canCastle()) {
                        moves.add(new Move(x, y, 7, 2, piece, null, CASTLING_QUEENSIDE_W));
                    }
                } else if (canCastle && !turnWhite) {
                    if (x == 0 && y == 4 && getPieceAt(0, 5) == null && getPieceAt(0, 6) == null && getPieceAt(0, 7) != null && getPieceAt(0, 7).canCastle()) {
                        moves.add(new Move(x, y, 0, 6, piece, null, CASTLING_KINGSIDE_B));
                    }
                    if (x == 0 && y == 4 && getPieceAt(0, 1) == null && getPieceAt(0, 2) == null && getPieceAt(0, 3) == null && getPieceAt(0, 0) != null && getPieceAt(0, 0).canCastle()) {
                        moves.add(new Move(x, y, 0, 2, piece, null, CASTLING_QUEENSIDE_B));
                    }
                }
                break;
            case PAWN:
                if (turnWhite) {
                    // White
                    Piece inFront = getPieceAt(x - 1, y), inFrontLeft = getPieceAt(x - 1, y - 1), inFrontRight = getPieceAt(x - 1, y + 1);
                    if (inFront == null) {
                        moves.add(new Move(x, y, x - 1, y, piece, null, x != 1 ? NORMAL : PROMOTION_W));

                        // Zwei vor beim Start
                        if (x == 6 && getPieceAt(4, y) == null) {
                            moves.add(new Move(x, y, x - 2, y, piece, null, NORMAL));
                        }
                    }

                    // Vorne links schlagen
                    if (inFrontLeft != null && inFrontLeft.getPieceColor() == PieceColor.BLACK) {
                        moves.add(new Move(x, y, x - 1, y - 1, piece, inFrontLeft, x != 1 ? NORMAL : PROMOTION_W));
                    }
                    if (Arrays.equals(new int[]{x - 1, y - 1}, enPassantSquare)) {
                        moves.add(new Move(x, y, x - 1, y - 1, piece, getPieceAt(x, y - 1), ENPASSANT_W));
                    }

                    // Vorne rechts schlagen
                    if (inFrontRight != null && inFrontRight.getPieceColor() == PieceColor.BLACK) {
                        moves.add(new Move(x, y, x - 1, y + 1, piece, inFrontRight, x != 1 ? NORMAL : PROMOTION_W));
                    }
                    if (Arrays.equals(new int[]{x - 1, y + 1}, enPassantSquare)) {
                        moves.add(new Move(x, y, x - 1, y + 1, piece, getPieceAt(x, y + 1), ENPASSANT_W));
                    }

                } else {
                    // Black
                    Piece inFront = getPieceAt(x + 1, y), inFrontLeft = getPieceAt(x + 1, y - 1), inFrontRight = getPieceAt(x + 1, y + 1);
                    if (inFront == null) {
                        moves.add(new Move(x, y, x + 1, y, piece, null, x != 6 ? NORMAL : PROMOTION_B));

                        // Zwei vor beim Start
                        if (x == 1 && getPieceAt(3, y) == null) {
                            moves.add(new Move(x, y, x + 2, y, piece, null, NORMAL));
                        }
                    }

                    // Vorne links schlagen
                    if (inFrontLeft != null && inFrontLeft.getPieceColor() == PieceColor.WHITE) {
                        moves.add(new Move(x, y, x + 1, y - 1, piece, inFrontLeft, x != 6 ? NORMAL : PROMOTION_B));
                    }
                    if (Arrays.equals(enPassantSquare, new int[]{x + 1, y - 1})) {
                        moves.add(new Move(x, y, x + 1, y - 1, piece, getPieceAt(x, y - 1), ENPASSANT_B));
                    }

                    // Vorne rechts schlagen
                    if (inFrontRight != null && inFrontRight.getPieceColor() == PieceColor.WHITE) {
                        moves.add(new Move(x, y, x + 1, y + 1, piece, inFrontRight, x != 6 ? NORMAL : PROMOTION_B));
                    }
                    if (Arrays.equals(new int[]{x + 1, y + 1}, enPassantSquare)) {
                        moves.add(new Move(x, y, x + 1, y + 1, piece, getPieceAt(x, y + 1), ENPASSANT_B));
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown piece on " + FENHelper.positionToString(piece.getX(), piece.getY()) + toString() + "!");
        }
        return filterMoves(moves, checkTest);
    }

    private List<Move> filterMoves(List<Move> moves, boolean checkTest) {
        // Filter moves by invalid position
        List<Move> movesWithoutWrongCoordinates = new ArrayList<>(moves);
        for (Move m : movesWithoutWrongCoordinates) {
            int targetX = m.getTargetX(), targetY = m.getTargetY();
            if (targetX > 7 || targetY > 7 || targetX < 0 || targetY < 0) {
                moves.remove(m);
            }
        }

        if (checkTest) {
            //TODO: Use iterators instead of copying lists
            //TODO: Check detection is kinda broken
            List<Move> iterMoves = new ArrayList<>(moves);
            for (Move m : iterMoves) {
                playMove(m);
                if (isKingInCheck()) {
                    moves.remove(m);
                }
                undoLastMove();
            }
        }
        return moves;
    }

    public boolean isKingInCheck() {
        //TODO: Use iterators instead of copying lists

        List<Move> allEnemyMoves = allPossibleMoves(false);
        for (Move enemyMove : allEnemyMoves) {
            Piece pieceCaptured = enemyMove.getPieceTaken();
            if (pieceCaptured != null && pieceCaptured.getPieceType() == PieceType.KING) {
                return true;
            }
        }
        return false;
    }

    public GameState determineGameState() {
        if (halfmove >= 50) {
            return GameState.DRAW_BY_50_MOVE_RULE;
        }
        if (allPossibleMoves(true).isEmpty()) {
            if (turnWhite) {
                if (!isKingInCheck()) {
                    return GameState.STALEMATE;
                }
                return GameState.BLACK_WINS;
            } else {
                if (!isKingInCheck()) {
                    return GameState.STALEMATE;
                }
                return GameState.WHITE_WINS;
            }
        }
        return GameState.ONGOING;
    }

    private Piece getPieceAt(int x, int y) {
        if (x > 7 || y > 7 || x < 0 || y < 0) {
            return null;
        }
        return board[x][y];
    }

    private void generateInfiniteMoverMoves(List<Move> moves, Piece piece, int x, int y, int[][] directions) {
        for (int dir = 0; dir < directions.length; dir++) { // Jede Direction durchgehen
            for (int movelength = 1; movelength <= 7; movelength++) { // Jede Movelength durchgehen
                int[] newPos = {x + directions[dir][0] * movelength, y + directions[dir][1] * movelength};
                Piece pieceOnNewPos = getPieceAt(newPos[0], newPos[1]);
                if (pieceOnNewPos != null) {
                    PieceColor pieceColorOnNewPos = pieceOnNewPos.getPieceColor();
                    // Chessboard.Move trifft auf ein Chessboard.Piece der gleichen Farbe
                    if (turnWhite && pieceColorOnNewPos == PieceColor.WHITE || !turnWhite && pieceColorOnNewPos == PieceColor.BLACK) {
                        break;
                    }
                    // Chessboard.Move trifft auf ein Chessboard.Piece der anderen Farbe
                    if (turnWhite && pieceColorOnNewPos == PieceColor.BLACK || !turnWhite && pieceColorOnNewPos == PieceColor.WHITE) {
                        moves.add(new Move(x, y, x + directions[dir][0] * movelength, y + directions[dir][1] * movelength, piece, pieceOnNewPos, NORMAL));
                        break;
                    }
                }
                // Chessboard.Move trifft auf nichts
                moves.add(new Move(x, y, x + directions[dir][0] * movelength, y + directions[dir][1] * movelength, piece, null, NORMAL));
            }
        }
    }

    public Move getRandomMove() {
        Random r = new Random();
        List<Move> allPossibleMoves = allPossibleMoves(true);
        int numberOfMoves = allPossibleMoves.size();
        if (numberOfMoves == 0) {
            System.out.println("No possible moves left!");
        } else if (numberOfMoves == 1) {
            return allPossibleMoves(true).get(0);
        }
        return allPossibleMoves.get(r.nextInt(numberOfMoves - 1));
    }

    public String toString() {
        String output = "";
        for (int j = 0; j < 8; j++) {
            output += 8 - j + " ";
            for (int i = 0; i < 8; i++) {
                String concate = board[j][i] == null ? " " : board[j][i].toString();
                output += concate;
            }
            output += "\n";
        }
        return output += "  abcdefgh";
    }

    public Piece[][] getBoard() {
        return board;
    }

    public boolean isTurnWhite() {
        return turnWhite;
    }

    public boolean[] getCastlingOptions() {
        return castlingOptions;
    }

    public int[] getEnPassantSquare() {
        return enPassantSquare;
    }

    public int getHalfmove() {
        return halfmove;
    }

    public int getFullmove() {
        return fullmove;
    }

    public List<Piece> getPiecesOnTheBoard() {
        return piecesOnTheBoard;
    }
}