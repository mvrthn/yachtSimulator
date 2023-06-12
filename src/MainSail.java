import java.awt.*;

public class MainSail extends Sail {
    private final Point mainSheetPos;

    public MainSail(int x1, int y1, int x2, int y2, int length, int sheetMaxLen, int sheetMinLen, int a, int b) {
        super(x1, y1, length, sheetMaxLen, sheetMinLen, a, b);
        mainSheetPos = new Point(x2, y2);
    }

    public Point[] getPosition(double direction, double windDirection) {
        Point[] points = new Point[2];
        points[0] = getPointA(direction);
        points[1] = getPointB(windDirection, points[0], direction);
        return points;
    }

    private Point getPointA(double direction) {
        Point point = new Point();
        int r = Math.abs(getTackPos().y - getCenterPos().y);
        point.x = (int) (getCenterPos().x + (Math.sin(direction) * r));
        point.y = (int) (getCenterPos().y - (Math.cos(direction) * r));
        return point;
    }

    private Point getPointB(double angle, Point pointA, double direction) {
        Point pointC = adjustRotation(new Point(mainSheetPos), direction);
        Point point = new Point();
        point.x = (int) (pointA.x - (Math.sin(angle + Math.PI) * getLength()));
        point.y = (int) (pointA.y + (Math.cos(angle + Math.PI) * getLength()));
        if(point.distance(pointC) <= getSheetLength()) {
            return point;
        }
        if(getSheetLength() == 0) {
            return new Point(pointC);
        }
        double d = pointC.distance(pointA);
        double a = (Math.pow(getLength(), 2) - Math.pow(getSheetLength(), 2) + Math.pow(d, 2)) / (2 * d);
        double h = Math.sqrt(Math.pow(getLength(), 2) - Math.pow(a, 2));
        double px = pointA.x + a * (pointC.x - pointA.x) / d;
        double py = pointA.y + a * (pointC.y - pointA.y) / d;
        int i = (int) Math.signum(Math.PI - angle);
        point.x = (int) (px + i * h * (pointC.y - pointA.y) / d);
        point.y = (int) (py - i * h * (pointC.x - pointA.x) / d);
        return point;
    }

    private Point adjustRotation(Point point, double direction) {
        int r = Math.abs(point.y - getCenterPos().y);
        point.x = (int) (getCenterPos().x - (Math.sin(direction) * r));
        point.y = (int) (getCenterPos().y + (Math.cos(direction) * r));
        return point;
    }
}
