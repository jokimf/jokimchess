package im.jok.jokimchess;

import im.jok.jokimchess.chessboard.Board;
import im.jok.jokimchess.chessboard.FENHelper;
import im.jok.jokimchess.chessboard.Move;
import im.jok.jokimchess.chessboard.MoveType;

import java.util.HashMap;

public class Main {
    //r1b1k2r/1ppp2pp/1n2p3/p3Ppq1/1bPN4/2N5/PPQ1BPPP/R4RK1 b kq - -2 11
    // 1k6/8/1K1R4/8/8/8/8/8 w - - 0 1 Simple M1
    //TODO: Game takes args 'fen' and 'depth', gives move back
    public static void main(String[] args) {
        //new ChessAPI().startBackend();
        Board b = new FENHelper("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8").toBoard();
        b.playMove(b.getRandomMove());
        System.out.println(b);
        b.playMove(b.getRandomMove());
        System.out.println(b);
        b.playMove(b.getRandomMove());
        System.out.println(b);
        b.playMove(b.getRandomMove());
        System.out.println(b);
        b.playMove(b.getRandomMove());
        System.out.println(b);
    }

    //TODO Movecount check
    public void testMoveCount() {
        FENHelper f = new FENHelper();
        Board b = f.toBoard();
        HashMap<MoveType, Integer> map = new HashMap<>();
        int moveCount = 0;
        for (Move m : b.allPossibleMoves()) {
            b.playMove(m);
            for (Move m2 : b.allPossibleMoves()) {
                b.playMove(m2);
                for (Move m3 : b.allPossibleMoves()) {
                    moveCount += b.allPossibleMoves().size();
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

    // TODO King castle out of danger
    public void fixKingCastleOutOfDanger() {
        FENHelper f5 = new FENHelper("rnbQk2r/3p1pp1/2p1p2p/3Pn3/p7/R6P/1PP1PPP1/1N2KB1R b Kkq - 0 13");
        Board b5 = f5.toBoard();
        System.out.println(b5.toString());
        System.out.println(b5.allPossibleMoves());
    }
}
