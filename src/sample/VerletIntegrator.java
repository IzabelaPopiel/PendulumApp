package sample;

public class VerletIntegrator {

    private double dt;

    public VerletIntegrator(double dt) {
        this.dt = dt;
    }

    public void intergrate(CalculateAcceleration calculateAcceleration, TotalEnergy totalEnergy, ODEUpdate odeupdate, double tStart, double tStop, double x0, double v0) {

        int nSteps = (int) ((tStop - tStart) / dt);

        double t = tStart;
        double x = x0;
        double v = v0;
        double e = totalEnergy.energy(x, v);

        odeupdate.update(t, x, v, e);


        for (int i = 0; i < nSteps; i++) {
            double a = calculateAcceleration.a(x);

            double vHalf = v + dt * a / 2;
            double xNew = x + dt * vHalf;
            double aNew = calculateAcceleration.a(xNew);
            double vNew = v + dt * (a + aNew) / 2;
            double eNew = totalEnergy.energy(xNew, vNew);

            e = eNew;
            t += dt;
            x = xNew;
            v = vNew;

            odeupdate.update(t, x, v, e);
        }
    }
}
