package View.ObjectViews.UpgradeViews;

import Model.Upgrades.Upgrade;

import javax.swing.*;

public class LifeUpgradeView extends UpgradeView{
    public LifeUpgradeView(Upgrade upgrade) {
        super(upgrade);
        setImage(new ImageIcon("Resources\\LifeUpgrade.png"));
    }

}
