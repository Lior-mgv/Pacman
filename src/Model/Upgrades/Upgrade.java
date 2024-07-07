package Model.Upgrades;

import Model.GameModel;
import Model.GameObject;
import Settings.Settings;

import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;

public abstract class Upgrade extends GameObject {
    public Upgrade(Point location) {
        super(location, Settings.upgradeSize, Settings.upgradeSize);
    }
    public abstract void activate(GameModel model);
}

