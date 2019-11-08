package sample;

public class ImprovedEulerMethod extends SolutionMethod {

    public double func(double i, double h) {
        double y;
        double fxy = 5 - Math.pow(i - h, 2.0) - Math.pow(yval, 2.0) + 2 * (i - h) * yval;
        double hfxy = h * fxy;
        double f = 5 - Math.pow(i, 2.0) - Math.pow(yval + hfxy, 2.0) + 2 * (i) * (yval + hfxy);
        y = yval + (h / 2) * (fxy + f);
        yval = y;
        return y;
    }

}

