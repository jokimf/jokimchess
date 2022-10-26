package im.jok.jokimchess;

import im.jok.jokimchess.chessboard.*;
import im.jok.jokimchess.evaluation.EvaluationResult;
import im.jok.jokimchess.evaluation.Evaluator;
import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessAPI {
    private static final Evaluator e = new Evaluator();
    private static final int DEPTH = 4;

    public void startBackend() {
        Javalin app = Javalin.create(javalinConfig -> javalinConfig.plugins.enableCors(corsContainer -> corsContainer.add(CorsPluginConfig::anyHost))).start(7001);

        app.get("/moves", ctx -> { // Get all possible moves for specific FEN
            String fen = ctx.queryParam("fen");
            List<Move> moves = new FENHelper(fen).toBoard().allPossibleMoves();
            ctx.status(200);
            ctx.json(moves);
        });

        app.get("/newFen", ctx -> { // Return new FEN after playing move
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

        app.get("/bot", ctx -> { // Get 'best' move for FEN
            Board b = new FENHelper(ctx.queryParam("fen")).toBoard();
            EvaluationResult bestEvaluationResult = e.determineBestEvaluationResult(b, DEPTH);
            Move bestMove = bestEvaluationResult.move();
            b.playMove(bestMove);

            Map<String, Object> json = new HashMap<>();
            json.put("fen", new FENHelper().boardToFen(b));
            json.put("move", bestMove);
            json.put("eval", bestEvaluationResult.eval().toString());
            ctx.status(200);
            ctx.json(json);
        });
    }
}