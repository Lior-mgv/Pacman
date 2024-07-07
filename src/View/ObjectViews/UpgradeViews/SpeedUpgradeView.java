package View.ObjectViews.UpgradeViews;

import Model.Upgrades.Upgrade;

import javax.swing.*;

public class SpeedUpgradeView extends UpgradeView{
    public SpeedUpgradeView(Upgrade upgrade) {
        super(upgrade);
        setImage(new ImageIcon("Resources\\SpeedUpgrade.png"));
    }
}

