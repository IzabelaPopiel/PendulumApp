package sample;

public class Simple implements CalculateAcceleration, TotalEnergy {
    @Override
    public double a(double x) {
        return -x;
    }

    @Override
    public double energy(double x, double v) {
        return 0.5 * v * v - Math.cos(x);
    }
}
