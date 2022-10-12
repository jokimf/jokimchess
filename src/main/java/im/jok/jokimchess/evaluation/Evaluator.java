package im.jok.jokimchess.evaluation;

import im.jok.jokimchess.chessboard.Board;
import im.jok.jokimchess.chessboard.Move;
import im.jok.jokimchess.chessboard.Piece;
import im.jok.jokimchess.chessboard.GameState;
import im.jok.jokimchess.chessboard.PieceColor;

public class Evaluator {

    public Move determineBestMove(Board b) {
        EvaluationResult e = alphabeta(b, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, b.isTurnWhite());
        return e.move();
    }

    public double evaluate_single_board(Board b) {
        double sum = 0;
        int fullmoves = b.getFullmove();

        // Consider all pieces on the board
        for (Piece p : b.getPiecesOnTheBoard()) {

            int x = p.getX(), y = p.getY();

            double value = 0;
            switch (p.getPieceType()) {
                case BISHOP -> value = 3;
                case KNIGHT -> {
                    value = 3;
                    //Knight is in the middle squares
                    if (x >= 3 && x <= 5 && y >= 3 && y <= 5) {
                        value += 0.5;
                    }
                }
                case KING -> {
                    value = 10;
                    if (fullmoves < 30) {
                        if (x < 1 || x > 6) {
                            value += 1;
                        } else {
                            value -= 1;
                        }
                    }
                }
                case PAWN -> {
                    value = 1;
                    if (x < 2 || x > 6 && y < 2 || y > 6) {
                        value += 0.5;
                    }
                    if (fullmoves < 30) {
                        value += 0.3;
                    }
                }
                case QUEEN -> {
                    value = 9;
                    if (fullmoves < 30) {
                        value += 2;
                    }
                }
                case ROOK -> value = 5;
            }

            // Add or subtract from total value depending on PieceColor
            if (p.getPieceColor() == PieceColor.BLACK) {
                value = -value;
            }
            sum += value;
        }
        return sum;
    }

    public EvaluationResult alphabeta(Board b, int depth, double alpha, double beta, boolean isMax) {
        GameState g = b.determineGameState();
        switch (g) {
            case ONGOING -> {
                // Recursion Base Case
                if (depth == 0) {
                    return new EvaluationResult(new Evaluation(evaluate_single_board(b)), null);
                }

                if (isMax) {
                    EvaluationResult max = null;
                    for (Move m : b.allPossibleMoves(true)) {
                        b.playMove(m);
                        EvaluationResult eval = alphabeta(b, depth - 1, alpha, beta, false);
                        b.undoLastMove();
                        if (max == null || eval.eval().compareTo(max.eval()) > 0) {
                            max = new EvaluationResult(eval.eval(), m);
                        }
                        if (max.eval().getEval() >= beta) { // TODO: Use compareTo
                            break;
                        }
                        alpha = Math.max(alpha, max.eval().getEval());
                    }
                    return new EvaluationResult(max.eval().getOneMoveLater(), max.move());
                } else {
                    EvaluationResult min = null;
                    for (Move m : b.allPossibleMoves(true)) {
                        b.playMove(m);
                        EvaluationResult eval = alphabeta(b, depth - 1, alpha, beta, true);
                        b.undoLastMove();
                        if (min == null || eval.eval().compareTo(min.eval()) < 0) {
                            min = new EvaluationResult(eval.eval(), m);
                        }
                        if (min.eval().getEval() <= alpha) { // TODO: Use compareTo
                            break;
                        }
                        beta = Math.max(beta, min.eval().getEval());
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
                return new EvaluationResult(new Evaluation(0.0), null);
            }
            default -> throw new IllegalArgumentException();
        }
    }

}