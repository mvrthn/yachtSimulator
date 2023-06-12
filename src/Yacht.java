import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Yacht implements Runnable {
    private final BufferedImage icon;
    private final int width;
    private final int height;
    private int posX;
    private int posY;
    private int rotationX;
    private int rotationY;
    private double direction;
    private double speed;
    private final Indicator indicator;
    private final Rudder rudder;
    private int rudderTurn;
    private final MainSail mainSail;
    private final JibSail jibSail;
    private int mainSheetChange;

    public Yacht(Indicator indicator) throws IOException {
        icon = ImageIO.read(new File("images/yacht.png"));

        this.indicator = indicator;

        Data d = new Data();

        width = d.yachtWidth;
        height = d.yachtHeight;

        direction = 0;
        speed = 1;

        rudder = new Rudder(d.rP, d.rA, d.rS);
        rudderTurn = 0;

        int a = width / 2;
        int b = height / 2;
        mainSail = new MainSail(d.mPA.x, d.mPA.y, d.mPB.x, d.mPB.y, d.mL, d.mSML, d.mSmL, a, b);
        jibSail = new JibSail(d.jPA.x, d.jPA.y, d.jPB.x, d.jPB.y, d.jPC.x, d.jPC.y, d.jL, d.jSML, d.jSmL, a, b);

        Thread thread = new Thread(this);
        thread.start();
    }

    public void setPosition(Dimension dimension) {
        rotationX = dimension.width / 2;
        rotationY = dimension.height / 2;
        posX = rotationX - width / 2;
        posY = rotationY -  height / 2;
    }

    public BufferedImage getIcon() {
        return icon;
    }

    public BufferedImage getRudderIcon() {
        return rudder.getIcon();
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getDirection() {
        return direction;
    }

    public double getSpeed() {
        return speed;
    }

    public int getRotationX() {
        return rotationX;
    }

    public int getRotationY() {
        return rotationY;
    }

    public Point[] getRudderData() {
        Point[] points = new Point[3];
        points[0] = rudder.getAxis();
        points[0].translate(posX, posY);
        points[1] = rudder.getPosition();
        points[1].translate(posX, posY);
        points[2] = rudder.getSize();
        return points;
    }

    public double getRudderAngle() {
        return rudder.getAngle();
    }


    public void rudderStraight() {
        rudderTurn = (int) (Math.signum(rudder.getAngle()) * -2);
    }

    public void turnRudder(int x) {
        rudderTurn = x;
    }

    public void changeMainSheet(int x) {
        if(mainSail.sailIsUp()) {
            mainSheetChange = x;
        }
    }

    public boolean mainSailIsUp() {
        return mainSail.sailIsUp();
    }

    public boolean jibSailIsUp() {
        return jibSail.sailIsUp();
    }

    public void setMainSail() {
        if(!mainSail.sailIsUp()) {
            mainSail.sailUp();
        }
        else {
            mainSail.sailDown();
        }
    }

    public Point[] getMainSailPos() {
        Point[] points = mainSail.getPosition(direction, indicator.getDirection());
        for(Point point : points) {
            point.translate(posX, posY);
        }
        return points;
    }

    @Override
    public void run() {
        while(true) {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            direction += rudder.getAngle() * -0.01;
            if(rudderTurn != 0) {
                rudder.changeAngle(0.01 * rudderTurn);
                if((rudderTurn >= 2 || rudderTurn <= -2) && rudder.angleCloseToZero()) {
                    rudder.setAngle(0);
                    rudderTurn = 0;
                }
            }
            if(mainSheetChange != 0) {
                mainSail.changeSheet(mainSheetChange);
            }
        }
    }
}
