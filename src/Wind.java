import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Wind extends Thread {
    //speed in m/s
    private double speed;
    //direction in radians from north clock-wise
    private double direction;
    private final Random random;
    private final double[] beaufortScale;
    public double maxStartSpeed;
    public double maxSpeed;
    public double minNormalSpeed;
    public double maxNormalSpeed;
    public double criticalSpeed;
    public double maxSpeedChange;

    public Wind() {
        beaufortScale = new double[]  {0.2, 1.5, 3.3, 5.4, 7.9, 10.7, 13.8, 17.1, 20.7, 24.4, 28.4, 32.6};
        maxStartSpeed = beaufortScale[6];
        maxSpeed = beaufortScale[9];
        minNormalSpeed = beaufortScale[3] + 0.1;
        maxNormalSpeed = beaufortScale[5];
        criticalSpeed = beaufortScale[7] + 0.1;
        maxSpeedChange = 8;
        
        random = new Random();
        speed = random.nextDouble() * maxStartSpeed;
        direction = random.nextDouble() * 2 * Math.PI;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDirection() {
        return direction;
    }

    private double distribution(double max, int n) {
        double r = random.nextDouble();
        return max * Math.pow(r, n);
    }

    @Override
    public void run() {
        double speedIncreaseProb = 0.10;
        double speedDecreaseProb = 0.10;
        double dirIncreaseProb = 0.10;
        double dirDecreaseProb = 0.10;
        double dirReverseProb = 0.01;
        int calm = 0;
        int squall = 0;
        while(true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double rDirection = random.nextDouble();
            if(rDirection <= dirIncreaseProb) {
                direction = (direction + distribution(Math.PI / 4, 4)) % (2 * Math.PI);
            }
            else if(rDirection >= 1 - dirDecreaseProb) {
                direction = (direction - distribution(Math.PI / 4, 4)) % (2 * Math.PI);
            }
            else if(rDirection <= dirIncreaseProb + dirReverseProb ) {
                direction = ((direction + Math.PI) + distribution(Math.PI * 3 / 4, 3)) % (2 * Math.PI);
            }

            double rSpeed = random.nextDouble();
            if(speed < minNormalSpeed) {
                rSpeed /= 2;
            }
            else if(speed > maxNormalSpeed) {
                rSpeed *= 2;
            }
            if(rSpeed <= speedIncreaseProb) {
                speed += distribution(maxSpeedChange, 4);
                if(speed > maxSpeed) {
                    speed = maxSpeed - distribution(maxSpeedChange, 4);
                }
            }
            else if(rSpeed >= 1 - speedDecreaseProb) {
                speed -= distribution(maxSpeedChange, 4);
                if(speed < 0) {
                    speed = 0 + distribution(maxSpeedChange, 4);
                }
            }
            if(speed == 0) {
                calm++;
            }
            else if(calm != 0) {
                calm = 0;
            }
            if(speed >= criticalSpeed) {
                squall++;
            }
            else if(squall != 0) {
                squall = 0;
            }
            if(calm == 10) {
                speed = random.nextDouble() * maxStartSpeed;
                direction = random.nextDouble() * 2 * Math.PI;
                calm = 0;
            }
            else if(squall == 10) {
                speed = speed - distribution(maxSpeedChange, 4);
                squall = 0;
            }
        }
    }
}
