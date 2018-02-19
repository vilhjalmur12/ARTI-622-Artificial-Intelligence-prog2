import java.util.List;
import java.util.Stack;


public class AlphaBeta {
    private StateNode root;
    private StateNode best;
    private Stack<Action> actionStack;
    private int expansionCount;


    public AlphaBeta(StateNode node, Stopwatch timer, long timeLimit) {
        this.root = node;
        best = expandNode(root, Constants.minInt, Constants.maxInt, timer, timeLimit);
        actionStack = new Stack<>();
        stackActions(best);
    }

    public AlphaBeta(StateNode node, int depthLimit) {
        this.root = node;
        expansionCount = 0;
        best = expandNode(root, Constants.minInt, Constants.maxInt, depthLimit);
        actionStack = new Stack<>();
        stackActions(best);
    }


    private StateNode expandNode(StateNode node, int alpha, int beta, Stopwatch timer, long timeLimit) {
        if (node.isLeaf() || timer.elapsedTime() >= timeLimit) {
            return node;
        }

        this.expansionCount++;
        node.makeChildNodes();

        if (node.isMax()) {
            int best = Constants.minInt;
            StateNode bestNode = new StateNode();

            for (StateNode child : node.children) {
                StateNode val = expandNode(child, alpha, beta, timer, timeLimit);
                if (best < val.getScore()) {
                    best = val.getScore();
                    bestNode = val;
                }
                alpha = Math.max(alpha, best);

                if(beta <= alpha) {
                    break;
                }
            }
            return bestNode;

        }
        else
        {
            int best = Constants.maxInt;
            StateNode bestNode = new StateNode();

            for (StateNode child : node.children) {
                StateNode val = expandNode(child, alpha, beta, timer, timeLimit);
                if (best > val.getScore()) {
                    best = val.getScore();
                    bestNode = val;
                }
                beta = Math.min(beta, best);

                if (beta <= alpha) {
                    break;
                }
            }
            return bestNode;
        }
    }

    private StateNode expandNode(StateNode node, int alpha, int beta, int depthLimit) {
        if (node.isLeaf() || depthLimit <= 0) {
            return node;
        }

        node.makeChildNodes();

        if (node.isMax()) {
            int best = Constants.minInt;
            StateNode bestNode = new StateNode();

            for (StateNode child : node.children) {
                StateNode val = expandNode(child, alpha, beta, depthLimit--);
                if (best < val.getScore()) {
                    best = val.getScore();
                    bestNode = val;
                }
                alpha = Math.max(alpha, best);

                if(beta <= alpha) {
                    break;
                }
            }
            return bestNode;
        }
        else
        {
            int best = Constants.maxInt;
            StateNode bestNode = new StateNode();

            for (StateNode child : node.children) {
                StateNode val = expandNode(child, alpha, beta, depthLimit--);
                if (best > val.getScore()) {
                    best = val.getScore();
                    bestNode = val;
                }
                beta = Math.min(beta, best);

                if (beta <= alpha) {
                    break;
                }
            }
            return bestNode;
        }
    }

    private void stackActions (StateNode node) {
        if (node.isRoot()) {
            return;
        }
        this.actionStack.push(node.getAction());
        stackActions(node.getParent());
    }

    public Action nextAction() {
        return actionStack.pop();
    }

    public Stack<Action> getActionStack() {
        return actionStack;
    }

    public int getExpansionCount() {
        return this.expansionCount;
    }
}
