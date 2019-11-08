package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ExactSolution extends SolutionMethod {

    double C;

    //before computing exact solution, needs to find constant depending on x0 and y0
    public void findConst(double x0, double y0){
        C = (1/(y0-x0-2.0)+(1.0/4.0))*Math.exp(-4.0*x0);

    }

    protected double func(double i, double h) {
        double y;
        y = (1.0 / ((C * Math.exp(4.0 * i)) - 1.0 / 4.0)) + i + 2.0;
        test(y);
        return y;
    }
}

