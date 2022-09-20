package Evaluation;

import Chessboard.Board;
import Chessboard.Piece;
import Enums.GameState;
import Enums.PieceColor;

public class Evaluator {
    public double evaluate(Board b) {
        double sum = 0;
        int fullmoves = b.getFullmove();
        for (Piece p : b.getPiecesOnTheBoard()) {
            int x = p.getX(), y = p.getY();
            double value = 0;
            switch (p.getPieceType()) {
                case BISHOP:
                    value = 3;
                case KNIGHT:
                    value = 3;
                    //Knight is in the middle squares
                    if (x > 2 || x < 6 && y > 2 || y < 6) {
                        value += 0.5;
                    }
                    break;
                case KING:
                    value = 100;
                    if (fullmoves < 30) {
                        if (x < 1 || x > 6) {
                            value += 1;
                        } else {
                            value -= 1;
                        }
                    }
                    break;
                case PAWN:
                    value = 1;
                    if (x < 2 || x > 6 && y < 2 || y > 6) {
                        value += 0.5;
                    }

                    if (fullmoves < 30) {
                        value += 0.3;
                    }
                    break;
                case QUEEN:
                    value = 9;
                    if (fullmoves < 30) {
                        value += 2;
                    }
                    break;
                case ROOK:
                    value = 5;
                    break;
            }
            if (p.getPieceColor() == Enums.PieceColor.BLACK) {
                value = -value;
            }
            sum += value;
        }
        return sum;
    }

    public EvaluationResult minimax(Board b, int depth, boolean isMax) {
        GameState g = b.determineGameState();
        if (g == GameState.WHITE_WINS) {
            return new EvaluationResult(new Evaluation(PieceColor.WHITE), null);
        } else if (g == GameState.BLACK_WINS) {
            return new EvaluationResult(new Evaluation(PieceColor.BLACK), null);
        } else if (g == GameState.STALEMATE || g == GameState.DRAW_BY_50_MOVE_RULE) {
            return new EvaluationResult(new Evaluation(0.0), null);
        }

        // Recursion Base Case
        if (depth == 0) {
            return new EvaluationResult(new Evaluation(evaluate(b)), null);
        }

        if (isMax) {
            EvaluationResult max = null;
            for (Chessboard.Move m : b.allPossibleMoves(true)) {
                b.playMove(m);
                EvaluationResult eval = minimax(b, depth - 1, false);
                b.undoLastMove();
                if (max == null || eval.getEval().compareTo(max.getEval()) > 0) {
                    max = new EvaluationResult(eval.getEval(), m);
                }
            }
            return new EvaluationResult(max.getEval().getOneMoveLater(), max.getMove());
        } else {
            EvaluationResult min = null;
            for (Chessboard.Move m : b.allPossibleMoves(true)) {
                b.playMove(m);
                EvaluationResult eval = minimax(b, depth - 1, true);
                b.undoLastMove();
                if (min == null || eval.getEval().compareTo(min.getEval()) < 0) {
                    min = new EvaluationResult(eval.getEval(), m);
                }
            }
            return new EvaluationResult(min.getEval().getOneMoveLater(), min.getMove());
        }
    }

}