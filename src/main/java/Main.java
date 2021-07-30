import Chessboard.Board;
import Chessboard.FENHelper;
import Chessboard.Move;
import Enums.MoveType;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        FENHelper f = new FENHelper();
        Board b = f.fenToBoard();
    }

    public void testMoveCount() {
        FENHelper f = new FENHelper();
        Board b = f.fenToBoard();
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
}
