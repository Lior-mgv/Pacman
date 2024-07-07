package View.ObjectViews.UpgradeViews;

import Model.Upgrades.Upgrade;

import javax.swing.*;

public class HunterUpgradeView extends UpgradeView{
    public HunterUpgradeView(Upgrade upgrade) {
        super(upgrade);
        setImage(new ImageIcon("Resources\\HunterPill.png"));
    }
}
