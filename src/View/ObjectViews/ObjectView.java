package View.ObjectViews;

import Model.GameObject;
import View.ScalingHelper;

import javax.swing.*;

public abstract class ObjectView extends JLabel {
    protected GameObject object;
    protected int layer;
    private ImageIcon image;
    private ScalingHelper scaleHelper = new ScalingHelper(1);
    private ImageIcon scaledImage;

    public GameObject getObject() {
        return object;
    }

    public void updateObjectView() {
        var rect = object.getWrappingRectangle();
        setLocation(scaleHelper.scalePoint(rect.getLocation()));
        setSize(scaleHelper.scaleSize(rect.getSize()));
        setIcon(scaledImage !=null ? scaledImage : image);
    }

    private ImageIcon scaleIcon(Icon icon) {
        var imageIcon = (ImageIcon)icon;
        var rect = object.getWrappingRectangle();
        var scaledSize = scaleHelper.scaleSize(rect.getSize());
        return new ImageIcon(scaleHelper.scaleImage(imageIcon.getImage(), scaledSize));
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public void setScale(double scale) {
        this.scaleHelper = new ScalingHelper(scale);
        scaledImage = scaleIcon(image);
    }

    protected void setImage(ImageIcon image) {
        this.image = image;
        scaledImage = scaleIcon(image);
    }

    protected ImageIcon getImage() {
        return image;
    }
}
