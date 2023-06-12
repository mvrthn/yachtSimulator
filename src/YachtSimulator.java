import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class YachtSimulator {
    public static void main(String[] args) {
        Panel panel;
        Image icon;
        try {
            panel = new Panel();
            icon = ImageIO.read(new File("images/yachtIcon.png"));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return;
        }

        JFrame frame = new JFrame("Yacht Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setResizable(false);
        frame.setIconImage(icon);
        frame.add(panel);
        frame.setVisible(true);
    }
}
