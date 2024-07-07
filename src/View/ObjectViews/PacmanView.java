package View.ObjectViews;

import Model.CharacterState;
import Model.Pacman;
import View.AnimatedIcon;

import javax.swing.*;
import java.time.Duration;
import java.time.LocalTime;

public class PacmanView extends CharacterView {
    private LocalTime lastPictureChange;
    private ImageIcon[] icons = {new ImageIcon("Resources\\Pacman1.png"), new ImageIcon("Resources\\Pacman2.png"),
            new ImageIcon("Resources\\Pacman3.png")};
    private AnimatedIcon animatedIcon;

    public PacmanView(Pacman pacman) {
        this.object = pacman;
        this.layer = 3;
        animatedIcon = new AnimatedIcon(icons);
    }

    @Override
    public void updateObjectView() {
        if (((Pacman) object).isAlive()) {
            if (((Pacman) object).getState() == CharacterState.MOVING &&
                    (lastPictureChange == null || Duration.between(lastPictureChange, LocalTime.now()).toMillis() > 35)) {
                animatedIcon.setNextImage();
                lastPictureChange = LocalTime.now();
            }

            switch (getDirection()) {
                case RIGHT -> animatedIcon.setAngle(0);
                case DOWN -> animatedIcon.setAngle(90);
                case LEFT -> animatedIcon.setAngle(180);
                case UP -> animatedIcon.setAngle(270);
            }

            super.setImage(animatedIcon);

            super.updateObjectView();
        }
    }

}