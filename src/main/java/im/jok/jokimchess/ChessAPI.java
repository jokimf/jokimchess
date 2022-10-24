package im.jok.jokimchess;

import im.jok.jokimchess.chessboard.Board;
import im.jok.jokimchess.chessboard.FENHelper;
import im.jok.jokimchess.chessboard.Move;
import im.jok.jokimchess.chessboard.Piece;
import im.jok.jokimchess.chessboard.MoveType;
import im.jok.jokimchess.chessboard.PieceColor;
import im.jok.jokimchess.chessboard.PieceType;
import im.jok.jokimchess.evaluation.EvaluationResult;
import im.jok.jokimchess.evaluation.Evaluator;
import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessAPI {
    private static final Evaluator e = new Evaluator();
    private static final int DEPTH = 3;

    public void startBackend() {
        // http://localhost:7001/chess/eval?fen=1rb2rk1/p1qnbppp/B7/2ppP3/3P4/4QN2/1Pn2PPP/R1B2RK1 w - - 0 15
        Javalin app = Javalin.create(
                javalinConfig -> javalinConfig.plugins.enableCors(corsContainer -> corsContainer.add(CorsPluginConfig::anyHost))).start(7001);
        app.get("/chess", ctx -> {
            ctx.status(200);
            ctx.html(new String(Files.readAllBytes(Path.of("src/main/resources/index.html"))));
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

        app.get("/chess/bot", ctx -> {
            Board b = new FENHelper(ctx.queryParam("fen")).toBoard();
            EvaluationResult bestEvaluationResult = e.determineBestEvaluationResult(b, DEPTH);
            Move bestMove = bestEvaluationResult.move();
            b.playMove(bestMove);

            Map<String, Object> json = new HashMap<>();
            json.put("fen", new FENHelper().boardToFen(b));
            json.put("move", bestMove);
            json.put("eval", bestEvaluationResult.eval());
            ctx.status(200);
            ctx.json(json);
        });
    }
}