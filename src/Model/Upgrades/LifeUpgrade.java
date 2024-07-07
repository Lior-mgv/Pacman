package Model.Upgrades;

import Model.GameModel;

import java.awt.*;

public class LifeUpgrade extends Upgrade {

    public LifeUpgrade(Point location) {
        super(location);
    }

    @Override
    public void activate(GameModel gameModel) {
        gameModel.getPacman().increaseHp(1);
    }
}
