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

    public Evaluation oneMoveEarlier() {
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

    public int compareTo(@NotNull Evaluation other) {
        if (winner != null || other.winner != null) {
            if (winner == other.winner) {
                return 0;
            } else if (winner == PieceColor.WHITE) {
                return 1;
            } else if (winner == PieceColor.BLACK) {
                return -1;
            } else if (other.winner == PieceColor.WHITE) {
                return -1;
            } else if (other.winner == PieceColor.BLACK) {
                return 1;
            }
        } else if (mateIn != 0 || other.mateIn != 0) {
            if (mateIn > 0 && other.mateIn > 0 || mateIn < 0 && other.mateIn < 0) {
                return Integer.compare(other.mateIn, mateIn);
            }
            return Integer.compare(mateIn, other.mateIn);
        }
        return Float.compare(evalNumber, other.evalNumber);
    }

    public String toString() {
        if (winner != null) {
            return winner.name();
        }
        if (mateIn != 0) {
            return "M" + Math.abs(mateIn);
        }
        return Float.toString(evalNumber);
    }
}
