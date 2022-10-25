package im.jok.jokimchess;

import im.jok.jokimchess.chessboard.*;
import im.jok.jokimchess.evaluation.Evaluation;
import im.jok.jokimchess.evaluation.EvaluationResult;
import im.jok.jokimchess.evaluation.Evaluator;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Main {

    //TODO: Game takes args 'fen' and 'depth', gives move back
    public static void main(String[] args) {
        //new ChessAPI().startBackend();
        //  Triggers server warning: 1r1qkb1r/ppp1nBpp/8/4PQP1/7P/2N5/PPPB4/2KR2NR b kq - -2 21

        Board b = new FENHelper("1k6/8/1K1R4/8/8/8/8/8 w - - 0 1").toBoard();
        Evaluator e = new Evaluator();
        e.determineBestEvaluationResult(b, 3);
        e.moves.sort(Comparator.comparing(EvaluationResult::eval));
        System.out.println(e.moves);
    }

    public void printmoves() {
        Board b = new FENHelper("1k6/8/1K1R4/8/8/8/8/8 w - - 0 1").toBoard();
        Evaluator e = new Evaluator();
        e.determineBestEvaluationResult(b, 3);
        e.moves.sort(Comparator.comparing(EvaluationResult::eval));
        System.out.println(e.moves);
    }

    //TODO Movecount check
    public void testMoveCount() {
        FENHelper f = new FENHelper();
        Board b = f.toBoard();
        HashMap<MoveType, Integer> map = new HashMap<>();
        int moveCount = 0;
        for (Move m : b.allPossibleMoves(true)) {
            b.playMove(m);
            for (Move m2 : b.allPossibleMoves(true)) {
                b.playMove(m2);
                for (Move m3 : b.allPossibleMoves(true)) {
                    moveCount += b.allPossibleMoves(true).size();
                    if (!map.containsKey(m3.moveType())) {
                        map.put(m3.moveType(), 1);
                    } else {
                        map.put(m3.moveType(), map.get(m3.moveType()) + 1);
                    }
                }
                b.undoLastMove();
            }
            b.undoLastMove();
        }

        System.out.println(moveCount);
        System.out.println(map);
    }

    // TODO King check fix
    public static void kingCheckCheck() {
        FENHelper f5 = new FENHelper("rnbqkbnr/2pppppp/5N2/8/pp1P4/8/PPP1PPPP/RNBQKB1R b KQkq - 1 5");
        Board b5 = f5.toBoard();
        System.out.println(b5.toString());
        System.out.println(b5.isKingInCheck(true));
    }

    // TODO King castle out of danger
    public void fixKingCastleOutOfDanger() {
        FENHelper f5 = new FENHelper("rnbQk2r/3p1pp1/2p1p2p/3Pn3/p7/R6P/1PP1PPP1/1N2KB1R b Kkq - 0 13");
        Board b5 = f5.toBoard();
        System.out.println(b5.toString());
        System.out.println(b5.allPossibleMoves(true));
    }
}
