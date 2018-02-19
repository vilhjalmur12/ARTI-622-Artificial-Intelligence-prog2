import java.util.ArrayList;
import java.util.List;

public class BFS {
    private StateNode root;
    private int depthLimit;

    public BFS() {

    }

    public BFS(StateNode root) {
        this.root = root;
    }

    public BFS(StateNode root, int depth) {
        this.root = root;
        this.depthLimit = depth;

        expandNode(this.root, 1);

    }

    private void expandNode(StateNode node, int depth) {
        if (depth >= this.depthLimit) {
            return;
        }

        node.makeChildNodes();
        if (node.children.isEmpty()) {
            return;
        }

        for (StateNode tmpNode : node.children) {
            expandNode(tmpNode, depth + 1);
        }

        node.updateScoreFromChildren();
    }

    public StateNode getBestFirstNode () {
        StateNode tmpNode = root.children.get(0);

        for (StateNode child : root.getChildren()) {
            if (tmpNode.getScore() < child.getScore()) {
                tmpNode = child;
            }
        }
        return tmpNode;
    }

}
