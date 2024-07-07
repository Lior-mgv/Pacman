package Model;

import java.util.Random;

public enum Direction {
    LEFT(0),
    UP(1),
    RIGHT(2),
    DOWN(3);
    private final int value;

    Direction(int value) {
        this.value = value;
    }
    
    public int getValue(){
        return value;
    }
    public static Direction getByValue(int value){
        return switch (value) {
            case 0 -> LEFT;
            case 1 -> RIGHT;
            case 2 -> UP;
            case 3 -> DOWN;
            default -> null;
        };
    }
    public static Direction getRandomDirection(){
        Random random = new Random();
        return getByValue(random.nextInt(0,4));
    }

}
