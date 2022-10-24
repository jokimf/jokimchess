package im.jok.jokimchess;

import im.jok.jokimchess.chessboard.Board;
import im.jok.jokimchess.chessboard.FENHelper;
import im.jok.jokimchess.chessboard.Move;
import im.jok.jokimchess.chessboard.MoveType;
import im.jok.jokimchess.evaluation.Evaluator;

import java.util.HashMap;

public class Main {

    //TODO: Game takes args 'fen' and 'depth', gives move back
    public static void main(String[] args) {
        new ChessAPI().startBackend();
        Board b = new FENHelper("r5nk/p7/Pp1p3b/1B2p1pQ/1P2P2P/5p1q/1BP2P2/R4RK1 w - - 0 29").toBoard();
        System.out.println(new Evaluator().evaluate_single_board(b));
        //  Triggers server warning: 1r1qkb1r/ppp1nBpp/8/4PQP1/7P/2N5/PPPB4/2KR2NR b kq - -2 21

//        FENHelper f5 = new FENHelper();
//        Board b5 = f5.fenToBoard();
//        Evaluator e = new Evaluator();
//        EvaluationResult result = e.minimax(b5, 2, true);
//        System.out.println(result.getMove().toString());
//        System.out.println(result.getEval());
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
    public void kingCheckCheck() {
        FENHelper f5 = new FENHelper("rnbqkbnr/2pppppp/5N2/8/pp1P4/8/PPP1PPPP/RNBQKB1R b KQkq - 1 5");
        Board b5 = f5.toBoard();
        System.out.println(b5.toString());
        System.out.println(b5.isKingInCheck());
    }

    // TODO King castle out of danger
    public void fixKingCastleOutOfDanger() {
        FENHelper f5 = new FENHelper("rnbQk2r/3p1pp1/2p1p2p/3Pn3/p7/R6P/1PP1PPP1/1N2KB1R b Kkq - 0 13");
        Board b5 = f5.toBoard();
        System.out.println(b5.toString());
        System.out.println(b5.allPossibleMoves(true));
    }
}
