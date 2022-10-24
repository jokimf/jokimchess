package im.jok.jokimchess.evaluation;

import im.jok.jokimchess.chessboard.PieceColor;
import org.jetbrains.annotations.NotNull;

public class Evaluation implements Comparable<Evaluation> {
    private float evalNumber;
    private PieceColor winner;
    private int mateIn;

    public Evaluation(float evalNumber) {
        this.evalNumber = evalNumber;
    }

    public Evaluation(int mateIn) {
        this.mateIn = mateIn;
    }

    public Evaluation(PieceColor winner) {
        this.winner = winner;
    }

    public Evaluation getOneMoveLater() {
        if (winner == PieceColor.WHITE) {
            return new Evaluation(1);
        }
        if (winner == PieceColor.BLACK) {
            return new Evaluation(-1);
        }
        if (mateIn < 0) {
            return new Evaluation(mateIn - 1);
        }
        if (mateIn > 0) {
            return new Evaluation(mateIn + 1);
        }
        return this;
    }

    @Override
    public int compareTo(@NotNull Evaluation otherEval) {
        if (mateIn == 0 && otherEval.mateIn == 0) {
            return Float.compare(evalNumber, otherEval.evalNumber);
        }
        if (winner == PieceColor.WHITE) {
            if (otherEval.winner == PieceColor.WHITE) {
                return 0;
            } else {
                return 1;
            }
        }

        if (winner == PieceColor.BLACK) {
            if (otherEval.winner == PieceColor.BLACK) {
                return 0;
            } else {
                return -1;
            }
        }

        if (otherEval.winner == PieceColor.WHITE) {
            return -1;
        }
        if (otherEval.winner == PieceColor.BLACK) {
            return 1;
        }
        if (mateIn > 0 && otherEval.mateIn > 0) {
            if (mateIn > otherEval.mateIn) {
                return -1;
            }
            if (mateIn < otherEval.mateIn) {
                return 1;
            }
            if (mateIn == otherEval.mateIn) {
                return 0;
            }
        }

        if (mateIn < 0 && otherEval.mateIn < 0) {
            if (mateIn > otherEval.mateIn) {
                return 1;
            }
            if (mateIn < otherEval.mateIn) {
                return -1;
            }
            if (mateIn == otherEval.mateIn) {
                return 0;
            }
        }

        return Integer.compare(mateIn, otherEval.mateIn);
    }

    public float getEvalNumber() {
        return evalNumber;
    }

    public PieceColor getWinner() {
        return winner;
    }

    public int getMateIn() {
        return mateIn;
    }


    public String toString() {
        if (winner != null) {
            return winner.name();
        }
        if (mateIn != 0) {
            return "Mate in: " + Math.abs(mateIn);
        }
        return Float.toString(evalNumber);
    }
}
