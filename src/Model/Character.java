package Model;

import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;

public abstract class Character extends GameObject {

    protected CharacterState state;
    protected Direction direction;
    protected int speed;

    public Character(Point location, int width, int height, int speed, Direction direction) {
        super(location, width, height);
        this.speed = speed;
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public CharacterState getState() {
        return state;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setState(CharacterState state) {
        this.state = state;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move(Duration passedTime) {
        if(state == CharacterState.MOVING) {
            int distancePassed = (int) (passedTime.toMillis() * speed);
            move(distancePassed, this.direction);
        }
    }

    public void move(int distance, Direction direction) {
        Point newLocation = new Point();
        switch (direction) {
            case RIGHT -> {
                newLocation = new Point(location.x + distance, location.y);
            }
            case LEFT -> {
                newLocation = new Point(location.x - distance, location.y);
            }
            case UP -> {
                newLocation = new Point(location.x, location.y - distance);
            }
            case DOWN -> {
                newLocation = new Point(location.x, location.y + distance);
            }
        }
        location = newLocation;
    }

    public void stop() {
        this.state = CharacterState.NOT_MOVING;
    }

    public abstract void updateMovementDirection(ArrayList<Direction> validDirections);

    public void moveBack(int distance) {
        move(distance, getOppositeDirection(direction));
    }

    protected Direction getOppositeDirection(Direction direction) {
        switch (direction) {

            case LEFT -> {
                return Direction.RIGHT;
            }
            case UP -> {
                return Direction.DOWN;
            }
            case RIGHT -> {
                return Direction.LEFT;
            }
            case DOWN -> {
                return Direction.UP;
            }
        }
        throw new RuntimeException();
    }
}

