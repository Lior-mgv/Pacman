package Model;

public enum CellTypes {
    EMPTY(0),
    FREE(1),
    OBSTACLE(2),
    LEFT_WALL(3),
    RIGHT_WALL(4),
    TOP_WALL(5),
    BOTTOM_WALL(6);
    private final int value;

    CellTypes(int value) {
        this.value = value;
    }
    public int getValue(){
        return value;
    }

}
