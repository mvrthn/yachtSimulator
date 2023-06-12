import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Rudder {
    private final BufferedImage icon;
    private final Point position;
    private final Point axis;
    private final Point size;
    private double angle;

    public Rudder(Point position, Point axis, Point size) throws IOException {
        icon = ImageIO.read(new File("images/rudder.png"));

        this.position = new Point(position);
        this.axis = new Point(axis);
        this.size = new Point(size);
        angle = 0;
    }

    public BufferedImage getIcon() {
        return icon;
    }

    public Point getAxis() {
        return new Point(axis);
    }

    public Point getPosition() {
        return new Point(position);
    }

    public Point getSize() {
        return new Point(size);
    }


    public boolean angleCloseToZero() {
        return angle <= 0.02 && angle >= -0.02;
    }

    public double getAngle() {
        return angle;
    }

    public void changeAngle(double angleChange) {
        angle += angleChange;
        if(Math.abs(angle) > 1.39626) {
            angle = 1.39626 * Math.signum(angle);
        }
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
