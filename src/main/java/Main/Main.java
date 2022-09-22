package Main;

import Chessboard.Board;
import Chessboard.FENHelper;
import Chessboard.Move;
import Enums.MoveType;

import java.io.IOException;
import java.util.HashMap;

public class Main {

    //TODO: Game takes args 'fen' and 'depth', gives move back

    public static void main(String[] args) throws IOException {
        new ChessAPI().startBackend();
//        FENHelper f5 = new FENHelper();
//        Board b5 = f5.fenToBoard();
//        Evaluator e = new Evaluator();
//        EvaluationResult result = e.minimax(b5, 2, true);
//        System.out.println(result.getMove().toString());
//        System.out.println(result.getEval());
    }


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
                    if (!map.containsKey(m3.getMoveType())) {
                        map.put(m3.getMoveType(), 1);
                    } else {
                        map.put(m3.getMoveType(), map.get(m3.getMoveType()) + 1);
                    }
                }
                b.undoLastMove();
            }
            b.undoLastMove();
        }

        System.out.println(moveCount);
        System.out.println(map.toString());
    }

    public void play50RandomMoves() {
        FENHelper f = new FENHelper();
        Board b = f.toBoard();
        for (int i = 0; i < 50; i++) {
            Move m = b.getRandomMove();
            System.out.println(m.toString() + " " + m.getMoveType() + " " + m.getPieceMoved().toString() + " " + m.getPieceTaken());
            b.playMove(m);
        }
        System.out.println(f.boardToFen(b));
    }

    public void kingCheckCheck() {
        FENHelper f5 = new FENHelper("rnbqkbnr/2pppppp/5N2/8/pp1P4/8/PPP1PPPP/RNBQKB1R b KQkq - 1 5");
        Board b5 = f5.toBoard();
        System.out.println(b5.toString());
        System.out.println(b5.isKingInCheck());
    }

    public void fixKingCastleOutOfDanger() {
        FENHelper f5 = new FENHelper("rnbQk2r/3p1pp1/2p1p2p/3Pn3/p7/R6P/1PP1PPP1/1N2KB1R b Kkq - 0 13");
        Board b5 = f5.toBoard();
        System.out.println(b5.toString());
        System.out.println(b5.allPossibleMoves(true));
    }
}
