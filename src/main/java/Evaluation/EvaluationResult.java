package Evaluation;

import Chessboard.Move;

public class EvaluationResult {
    //TODO: can be a record?
    private Evaluation eval;
    private Move move;

    public EvaluationResult(Evaluation eval, Move move) {
        this.eval = eval;
        this.move = move;
    }

    public Evaluation getEval() {
        return eval;
    }

    public Move getMove() {
        return move;
    }


}
