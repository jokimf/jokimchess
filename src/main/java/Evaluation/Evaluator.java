package Evaluation;

public class Evaluator {
/*
    public double evaluate(Chessboard.Board b) {
        double sum = 0;
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                Chessboard.Piece p = b.getPiece(x, y);
                double value = 0;
                switch (p.getPieceType()) {
                    case BISHOP:
                        value = 3;
                        break;
                    case KING:
                        value = 999;
                        break;
                    case KNIGHT:
                        value = 3;
                        break;
                    case PAWN:
                        value = 1;
                        break;
                    case QUEEN:
                        value = 9;
                        break;
                    case ROOK:
                        value = 5;
                        break;
                    default:
                        break;
                }
                if (p.getPieceColor() == Enums.PieceColor.BLACK) {
                    value = -value;
                }
                sum += value;
            }
        }
        return sum;
    }

    public Evaluation.EvaluationResult minimax(Chessboard.Board b, int depth, boolean isMax) {
        Enums.GameState g = b.determineGameState();
        if (g == Enums.GameState.WHITE_WINS) {
            return new Evaluation.EvaluationResult(new Evaluation.Evaluation(Enums.PieceColor.WHITE), null);
        } else if (g == Enums.GameState.BLACK_WINS) {
            return new Evaluation.EvaluationResult(new Evaluation.Evaluation(Enums.PieceColor.BLACK), null);
        } else if (g == Enums.GameState.STALEMATE || g == Enums.GameState.DRAW_BY_50_MOVE_RULE) {
            return new Evaluation.EvaluationResult(new Evaluation.Evaluation(0.0), null);
        }

        if (depth == 0) {
            return new Evaluation.EvaluationResult(new Evaluation.Evaluation(evaluate(b)), null);
        }

        if (isMax) {
            Evaluation.EvaluationResult max = null;
            for (Chessboard.Move m : b.allPossibleMoves(true)) {
                b.playMove(m);
                Evaluation.EvaluationResult eval = minimax(b, depth - 1, false);
                b.undoLastMove();
                if (max == null || eval.getEval().compareTo(max.getEval()) > 0) {
                    max = new Evaluation.EvaluationResult(eval.getEval(), m);
                }
            }
            return new Evaluation.EvaluationResult(max.getEval().getOneMoveLater(), max.getMove());
        } else {
            Evaluation.EvaluationResult min = null;
            for (Chessboard.Move m : b.allPossibleMoves(true)) {
                b.playMove(m);
                Evaluation.EvaluationResult eval = minimax(b, depth - 1, true);
                b.undoLastMove();
                if (min == null || eval.getEval().compareTo(min.getEval()) < 0) {
                    min = new Evaluation.EvaluationResult(eval.getEval(), m);
                }
            }
            return new Evaluation.EvaluationResult(min.getEval().getOneMoveLater(), min.getMove());
        }
    }

 */
}