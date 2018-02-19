import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class ABAgent implements Agent{
    private Random random = new Random();

    private String role; // the name of this agent's role (white or black)
    private int playclock; // this is how much time (in seconds) we have before nextAction needs to return a move
    private boolean myTurn; // whether it is this agent's turn or not
    private int width, height; // dimensions of the board
    private long timeLimit;
    private State state;

    // Statistics
    List<Double> listTime;
    Stopwatch fullTime;
    List<Integer> expansionCountList;


    /*
        init(String role, int playclock) is called once before you have to select the first action. Use it to initialize the agent. role is either "white" or "black" and playclock is the number of seconds after which nextAction must return.
    */
    public void init(String role, int width, int height, int playclock) {
        if (role == null) {
            role = "white";
        } else {
            this.role = role;
        }
        this.playclock = playclock;
        myTurn = !role.equals("white");
        this.width = width;
        this.height = height;

        // TODO: add your own initialization code here
        double fullLimit = (double) playclock/2;
        this.timeLimit = (long) fullLimit;
        this.listTime = new ArrayList<>();
        this.fullTime = new Stopwatch();
        this.expansionCountList = new ArrayList<>();

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
            System.out.println("Time Limit: " + this.timeLimit);
            Stopwatch timer = new Stopwatch();

            Action action = ABAction(timer, this.timeLimit);

            System.out.println("Elapsed Time at end: " + timer.elapsedTime());
            this.listTime.add(timer.elapsedTime());

            return "(move " + action.from.x + " " + action.from.y + " " + action.to.x + " " + action.to.y + ")";
        } else {
            return "noop";
        }
    }

    // is called when the game is over or the match is aborted
    @Override
    public void cleanup() {
        // TODO: cleanup so that the agent is ready for the next match

        double MAX_time = (double) Constants.minInt;
        double MIN_time = (double) Constants.maxInt;
        double aveTime = 0;
        double fullTime = this.fullTime.elapsedTime();
        int MAX_expCount = Constants.minInt;
        int MIN_expCount = Constants.maxInt;
        double averageExpCount = 0;

        for (Double t : listTime) {
            aveTime += t;
            MAX_time = Math.max(MAX_time, t);
            MIN_time = Math.min(MIN_time, t);
        }

        for (Integer a : expansionCountList) {
            averageExpCount += (double) a;
            MAX_expCount = Math.max(MAX_expCount, a);
            MIN_expCount = Math.min(MIN_expCount, a);
        }

        averageExpCount /= expansionCountList.size();
        aveTime /= listTime.size();

        System.out.println("**** Statistics ***");
        System.out.println("Average action time: " + aveTime);
        System.out.println("MAX action time: " + MAX_time);
        System.out.println("MIN action time: " + MIN_time);
        System.out.println("Gametime length: " + fullTime);
        System.out.println("Average Expansion Count: " + averageExpCount);
        System.out.println("MAX Expansion count: " + MAX_expCount);
        System.out.println("MIN Expansion count: " + MIN_expCount);
        System.out.println("*******************");

        this.state = null;
        init(this.role, this.width, this.height, this.playclock);
    }

    public Action ABAction(Stopwatch timer, long limit) {
        StateNode root = new StateNode(null, this.state, null, true);
        AlphaBeta search = new AlphaBeta(root, timer, limit);

        this.expansionCountList.add(search.getExpansionCount());

        return search.nextAction();
    }

    public Action ABAction(int limit) {
        StateNode root = new StateNode(null, this.state, null, true);
        AlphaBeta search = new AlphaBeta(root, limit);

        Stack<Action> searchStack = search.getActionStack();
        Action action = searchStack.pop();

        System.out.println(action);

        while (!searchStack.empty()) {
            System.out.println(searchStack.pop());
        }

        //return search.nextAction();
        return action;
    }
}
