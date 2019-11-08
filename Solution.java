package sample;

import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public interface Solution {

    //Computation of either exact solution or numerical one, depending on the type
    public XYChart.Series Solution(double x0, double y0, double X, double N, Label l, Solution sol);

    //Computation of Local error
    public XYChart.Series LocalError(double x0, double y0, double X, double N, Label l, Solution sol);

    //Computation of the Total error for N in range N1..N2
    public XYChart.Series TotalError(double x0, double y0, double X, Label l, double N1, double N2, Solution sol);

}


