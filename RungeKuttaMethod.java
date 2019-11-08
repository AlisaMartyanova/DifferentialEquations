package sample;

public class RungeKuttaMethod extends SolutionMethod {

    public double func(double i, double h) {
        double y;
        double k1 = 5 - Math.pow(i - h, 2.0) - Math.pow(yval, 2.0) + 2 * (i - h) * yval;
        double k2 = 5 - Math.pow(i - h + h / 2, 2.0) - Math.pow(yval + (h / 2) * k1, 2.0) + 2 * (i - h + h / 2) * (yval + (h / 2) * k1);
        double k3 = 5 - Math.pow(i - h + h / 2, 2.0) - Math.pow(yval + (h / 2) * k1, 2.0) + 2 * (i - h + h / 2) * (yval + (h / 2) * k2);
        double k4 = 5 - Math.pow(i, 2.0) - Math.pow(yval + h * k3, 2.0) + 2 * (i) * (yval + h * k3);
        y = yval + (h / 6) * (k1 + 2 * k2 + 2 * k3 + k4);
        test(y);
        yval = y;
        return y;
    }

}

