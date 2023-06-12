import java.awt.*;

public class JibSail extends Sail {
    private final Point leftSheetPos;
    private final Point rightSheetPos;
    private boolean sailOnLeft;

    public JibSail(int x1, int y1, int x2, int y2, int x3, int y3, int length, int sheetMaxLen, int sheetMinLen, int a, int b) {
        super(x1, y1, length, sheetMaxLen, sheetMinLen, a, b);
        leftSheetPos = new Point(x2, y2);
        rightSheetPos = new Point(x3, y3);
        sailOnLeft = true;
    }

}
