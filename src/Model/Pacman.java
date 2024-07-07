package Model;

import Settings.Settings;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class Pacman extends Character {
    private Direction pendingDirection;
    private boolean isInHunterMode;
    private boolean isImmune;
    private LocalTime becameImmune;
    private int hp = Settings.initialPacmanHp;

    public GameObject getMouth() {
        return new GameObject(new Point(location.x-7, location.y-7), 14,14);
    }
    public Pacman(Point location) {
        super(location, Settings.characterSize, Settings.characterSize, Settings.pacmanInitialSpeed, Direction.RIGHT);
        state = CharacterState.NOT_MOVING;
    }

    public boolean isAlive() {
        return hp>0;
    }

    public boolean isInHunterMode() {
        return isInHunterMode;
    }

    public void setInHunterMode(boolean inHunterMode) {
        isInHunterMode = inHunterMode;
    }

    public boolean canEat(FoodObject food) {
        return getMouth().collidesWith(food);
    }

    public void setPendingDirection(Direction direction) {
        pendingDirection = direction;
    }

    @Override
    public void updateMovementDirection(ArrayList<Direction> validDirections) {
        if (validDirections.contains(pendingDirection)) {
            direction = pendingDirection;
            state = CharacterState.MOVING;
        }

        if (!validDirections.contains(direction))
            stop();
    }

    @Override
    public void stop() {
        super.stop();
        pendingDirection = null;
    }

    public void setImmunity(boolean value) {
        isImmune = value;
    }

    public boolean isImmune() { return isImmune; }

    public void setImmune(boolean immune) {
        isImmune = immune;
        becameImmune = LocalTime.now();
    }

    public int getHp() {
        return hp;
    }

    public void increaseHp(int value) {
        this.hp += value;
    }

    public void reduceHp() {
        hp--;
    }

    public LocalTime getBecameImmune() {
        return becameImmune;
    }
}

