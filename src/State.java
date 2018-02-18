import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class State {

    private enum  Results { loss, win, unknown }

    private String player;
    private Results result = Results.unknown;
    private Environment environment;
    private Action action;


    public State () {
        environment = new Environment();
    }

    public State (Environment environment, String player) {
        this.environment = environment;
        this.player = player;
    }

    public List<Action> getLegalActions() {
        List<Action> actions = new ArrayList<>();

        if(whiteTurn()) {
            Set<Position> W_positions = environment.getWhitePositions();

            for (Position pos : W_positions) {
                if (!environment.positionContainsBlack(new Position(pos.x, pos.y + 1))
                        && !environment.positionContainsWhite(new Position(pos.x, pos.y +1))
                        && !environment.whiteAtEnd(pos)
                        ) {
                    actions.add(
                            new Action( pos, new Position(pos.x, pos.y +1), false)
                    );
                }

                if (environment.whiteCanCaptureBlackLeft(pos)) {
                    actions.add(
                            new Action( pos, new Position(pos.x - 1, pos.y + 1), true)
                    );
                }

                if (environment.whiteCanCaptureBlackRight(pos)) {
                    actions.add(
                            new Action( pos, new Position(pos.x + 1, pos.y + 1), true)
                    );
                }
            }
        }
        // else it is blacks turn
        else
        {
            Set<Position> B_positions = environment.getBlackPositions();

            for (Position pos : B_positions) {
                if (!environment.positionContainsWhite(new Position(pos.x, pos.y - 1))
                        && !environment.positionContainsBlack(new Position(pos.x, pos.y - 1))
                        && !environment.blackAtEnd(pos)
                        ) {
                    actions.add(
                            new Action( pos, new Position(pos.x, pos.y - 1), false)
                    );
                }

                if (environment.blackCanCaptureWhiteLeft(pos)) {
                    actions.add(
                            new Action( pos, new Position(pos.x - 1, pos.y - 1), true)
                    );
                }

                if (environment.blackCanCaptureWhiteRight(pos)) {
                    actions.add(
                            new Action( pos, new Position(pos.x + 1, pos.y - 1), true)
                    );
                }
            }
        }

        if (actions == null) { result = Results.loss; }

        return actions;
    }

    public void makeAction (Action action) {
        this.environment.make_move(action.from.x, action.from.y, action.to.x, action.to.y, player);
    }

    public void makeAction (Action action, String player) {
        this.environment.make_move(action.from.x, action.from.y, action.to.x, action.to.y, player);
    }

    // was needed for the agent since environment gets updated after making move
    public void makeLastAction (Action action) {
        String lastPlayer = "";
        if (player.equals("white")) {
            lastPlayer.equals("black");
        } else {
            lastPlayer.equals("white");
        }

        this.environment.make_move(action.from.x, action.from.y, action.to.x, action.to.y, lastPlayer);
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    // Private functions
    private boolean whiteTurn() {
        return this.player.equals("white");
    }

    private boolean blackTurn() {
        return this.player.equals("white");
    }


}
