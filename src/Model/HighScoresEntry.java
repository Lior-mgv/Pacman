package Model;

import java.io.Serializable;

public class HighScoresEntry implements Serializable {
    private String name;
    private int score;

    public HighScoresEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return name + "  -  " + score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
