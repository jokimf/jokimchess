package Main;

import Chessboard.Board;
import Chessboard.FENHelper;
import Chessboard.Move;
import Chessboard.Piece;
import Enums.MoveType;
import Enums.PieceColor;
import Enums.PieceType;
import Evaluation.Evaluator;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ChessAPI {
    private static final Evaluator e = new Evaluator();

    public void startBackend() {
        // http://localhost:8002/chess/eval?fen=1rb2rk1/p1qnbppp/B7/2ppP3/3P4/4QN2/1Pn2PPP/R1B2RK1 w - - 0 15
        Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins).start(7001);
        app.get("/chess", ctx -> {
            String site = new String(Files.readAllBytes(Path.of("src/main/resources/index.html")));
            ctx.status(200);
            ctx.html(site);
        });

        app.get("/chess/moves", ctx -> {
            String fen = ctx.queryParam("fen");
            List<Move> moves = new FENHelper(fen).toBoard().allPossibleMoves(true);
            ctx.status(200);
            ctx.json(moves);
        });

        app.get("/chess/newFen", ctx -> {
            String fen = ctx.queryParam("fen"), moveType = ctx.queryParam("moveType"), pieceColor = ctx.queryParam("pieceColor"), pieceType = ctx.queryParam("pieceType");
            int startX = Integer.parseInt(ctx.queryParam("startX")), startY = Integer.parseInt(ctx.queryParam("startY"));
            int targetX = Integer.parseInt(ctx.queryParam("targetX")), targetY = Integer.parseInt(ctx.queryParam("targetY"));
            Move movePlayed =
                    new Move(startX, startY, targetX, targetY, new Piece(targetX, targetY, PieceType.valueOf(pieceType), PieceColor.valueOf(pieceColor)), null, MoveType.valueOf(moveType));
            FENHelper f = new FENHelper(fen);
            Board b = f.toBoard();
            b.playMove(movePlayed);
            ctx.status(200);
            ctx.json(f.boardToFen(b));
        });

        // Not important for frontend
        app.get("/chess/eval", ctx -> {
            String fen = ctx.queryParam("fen");
            Move bestMove = e.determineBestMove(new FENHelper(fen).toBoard());
            String bestMoveString = bestMove == null ? "null" : bestMove.toString();
            ctx.status(200);
            ctx.result(bestMoveString);
        });
    }
}