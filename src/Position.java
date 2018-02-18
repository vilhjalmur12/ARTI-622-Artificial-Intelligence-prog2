
public class Position {
    public int x;
    public int y;

    public Position (int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result +y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }

        Position tmp = (Position) obj;
        if (this.x == tmp.x && this.y == tmp.y) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
