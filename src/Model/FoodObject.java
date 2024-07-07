package Model;

import Settings.Settings;

import java.awt.*;

public class FoodObject extends GameObject {
    public FoodObject(Point location) {
        super(location, Settings.foodSize, Settings.foodSize);
    }
}
