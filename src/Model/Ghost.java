package Model;

import Settings.Settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Ghost extends Character {
    private boolean isScared;
    public Ghost(Point location, Direction direction) {
        super(location, Settings.characterSize, Settings.characterSize, Settings.ghostSpeed, direction);
        state = CharacterState.MOVING;
    }

    @Override
    public void updateMovementDirection(ArrayList<Direction> validDirections) {
        boolean canMoveForward = validDirections.contains(this.direction);

        var newDirections = new ArrayList<Direction>();
        for(var d : validDirections) {
            if (d!=direction && d!=getOppositeDirection(direction))
                newDirections.add(d);
        }

        if (!canMoveForward)
            newDirections.add(getOppositeDirection(direction));

        if (newDirections.size()==0)
            return;

        var turnProbability = canMoveForward ? 35 : 100;
        Random rnd = new Random();
        if (rnd.nextInt(100)<turnProbability) {
            direction = newDirections.get(rnd.nextInt(newDirections.size()));
        }
    }

    public boolean isScared() {
        return isScared;
    }

    public void setScared(boolean scared) {
        isScared = scared;
    }
}
