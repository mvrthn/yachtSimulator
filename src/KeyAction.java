import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyAction implements KeyListener {
    private final Yacht yacht;

    public KeyAction(Yacht yacht) {
        this.yacht = yacht;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 32 -> yacht.rudderStraight();
            case 37 -> yacht.turnRudder(1);
            case 39 -> yacht.turnRudder(-1);
            case 16 -> yacht.setMainSail();
            case 38 -> yacht.changeMainSheet(-1);
            case 40 -> yacht.changeMainSheet(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == 37 || e.getKeyCode() == 39) {
            yacht.turnRudder(0);
        }
        else if(e.getKeyCode() == 38 || e.getKeyCode() == 40) {
            yacht.changeMainSheet(0);
        }
    }
}
