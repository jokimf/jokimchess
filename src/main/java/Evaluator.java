public class Evaluator {
/*
    public double evaluate(Board b) {
        double sum = 0;
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                Piece p = b.getPiece(x, y);
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
                if (p.getPieceColor() == PieceColor.BLACK) {
                    value = -value;
                }
                sum += value;
            }
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

        if (depth == 0) {
            return new EvaluationResult(new Evaluation(evaluate(b)), null);
        }

        if (isMax) {
            EvaluationResult max = null;
            for (Move m : b.allPossibleMoves(true)) {
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
            for (Move m : b.allPossibleMoves(true)) {
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

 */
}