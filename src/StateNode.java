import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StateNode {

    private StateNode parent;
    public State state;
    //private int depth;
    public List<StateNode> children;
    private Action action;
    private int score;
    private boolean isMax;

    public StateNode () {

    }

    public StateNode (StateNode parent, State state, Action action, boolean isMax) {
        this.parent = parent;
        this.state = state;
        this.action = action;
        this.children = new ArrayList<>();
        this.score = evaluateScore();
        this.isMax = isMax;
    }

    public void makeChildNodes () {

        this.children = new ArrayList<>();

        List<Action> legalActions = state.getLegalActions();

        Environment tmpEnv;
        State tmpState;

        for (Action action : legalActions) {
            tmpEnv = new Environment(
                    this.state.getEnvironment().getBoard_width(), this.state.getEnvironment().getBoard_height(),
                    new HashSet<Position>(this.state.getEnvironment().getWhitePositions()),
                    new HashSet<Position>(this.state.getEnvironment().getBlackPositions())
            );
            tmpState = new State(tmpEnv, this.state.getPlayer());
            tmpState.makeAction(action);
            tmpState.switchPlayer();
            children.add(new StateNode(this ,tmpState, action, !this.isMax));
        }
    }

    private int evaluateScore() {
        int score = 0;
        if (this.state.isDraw()) score += 50;
        if (this.state.playerIsLoser()) score += -100;
        if (this.state.playerIsWinner()) score += 100;

        int myPlayers = this.state.myPlayerCount();
        int enemyPlayers = this.state.enemyPlayerCount();

        return score + (myPlayers - enemyPlayers);
    }

    public void updateScoreFromChildren() {
        for (StateNode node : children) {
            this.score += node.getScore();
        }
    }

    public boolean isLeaf() {
        return this.state.gameOver();
    }

    public int getScore() {
        return this.score;
    }

    public Action getAction() {
        return this.action;
    }

    public List<StateNode> getChildren() {
        return this.children;
    }

    public StateNode getParent() {
        return this.parent;
    }

    public boolean isMax() {
        return this.isMax;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    @Override
    public String toString() {
        return "StateNode{" +
                "action=" + action +
                ", score=" + score +
                '}';
    }
}
