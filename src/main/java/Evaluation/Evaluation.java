package Evaluation;

import Enums.PieceColor;
import org.jetbrains.annotations.NotNull;

public class Evaluation implements Comparable<Evaluation> {
    private double eval;
    private PieceColor winner;
    private int mateIn;

    public Evaluation(double eval) {
        this.eval = eval;
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

        if (mateIn == 0 && otherEval.mateIn == 0) {
            return Double.compare(eval, otherEval.eval);
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

    public double getEval() {
        return eval;
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
        return Double.toString(eval);
    }
}
