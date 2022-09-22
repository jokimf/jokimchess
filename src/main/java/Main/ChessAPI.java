package Main;

import Chessboard.Board;
import Chessboard.FENHelper;
import Chessboard.Move;
import Evaluation.Evaluator;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ChessAPI {
    private Evaluator e = new Evaluator();

    public void startBackend() {
        // http://localhost:8002/chess/eval?fen=1rb2rk1/p1qnbppp/B7/2ppP3/3P4/4QN2/1Pn2PPP/R1B2RK1 w - - 0 15
        Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins).start(7001);
        app.get("/chess", ctx -> {
            String site = new String(Files.readAllBytes(Path.of("src/main/resources/index.html")));
            ctx.status(200);
            ctx.html(site);
        });

        app.get("/chess/eval", ctx -> {
            String fen = ctx.queryParam("fen");
            Move bestMove = e.determineBestMove(new FENHelper(fen).toBoard());
            String bestMoveString = bestMove == null ? "null" : bestMove.toString();
            ctx.status(200);
            ctx.result(bestMoveString);
        });

        app.get("/chess/availableMoves", ctx -> {
            String fen = ctx.queryParam("fen");
            List<String> b = new ArrayList<>();
            new FENHelper(fen).toBoard().allPossibleMoves(true).forEach(move -> b.add(move.simpleToString()));
            ctx.status(200);
            ctx.json(b);
        });

        app.get("/chess/newFen", ctx -> {
            String fen = ctx.queryParam("fen");
            String move = ctx.queryParam("move");
            FENHelper f = new FENHelper(fen);
            Board b = f.toBoard();
            b.playMove(new Move(move));
            ctx.status(200);
            ctx.json(f.boardToFen(b));
        });
    }
}
/*
Nachts, im Mondschein lag auf einem Blatt ein kleines Ei.
Un als an einem schönen Sonntagmorgen die SOnne aufging, hell und warm, da schlüpfte aus dem Ei - knack - eine kleine, hungrige Raupe.
Sie machte sich auf den Weg, um Futter zu suchen.

Am Montag fraß sie sich durch einen Apfel, aber satt war sie noch immer nicht.
Am Dienstag fraß sie sich durch zwei Birnen, aber satt war sie noch immer nicht.
Am Mittwoch fraß sie sich durch drei Pflaumen, aber satt war sie noch immer nicht.
Am Donnerstag fraß sie sich durch vier Erdbeeren, aber satt war sie noch immer nicht
Am Freitag fraß sie sich durch fünf Apfelsinen, aber satt war sie noch immer nicht.
Am Sonnabend fraß sie sich durch ein Stück Schokoladenkuchen, eine Eiswaffel, eine saure Gurke, eine Scheibe Käse,
ein Stück Wurst, einen Lolli, ein Stück Früchtebrot, ein Würstchen, ein Törtchen, und ein Stück Melone. An diesem Abend hatte sie Bauchschmerzen!

Der nächste Tag war wieder ein Sonntag. Die Raupe fraß sich durch ein grünes Blatt. Es ging ihr nun viel besser.
Sie war nicht mehr hungrig, sie war richtig satt. Und sie war auch nicht mehr klein, sie war groß und dick geworden.
Sie baute sich ein enges Haus, dass man Kokon nennt, und blieb darin mehr als zwei Wochen lang.
Dann knabberte sie sich ein Loch in den Kokon, zwängte sich nach draußen und ...

war ein wunderschöner Schmetterling!
*/