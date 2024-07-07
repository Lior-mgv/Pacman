package View.ObjectViews;

import Model.Ghost;
import View.ObjectViews.CharacterView;

import javax.swing.*;

public class GhostView extends CharacterView {
    public GhostView(Ghost ghost) {
        this.object = ghost;
        this.layer = 4;
    }

    @Override
    public void updateObjectView() {
        if(((Ghost)object).isScared()){
            setImage(new ImageIcon("Resources\\ScaredGhost.png"));
        }
        else {
            switch (getDirection()) {
                case LEFT -> setImage(new ImageIcon("Resources\\GhostLeft.png"));
                case RIGHT -> setImage(new ImageIcon("Resources\\GhostRight.png"));
                case UP -> setImage(new ImageIcon("Resources\\GhostUp.png"));
                case DOWN -> setImage(new ImageIcon("Resources\\GhostDown.png"));
            }
        }
        super.updateObjectView();
    }
}
