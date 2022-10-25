package im.jok.jokimchess.evaluation;

import im.jok.jokimchess.chessboard.Move;

public record EvaluationResult(Evaluation eval, Move move) {

    @Override
    public String toString() {
        return move + ": " + eval + "\n";
    }
}