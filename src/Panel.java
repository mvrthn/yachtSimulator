import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Panel extends JPanel implements Runnable {
    private final BufferedImage windRoseIcon;
    private final BufferedImage indicatorIcon;
    private final Wind wind;
    private final Indicator indicator;
    private final Yacht yacht;

    public Panel() throws IOException {
        windRoseIcon = ImageIO.read(new File("images/windRose.png"));
        indicatorIcon = ImageIO.read(new File("images/indicator.png"));

        wind = new Wind();
        wind.start();

        indicator = new Indicator(wind);

        yacht = new Yacht(indicator);

        this.setBackground(new Color(0x0060A0));
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.addKeyListener(new KeyAction(yacht));

        Thread thread = new Thread(this);
        thread.start();
    }

    private void drawIndicator(Graphics2D g2d) {
        int posX = this.getWidth() - 200;
        g2d.drawImage(windRoseIcon, posX, 0, 200, 200, null);

        AffineTransform at = g2d.getTransform();
        g2d.rotate(indicator.getDirection(yacht.getSpeed(), yacht.getDirection()), posX + 100, 100);
        g2d.drawImage(indicatorIcon, posX, 0, 200, 200, null);
        g2d.setTransform(at);

        g2d.setFont(new Font("Roboto", Font.BOLD, 24));
        g2d.drawString(indicator.getSpeed() + " m/s", this.getSize().width - 130, 230);
    }

    private void drawYacht(Graphics2D g2d) {
        AffineTransform initialAT = g2d.getTransform();
        g2d.rotate(yacht.getDirection(), yacht.getRotationX(), yacht.getRotationY());
        g2d.drawImage(yacht.getIcon(), yacht.getPosX(), yacht.getPosY(), yacht.getWidth(), yacht.getHeight(), null);
        AffineTransform yachtAT = g2d.getTransform();

        Point[] rudderData = yacht.getRudderData();
        g2d.rotate(yacht.getRudderAngle(), rudderData[0].x, rudderData[0].y);
        g2d.drawImage(yacht.getRudderIcon(), rudderData[1].x, rudderData[1].y, rudderData[2].x, rudderData[2].y, null);
        g2d.setTransform(yachtAT);

        if(yacht.mainSailIsUp()) {
            Point[] mainSailPos = yacht.getMainSailPos();
            g2d.drawLine(mainSailPos[0].x, mainSailPos[0].y, mainSailPos[1].x, mainSailPos[1].y);
        }
        g2d.setTransform(initialAT);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawIndicator(g2d);
        drawYacht(g2d);
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        yacht.setPosition(this.getSize());
        while(true) {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }
}
