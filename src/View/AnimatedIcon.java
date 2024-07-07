package View;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.time.LocalTime;
import java.util.Arrays;

public class AnimatedIcon extends ImageIcon {
    private ImageIcon currentImage;
    private double angle;
    private ImageIcon[] images;

    public AnimatedIcon(ImageIcon[] images) {
        this.images = images;
        currentImage = images[0];
        angle = 0;
    }

    @Override
    public Image getImage() {
        return rotateImage(currentImage.getImage(), angle);
    }

    private Image rotateImage(Image image, double angle) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        double centerX = width / 2.0;
        double centerY = height / 2.0;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), centerX, centerY);

        g2d.drawImage(image, transform, null);
        g2d.dispose();

        return bufferedImage;
    }

    @Override
    public int getIconWidth() {
        return currentImage.getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return currentImage.getIconHeight();
    }

    public void setNextImage(){
        if(Arrays.asList(images).indexOf(currentImage) == images.length-1)
            currentImage = images[0];
        else currentImage = images[Arrays.asList(images).indexOf(currentImage)+1];
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
