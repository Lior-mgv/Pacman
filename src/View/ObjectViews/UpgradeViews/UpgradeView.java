package View.ObjectViews.UpgradeViews;

import Model.Upgrades.Upgrade;
import View.ObjectViews.ObjectView;

import javax.swing.*;

public class UpgradeView extends ObjectView {
    public UpgradeView(Upgrade upgrade) {
        this.object = upgrade;
        this.layer = 2;
    }
}
