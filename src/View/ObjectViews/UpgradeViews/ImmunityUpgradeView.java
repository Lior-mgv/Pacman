package View.ObjectViews.UpgradeViews;

import Model.Upgrades.Upgrade;

import javax.swing.*;

public class ImmunityUpgradeView extends UpgradeView{
    public ImmunityUpgradeView(Upgrade upgrade) {
        super(upgrade);
        setImage(new ImageIcon("Resources\\ImmunityUpgrade.png"));
    }
}
