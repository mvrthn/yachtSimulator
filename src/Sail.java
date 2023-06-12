import java.awt.*;

public class Sail {
    private boolean isUp;
    private final Point tackPos;
    private final int length;
    private final int maxSheetLength;
    private final int minSheetLength;
    private int sheetLength;
    private final Point centerPos;

    public Sail(int x, int y, int length, int maxLen, int minLen, int a, int b) {
        isUp = false;
        tackPos = new Point(x, y);
        this.length = length;
        maxSheetLength = maxLen;
        minSheetLength = minLen;
        sheetLength = minSheetLength;
        centerPos = new Point(a, b);
    }

    public boolean sailIsUp() {
        return isUp;
    }

    public void sailUp() {
        isUp = true;
    }

    public void sailDown() {
        isUp = false;
     }

    public int getLength() {
        return length;
    }

    public int getSheetLength() {
        return sheetLength;
    }

    public Point getTackPos() {
        return new Point(tackPos);
    }

    public Point getCenterPos() {
        return new Point(centerPos);
    }

    public void changeSheet(int value) {
        sheetLength += value;
        if(sheetLength > maxSheetLength) {
            sheetLength = maxSheetLength;
        }
        else if(sheetLength < minSheetLength) {
            sheetLength = minSheetLength;
        }
    }
}
