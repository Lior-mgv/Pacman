package Model.Upgrades;

import Model.GameModel;

import java.awt.*;

public class HunterUpgrade extends TemporaryUpgrade{

    public HunterUpgrade(Point location) {
        super(location, 10000);
    }

    @Override
    public void activate(GameModel gameModel) {
        super.activate(gameModel);
        gameModel.getPacman().setInHunterMode(true);
        gameModel.getGhosts().forEach(ghost -> {ghost.setScared(true);});
    }

    @Override
    public void deactivate() {
        gameModel.getPacman().setInHunterMode(false);
        gameModel.getGhosts().forEach(ghost -> {
            ghost.setScared(false);
        });

    }
}
