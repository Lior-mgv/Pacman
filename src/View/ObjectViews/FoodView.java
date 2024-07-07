package View.ObjectViews;

import Model.FoodObject;

import javax.swing.*;

public class FoodView extends ObjectView {
    public FoodView(FoodObject foodObject){
        this.object = foodObject;
        setLocation(object.getWrappingRectangle().getLocation());
        this.layer = 1;
        this.setImage(new ImageIcon("Resources\\Food.png"));
    }
}
