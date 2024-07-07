package Model.Upgrades;

import Model.GameModel;

import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;

public abstract class TemporaryUpgrade extends Upgrade {
    protected LocalTime activatedTime;
    protected int durationMs;
    protected GameModel gameModel;

    public TemporaryUpgrade(Point location, int duration) {
        super(location);
        durationMs = duration;
    }

    @Override
    public void activate(GameModel gameModel) {
        this.gameModel = gameModel;
        activatedTime = LocalTime.now();
        var currentUpgrade = gameModel.getActiveUpgrade();
        if (currentUpgrade!=null) {
            currentUpgrade.deactivate();
        }
        this.gameModel.setActiveUpgrade(this);
    }

    public abstract void deactivate();

    public void checkExpiry() {
        if (activatedTime == null)
            return;
        if (Duration.between(activatedTime, LocalTime.now()).toMillis() > durationMs) {
            deactivate();
            gameModel.setActiveUpgrade(null);
        }
    }
}
