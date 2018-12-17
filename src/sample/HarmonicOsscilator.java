package sample;

public class HarmonicOsscilator implements CalculateAcceleration, TotalEnergy {
    @Override
    public double a(double x) {
        return -x;
    }

    @Override
    public double energy(double x, double v) {
        return 0.5 * v * v + 0.5 * x * x;
    }
}
