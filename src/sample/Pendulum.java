package sample;

public class Pendulum implements CalculateAcceleration, TotalEnergy {
    @Override
    public double a(double x) {
        return -Math.sin(x);
    }

    @Override
    public double energy(double x, double v) {
        return 0.5 * v * v - Math.cos(x);
    }
}
