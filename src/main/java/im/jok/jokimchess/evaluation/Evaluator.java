package im.jok.jokimchess.evaluation;

import im.jok.jokimchess.chessboard.*;

import java.util.ArrayList;
import java.util.List;

public class Evaluator {

    // Position Heatmaps
    //WHITE
    private static final float[][] whiteKnightMap = { //done
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0.5f, 0.5f, 0.5f, 0.5f, 0, 0},
            {0, 0, 0.5f, 0.8f, 0.8f, 0.5f, 0, 0},
            {0, 0, 0.5f, 0.8f, 0.8f, 0.5f, 0, 0},
            {0, 0, 0.5f, 0.5f, 0.5f, 0.5f, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
    };
    private static final float[][] whitePawnMap = { // done
            {0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f},
            {0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f},
            {0.15f, 0.15f, 0.15f, 0.15f, 0.15f, 0.15f, 0.15f, 0.15f},
            {0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f},
            {0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f},
            {0.025f, 0.025f, 0.025f, 0.025f, 0.025f, 0.025f, 0.025f, 0.025f},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
    };
    private static final float[][] whiteBishopMap = { // done
            {0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f},
            {0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f},
            {0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.15f},
            {0.1f, 0.1f, 0.1f, 0.15f, 0.1f, 0.1f, 0.15f, 0.1f},
            {0.05f, 0.05f, 0.3f, 0.05f, 0.05f, 0.3f, 0.05f, 0.05f},
            {0.05f, 0.1f, 0.05f, 0.45f, 0.45f, 0.05f, 0.1f, 0.05f},
            {0, 0.5f, 0, 0.4f, 0.4f, 0, 0.5f, 0},
            {0, 0, 0.2f, 0, 0, 0.2f, 0, 0}
    };
    private static final float[][] whiteRookMap = {//done
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0.1f, 0, 0, 0, 0},
            {0.2f, 0.05f, 0.6f, 0.7f, 0.7f, 0.6f, 0.05f, 0.2f}
    };
    private static final float[][] whiteQueenMap = {//done
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0.4f, 0.4f, 0.4f, 0.4f, 0.4f, 0.4f, 0},
            {0, 0.4f, 0.5f, 0.5f, 0.5f, 0.5f, 0.4f, 0},
            {0, 0.4f, 0.5f, 0.8f, 0.8f, 0.5f, 0.4f, 0},
            {0, 0.4f, 0.5f, 0.8f, 0.8f, 0.5f, 0.4f, 0},
            {0, 0.4f, 0.5f, 0.5f, 0.5f, 0.5f, 0.4f, 0},
            {0, 0.4f, 0.4f, 0.45f, 0.45f, 0.4f, 0, 0},
            {0, 0, 0.1f, 0.2f, 0.1f, 0, 0, 0}
    };
    private static final float[][] whiteKingMap = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0.35f, 0.8f, 0.7f, 0, 0, 0, 0.8f, 0.35f}
    };

    //BLACK
    private static final float[][] blackKnightMap = { //done
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0.5f, 0.5f, 0.5f, 0.5f, 0, 0},
            {0, 0, 0.5f, 0.8f, 0.8f, 0.5f, 0, 0},
            {0, 0, 0.5f, 0.8f, 0.8f, 0.5f, 0, 0},
            {0, 0, 0.5f, 0.5f, 0.5f, 0.5f, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
    };
    private static final float[][] blackPawnMap = { // done
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0.025f, 0.025f, 0.025f, 0.025f, 0.025f, 0.025f, 0.025f, 0.025f},
            {0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f},
            {0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f},
            {0.15f, 0.15f, 0.15f, 0.15f, 0.15f, 0.15f, 0.15f, 0.15f},
            {0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f},
            {0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f}
    };
    private static final float[][] blackBishopMap = { // done
            {0, 0, 0.2f, 0, 0, 0.2f, 0, 0},
            {0, 0.5f, 0, 0.4f, 0.4f, 0, 0.5f, 0},
            {0.05f, 0.1f, 0.05f, 0.45f, 0.45f, 0.05f, 0.1f, 0.05f},
            {0.05f, 0.05f, 0.3f, 0.05f, 0.05f, 0.3f, 0.05f, 0.05f},
            {0.1f, 0.1f, 0.1f, 0.15f, 0.1f, 0.1f, 0.15f, 0.1f},
            {0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.15f},
            {0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f},
            {0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f}
    };
    private static final float[][] blackRookMap = { // done
            {0.2f, 0.05f, 0.6f, 0.7f, 0.7f, 0.6f, 0.05f, 0.2f},
            {0, 0, 0, 0.1f, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
    };
    private static final float[][] blackQueenMap = {//done
            {0, 0, 0.1f, 0.2f, 0.1f, 0, 0, 0},
            {0, 0.4f, 0.4f, 0.45f, 0.45f, 0.4f, 0, 0},
            {0, 0.4f, 0.5f, 0.5f, 0.5f, 0.5f, 0.4f, 0},
            {0, 0.4f, 0.5f, 0.8f, 0.8f, 0.5f, 0.4f, 0},
            {0, 0.4f, 0.5f, 0.8f, 0.8f, 0.5f, 0.4f, 0},
            {0, 0.4f, 0.5f, 0.5f, 0.5f, 0.5f, 0.4f, 0},
            {0, 0.4f, 0.4f, 0.4f, 0.4f, 0.4f, 0.4f, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
    };
    private static final float[][] blackKingMap = {
            {0.35f, 0.8f, 0.7f, 0, 0, 0, 0.8f, 0.35f},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
    };

    public EvaluationResult determineBestEvaluationResult(Board board, int depth) {
        return alphabeta(board, depth, new Evaluation(PieceColor.BLACK), new Evaluation(PieceColor.WHITE), board.isTurnWhite());
    }

    public float evaluate_single_board(Board b) {
        float sum = 0;
        int fullmoves = b.getFullmove(); //TODO: determine early/mid/end by piece count, not fullmoves

        for (Piece p : b.getPiecesOnTheBoard()) {
            int x = p.getX(), y = p.getY();
            PieceColor color = p.getPieceColor();

            float boardValue = 0;
            switch (p.getPieceType()) {
                case PAWN -> {
                    boardValue += PieceType.PAWN.getValue(fullmoves);
                    boardValue += color == PieceColor.WHITE ? whitePawnMap[x][y] : blackPawnMap[x][y];
                }
                case KNIGHT -> {
                    boardValue += PieceType.KNIGHT.getValue(fullmoves);
                    boardValue += color == PieceColor.WHITE ? whiteKnightMap[x][y] : blackKnightMap[x][y];
                }
                case KING -> {
                    boardValue += PieceType.KING.getValue(fullmoves);
                    boardValue += color == PieceColor.WHITE ? whiteKingMap[x][y] : blackKingMap[x][y];
                }
                case QUEEN -> {
                    boardValue += PieceType.QUEEN.getValue(fullmoves);
                    boardValue += color == PieceColor.WHITE ? whiteQueenMap[x][y] : blackQueenMap[x][y];
                }
                case ROOK -> {
                    boardValue += PieceType.ROOK.getValue(fullmoves);
                    boardValue += color == PieceColor.WHITE ? whiteRookMap[x][y] : blackRookMap[x][y];
                }
                case BISHOP -> {
                    boardValue += PieceType.BISHOP.getValue(fullmoves);
                    boardValue += color == PieceColor.WHITE ? whiteBishopMap[x][y] : blackBishopMap[x][y];
                }
            }

            sum += (color == PieceColor.WHITE ? boardValue : -boardValue);
        }
        //TODO: Points for bishop pair, more space, doubled rooks, alekhine gun, etc
        return sum;
    }

    public List<EvaluationResult> moves = new ArrayList<>();

    private EvaluationResult alphabeta(Board b, int depth, Evaluation alpha, Evaluation beta, boolean isMax) {
        switch (b.determineGameState()) {
            case ONGOING -> {
                if (depth == 0) {
                    return new EvaluationResult(new Evaluation(evaluate_single_board(b)), null);
                }

                if (isMax) {
                    EvaluationResult max = null;
                    for (Move m : b.allPossibleMoves()) {
                        b.playMove(m);
                        EvaluationResult evaluationResult = alphabeta(b, depth - 1, alpha, beta, false);
                        b.undoLastMove();
                        // if (depth == 3) {
                        //    moves.add(new EvaluationResult(evaluationResult.eval(), m));
                        //}
                        if (max == null || evaluationResult.eval().compareTo(max.eval()) > 0) {
                            max = new EvaluationResult(evaluationResult.eval(), m);
                        }
                        if (max.eval().compareTo(beta) >= 0) {
                            break;
                        }
                        if (alpha.compareTo(max.eval()) <= 0) {
                            alpha = max.eval();
                        }
                    }
                    return new EvaluationResult(max.eval().getOneMoveLater(), max.move());
                } else {
                    EvaluationResult min = null;
                    for (Move m : b.allPossibleMoves()) {
                        b.playMove(m);
                        EvaluationResult eval = alphabeta(b, depth - 1, alpha, beta, true);
                        b.undoLastMove();
                        //if (depth == 3) {
                        //    moves.add(new EvaluationResult(eval.eval(), m));
                        //}
                        if (min == null || eval.eval().compareTo(min.eval()) < 0) {
                            min = new EvaluationResult(eval.eval(), m);
                        }
                        if (min.eval().compareTo(alpha) <= 0) {
                            break;
                        }
                        if (beta.compareTo(min.eval()) >= 0) {
                            beta = min.eval();
                        }
                    }
                    return new EvaluationResult(min.eval().getOneMoveLater(), min.move());
                }
            }
            case WHITE_WINS -> {
                return new EvaluationResult(new Evaluation(PieceColor.WHITE), null);
            }
            case BLACK_WINS -> {
                return new EvaluationResult(new Evaluation(PieceColor.BLACK), null);
            }
            case STALEMATE, DRAW_BY_50_MOVE_RULE -> {
                return new EvaluationResult(new Evaluation(0.0f), null);
            }
            default -> throw new IllegalArgumentException();
        }
    }

}