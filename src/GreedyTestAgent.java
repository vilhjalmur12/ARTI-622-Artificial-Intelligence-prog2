import javafx.scene.paint.Stop;

import java.util.List;
import java.util.Random;
import java.util.Stack;

public class GreedyTestAgent implements Agent {
    private Random random = new Random();

    private String role; // the name of this agent's role (white or black)
    private int playclock; // this is how much time (in seconds) we have before nextAction needs to return a move
    private boolean myTurn; // whether it is this agent's turn or not
    private int width, height; // dimensions of the board

    private State state;

    /*
        init(String role, int playclock) is called once before you have to select the first action. Use it to initialize the agent. role is either "white" or "black" and playclock is the number of seconds after which nextAction must return.
    */
    public void init(String role, int width, int height, int playclock) {
        this.role = role;
        this.playclock = playclock;
        myTurn = !role.equals("white");
        this.width = width;
        this.height = height;
        // TODO: add your own initialization code here

        Environment environment = new Environment(width, height);
        state = new State(environment, role);

        System.out.println("*** Initial Environment ***\n" + state.getEnvironment());
    }

    // lastMove is null the first time nextAction gets called (in the initial state)
    // otherwise it contains the coordinates x1,y1,x2,y2 of the move that the last player did
    public String nextAction(int[] lastMove) {
        if (lastMove != null) {
            int x1 = lastMove[0], y1 = lastMove[1], x2 = lastMove[2], y2 = lastMove[3];
            String roleOfLastPlayer;
            if (myTurn && role.equals("white") || !myTurn && role.equals("black")) {
                roleOfLastPlayer = "white";
            } else {
                roleOfLastPlayer = "black";
            }
            System.out.println(roleOfLastPlayer + " moved from " + x1 + "," + y1 + " to " + x2 + "," + y2);
            // TODO: 1. update your internal world model according to the action that was just executed


            state.makeAction(new Action(new Position(x1, y1), new Position(x2, y2), false), roleOfLastPlayer);

            System.out.println("*** Environment After turn ***\n" + state.getEnvironment());

        }

        // update turn (above that line it myTurn is still for the previous state)
        myTurn = !myTurn;
        if (myTurn) {
            // TODO: 2. run alpha-beta search to determine the best move


            // new timer
            Stopwatch timer = new Stopwatch();
            //Action action = getGreedyAction();
            Action action = ActionBFS();

            //state.makeAction(action);
            System.out.println("Time elapsed after decision: " + timer.elapsedTime());
            System.out.println("Playclock: " + playclock);
            return "(move " + action.from.x + " " + action.from.y + " " + action.to.x + " " + action.to.y + ")";
        } else {
            return "noop";
        }
    }

    // is called when the game is over or the match is aborted
    @Override
    public void cleanup() {
        // TODO: cleanup so that the agent is ready for the next match
    }

    // TODO: TEST
    private Action getGreedyAction() {
        List<Action> legalActions = state.getLegalActions();




        for (Action act : legalActions) {
            if (act.isCapturing()) {
                return act;
            }
        }
        return legalActions.get(0);
    }

    // TODO: TEST
    private Action ActionBFS() {
        StateNode root = new StateNode(null, this.state, null, true);
        BFS searchTree = new BFS(root, 5);

        StateNode bestNode = searchTree.getBestFirstNode();
        Action action = bestNode.getAction();
        // TODO: TEST
        //System.out.println("*** Environment ActionBFS ***\n" + state.getEnvironment());
        System.out.println("Best node score: " + bestNode.getScore());

        return action;
    }

}
