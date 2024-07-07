package Model.Upgrades;

import Model.GameModel;

import java.awt.*;
import java.time.LocalTime;

public class ImmunityUpgrade extends TemporaryUpgrade {
    public ImmunityUpgrade(Point location, int duration) {
        super(location, duration);
    }

    public ImmunityUpgrade(Point location) {
        this(location, 7000);
    }

    @Override
    public void activate(GameModel gameModel) {
        super.activate(gameModel);
        gameModel.getPacman().setImmunity(true);
    }

    @Override
    public void deactivate() {
        gameModel.getPacman().setImmunity(false);
    }
}
