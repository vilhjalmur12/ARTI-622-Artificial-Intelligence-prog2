public class Action {

    public Position from;
    public Position to;

    private boolean capturing;

    public Action () {

    }

    public Action (Position from, Position to, boolean capturing) {
        this.from = from;
        this.to = to;
        this.capturing = capturing;
    }

    public boolean isCapturing () {
        return capturing;
    }

    @Override
    public String toString() {
        return  "(move " + from.x + " " + from.y + " " + to.x + " " + to.y + ")";
    }
}
