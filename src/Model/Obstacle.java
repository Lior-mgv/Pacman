package Model;

import Settings.Settings;

import java.awt.*;

public class Obstacle extends GameObject {
    CellTypes type;

    public Obstacle(Point location) {
        super(location, Settings.cellSize, Settings.cellSize);
    }

    public CellTypes getType() {
        return type;
    }
}
