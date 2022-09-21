package Main;

import Chessboard.FENHelper;
import Chessboard.Move;
import Evaluation.Evaluator;
import io.javalin.Javalin;

import java.nio.file.Files;
import java.nio.file.Path;

public class ChessAPI {
    private Evaluator e = new Evaluator();

    public void startBackend() {
        // http://localhost:8002/chess/eval?fen=1rb2rk1/p1qnbppp/B7/2ppP3/3P4/4QN2/1Pn2PPP/R1B2RK1 w - - 0 15
        Javalin app = Javalin.create().start(7001);
        app.get("/chess/eval", ctx -> {
            String fen = ctx.queryParam("fen");
            Move bestMove = e.determineBestMove(new FENHelper(fen).fenToBoard());
            String bestMoveString = bestMove == null ? "null" : bestMove.toString();
            ctx.status(200);
            ctx.result(bestMoveString);
        });

        app.get("/chess", ctx -> {
            String site = new String(Files.readAllBytes(Path.of("src/main/resources/index.html")));
            ctx.status(200);
            ctx.html(site);
        });
    }
}
