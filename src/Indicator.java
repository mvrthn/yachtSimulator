public class Indicator {
    private double currentDirection;
    private int windSpeed;
    private double windDirection;
    private final Wind wind;

    public Indicator(Wind wind) {
        this.wind = wind;
        currentDirection = wind.getDirection();
    }

    public int getSpeed() {
        return windSpeed;
    }

    public double getDirection() {
        if(currentDirection != windDirection) {
            if(currentDirection <= windDirection + 0.02 && currentDirection >= windDirection - 0.02) {
                currentDirection = windDirection;
            }
            else if(currentDirection < windDirection) {
                currentDirection += 0.02;
            }
            else {
                currentDirection -= 0.02;
            }
        }
        return currentDirection;
    }

    public double getDirection(double yachtSpeed, double yachtDirection) {
        calculate(yachtSpeed, yachtDirection);
        return getDirection();
    }

    public void calculate(double yachtSpeed, double yachtDirection) {
        double realSpeed = wind.getSpeed();
        double realDirection = wind.getDirection();
        yachtDirection += Math.PI;

        double x = realSpeed * Math.cos(realDirection) + yachtSpeed * Math.cos(yachtDirection);
        double y = realSpeed * Math.sin(realDirection) + yachtSpeed * Math.sin(yachtDirection);

        windSpeed = (int) Math.round(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
        windDirection = Math.atan(y / x);
        if(x < 0) {
            windDirection += Math.PI;
        }
        else if(y < 0) {
            windDirection += 2 * Math.PI;
        }
    }
}
