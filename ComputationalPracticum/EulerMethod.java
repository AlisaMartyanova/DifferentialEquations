package sample;

public class EulerMethod extends SolutionMethod {

    public double func(double i, double h) {
        double y;
        y = yval + h * (5.0 - Math.pow(i - h, 2.0) - Math.pow(yval, 2.0) + 2.0 * (i - h) * yval);
        yval = y;
        test(y);
        return y;
    }

}




