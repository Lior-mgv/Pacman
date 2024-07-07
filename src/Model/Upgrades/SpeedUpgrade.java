package Model.Upgrades;

import Model.GameModel;
import Settings.Settings;

import java.awt.*;

public class SpeedUpgrade extends TemporaryUpgrade {
    public SpeedUpgrade(Point location) {
        super(location, 10000);
    }

    @Override
    public void activate(GameModel gameModel) {
        super.activate(gameModel);
        gameModel.getPacman().setSpeed(Settings.pacmanInitialSpeed+2);
    }

    @Override
    public void deactivate() {
        gameModel.getPacman().setSpeed(Settings.pacmanInitialSpeed);
    }
}
