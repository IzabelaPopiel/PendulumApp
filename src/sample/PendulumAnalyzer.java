package sample;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PendulumAnalyzer implements ODEUpdate {

    private ArrayList<Double> tValues = new ArrayList<>();
    private ArrayList<Double> xValues = new ArrayList<>();
    private ArrayList<Double> vValues = new ArrayList<>();
    private ArrayList<Double> eValues = new ArrayList<>();
    private double period;

    public PendulumAnalyzer() {
    }

    public ArrayList<Double> gettValues() {
        return tValues;
    }

    public ArrayList<Double> getxValues() {
        return xValues;
    }

    public ArrayList<Double> getvValues() {
        return vValues;
    }

    public ArrayList<Double> geteValues() {
        return eValues;
    }

    public void save(String fileName) {
        File file = new File(fileName);

        try (PrintWriter printWriter = new PrintWriter(file)) {

            for (int i = 0; i < tValues.size(); i++) {

                printWriter.print(tValues.get(i).toString() + "\t" + xValues.get(i).toString() + "\t" + vValues.get(i).toString() + "\t" + eValues.get(i).toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(double t, double x, double v, double e) {
        xValues.add(x);
        vValues.add(v);
        tValues.add(t);
        eValues.add(e);
        System.out.println(t + " " + x + " " + v + " " + e);
    }

    public double period(double dt) {

        double epsylon = dt/10;

//        double max = 0;
       double time = 0;
//        int index = 0;
//        for (int i = 0; i < tValues.size(); i++) {
//            if (xValues.get(i) > max) {
//                max = xValues.get(i);
//                time = tValues.get(i);
//                index = i;
//            }
//        }


        double x1 = xValues.get(30);
        boolean b1 = false;
        boolean b2 = false;

        for (int i = 30; i < tValues.size(); i++) {
            if (x1 <= xValues.get(i) + epsylon && x1 >= xValues.get(i) - epsylon) {
                b1 = true;
                time = tValues.get(i);
                i = i + 50;
                if (b1) {
                    if (x1 <= xValues.get(i) + epsylon && x1 >= xValues.get(i) - epsylon) {
                        b2 = true;
                    }
                    if (b2) {
                        period = tValues.get(i) - time;
                    }
                }
            }
        }

        return period;
    }
}
