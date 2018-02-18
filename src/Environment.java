import javafx.geometry.Pos;

import java.util.HashSet;
import java.util.Set;

public class Environment {

    private int board_width;
    private int board_height;

    public Environment () {}

    public Environment (int s_width, int s_height) {
        init_environment(s_width, s_height);
    }

    private Set<Position> white_positions = new HashSet<>();
    private Set<Position> black_positions = new HashSet<>();

    public void init_environment (int s_width, int s_height) {
        this.board_height = s_height;
        this.board_width = s_width;

        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= this.board_width; j++) {
                white_positions.add(new Position(j, i));
            }
        }

        for (int i = this.board_height; i >= this.board_height - 1; i--) {
            for (int j = 1; j <= this.board_width; j++) {
                black_positions.add(new Position(j, i));
            }
        }
    }

    public void make_move (int x1, int y1, int x2, int y2, String player) {
        Position old_p = new Position(x1, y1);
        Position new_p = new Position(x2, y2);

        if (player.equals("white")) {
            if (black_positions.contains(new_p)) {
                black_positions.remove(new_p);
            }

            white_positions.remove(old_p);
            white_positions.add(new_p);
            return;
        }

        if (player.equals("black")) {
            if (white_positions.contains(new_p)) {
                white_positions.remove(new_p);
            }

            black_positions.remove(old_p);
            black_positions.add(new_p);
            return;
        }
    }

    public Set<Position> getWhitePositions() {
        return white_positions;
    }

    public Set<Position> getBlackPositions() {
        return black_positions;
    }

    public boolean positionContainsBlack(Position pos) {
        return black_positions.contains(pos);
    }

    public boolean positionContainsWhite(Position pos) {
        return white_positions.contains(pos);
    }

    public boolean whiteAtEnd(Position pos) {
        return pos.y == this.board_height;
    }

    public boolean blackAtEnd(Position pos) {
        return pos.y == 1;
    }

    public boolean whiteCanCaptureBlackLeft(Position pos) {
        return black_positions.contains(new Position(pos.x - 1, pos.y + 1));
    }

    public boolean whiteCanCaptureBlackRight(Position pos) {
        return black_positions.contains(new Position(pos.x + 1, pos.y + 1));
    }

    public boolean blackCanCaptureWhiteLeft(Position pos) {
        return white_positions.contains(new Position(pos.x + 1, pos.y - 1));
    }

    public boolean blackCanCaptureWhiteRight(Position pos) {
        return white_positions.contains(new Position(pos.x - 1, pos.y - 1));
    }


    @Override
    public String toString() {
        String output = "";
        Position tmp;
        for (int i = board_height; i >= 1; i--) {
            for (int j = 1; j <= board_width; j++) {
                tmp = new Position(j, i);
                if (white_positions.contains(tmp)) {
                    output += "W ";
                    continue;
                }
                if (black_positions.contains(tmp)) {
                    output += "B ";
                    continue;
                }
                output += "O ";
            }
            output += "\n";
        }
        return output;
    }
}
