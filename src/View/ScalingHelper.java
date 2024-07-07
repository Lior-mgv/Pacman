package View;

import Model.Size;

import javax.swing.*;
import java.awt.*;

public class ScalingHelper {
    private final double scale;

    public ScalingHelper(double scale) {
        this.scale = scale;
    }

    public Dimension scaleSize(Size size) {
        return new Dimension(scaleInt(size.getWidth()), scaleInt(size.getHeight()));
    }

    public Dimension scaleSize(Dimension size) {
        return new Dimension(round(size.getWidth()*scale), round(size.getHeight()*scale));
    }

    public Image scaleImage(Image image, Dimension scaledSize) {
        return image.getScaledInstance(scaledSize.width, scaledSize.height, Image.SCALE_SMOOTH);
    }

    public int scaleInt(int value) {
        return round(value*scale);
    }

    public Point scalePoint(Point point) {
        return new Point(scaleInt(point.x), scaleInt(point.y));
    }

    private int round(double value) {
        return (int)Math.round(value);
    }
}
