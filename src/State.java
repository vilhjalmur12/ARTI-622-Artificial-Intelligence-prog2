import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class State {

    private enum  Results { loss, win, unknown, draw }

    private String player;
    private Results result;
    private Environment environment;
    private Action action;
    private List<Action> myLegalActions;
    private List<Action> enemyLegalActions;


    public State () {
        environment = new Environment();
    }

    public State (Environment environment, String player) {
        this.environment = environment;
        this.player = player;
        this.myLegalActions = getLegalActions();
        this.switchPlayer();
        this.enemyLegalActions = getLegalActions();
        this.switchPlayer();

        evaluateWin();
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


    public Environment getEnvironment() {
        return this.environment;
    }

    // Private functions
    private boolean whiteTurn() {
        return this.player.equals("white");
    }

    private boolean blackTurn() {
        return this.player.equals("black");
    }

    public void switchPlayer() {
        if (player.equals("white")) {
            this.player = "black";
        } else {
            this.player = "white";
        }
    }

    public String getPlayer () {
        return this.player;
    }

    public boolean gameOver() {
        return this.result != Results.unknown;
    }

    private void evaluateWin() {
        if (this.enemyLegalActions.isEmpty() && this.myLegalActions.isEmpty())
        {
            this.result = Results.draw;
        }
        else if (this.myLegalActions.isEmpty())
        {
            this.result = Results.loss;
        }
        else if (this.enemyLegalActions.isEmpty())
        {
            this.result = Results.win;
        }
        else {
            this.result = Results.unknown;
        }

    }

    public boolean playerIsWinner() {
        return this.result == Results.win;
    }

    public boolean playerIsLoser() {
        return this.result == Results.loss;
    }

    public boolean isDraw() {
        return this.result == Results.draw;
    }

    public int myPlayerCount() {
        if (player.equals("white")) {
            return this.environment.getWhitePositions().size();
        }
        else
        {
            return this.environment.getBlackPositions().size();
        }
    }

    public int enemyPlayerCount() {
        if (player.equals("black")) {
            return this.environment.getWhitePositions().size();
        }
        else
        {
            return this.environment.getBlackPositions().size();
        }
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        if (player.equals("white")) {
            for (Position pos : environment.getWhitePositions()) {
                result += pos.hashCode() * 3;
            }
        } else {
            for (Position pos : environment.getBlackPositions()) {
                result += pos.hashCode() * 3;
            }
        }
        result = prime * result;

        return result;
    }
}
