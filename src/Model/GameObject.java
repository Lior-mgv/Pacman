package Model;

import java.awt.*;
import java.util.ArrayList;

public class GameObject {
    public GameObject(Point location, int width, int height) {
        this.location = location;
        size = new Size(width, height);
    }

    protected Point location;
    protected Size size;

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(int width, int height) {
        this.size = new Size(width, height);
    }

    public Rectangle getWrappingRectangle() {
        return new Rectangle(location.x-size.getWidth()/2, location.y-size.getHeight()/2, size.getWidth(), size.getHeight());
    }

    public boolean collidesWith(GameObject other) {
        return this.getWrappingRectangle().intersects(other.getWrappingRectangle());
    }
    public boolean collidesWithAny(ArrayList<GameObject> objects){
        return getCollidingObject(objects)!=null;
    }

    public GameObject getCollidingObject(ArrayList<GameObject> objects) {
        for (var object : objects) {
            if (this.collidesWith(object))
                return object;
        }
        return null;
    }
}

