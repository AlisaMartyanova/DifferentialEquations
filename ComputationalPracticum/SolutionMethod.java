package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class SolutionMethod implements Solution {


    double yval;    //y with index i-1


    //Computation of either exact solution or numerical one, depending on the type
    public XYChart.Series Solution(double x0, double y0, double X, double N, Label l, Solution sol){


        double h = (X-x0)/N;    // step of approximation

        XYChart.Series series1 = new XYChart.Series();  //series where we will store values of y at each x
        ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();
        datas.add(new XYChart.Data(x0,y0));
        yval = y0;

        for (double i = x0+h; i<X; i+=h){
            double y = ((SolutionMethod)sol).func(i, h);
            if (Double.isInfinite(y) || Double.isNaN(y)){   //check if the value overflows
                l.setVisible(true);     //exception message
                l.setTextFill(Color.RED);
            }
            if(test(y))
                datas.add(new XYChart.Data(i, y));

        }

        series1.setData(datas);

        return series1;

    }


    //Computation of Local error
    public XYChart.Series LocalError(double x0, double y0, double X, double N, Label l, Solution sol){
        double h = (X-x0)/N;
        XYChart.Series series6 = new XYChart.Series();
        ObservableList<XYChart.Data> datasEr = FXCollections.observableArrayList();
        yval = y0;

        ExactSolution anSol = new ExactSolution();
        anSol.findConst(x0, y0);     //before computing exact solution, needs to find constant depending on x0 and y0

        datasEr.add(new XYChart.Data(x0, 0));   //at initial point error equals to zero
        for (double i = x0+h; i<X; i+=h){
            double exact = anSol.func(i, h);    //compute exact solution
            yval = anSol.func(i-0.1, h);
            double app = ((SolutionMethod)sol).func(i, h);  //compute approximate solution
            double el = Math.abs(exact - app);

            if (Double.isInfinite(el) || Double.isNaN(el)){ //check if the value overflows
                l.setVisible(true);    //exception message
                l.setTextFill(Color.RED);
            }

            if(test(el))
                datasEr.add(new XYChart.Data(i, el));
        }

        series6.setData(datasEr);
        return series6;
    }


    //Computation of the Total error for N in range N1..N2
    public XYChart.Series TotalError(double x0, double y0, double X, Label l, double N1, double N2, Solution sol){
        double h;
        XYChart.Series series5 = new XYChart.Series();
        ObservableList<XYChart.Data> datasEr = FXCollections.observableArrayList();


        ExactSolution anSol = new ExactSolution();
        anSol.findConst(x0, y0);    //before computing exact solution, needs to find constant depending on x0 and y0

        for (double i = N1; i<N2; i+=1.0){  //compute local errors for each i in N1..N2 and take max every time
            yval = y0;

            double egMax = 0.0;
            h = (X-x0)/i;
            for (double j = x0+0.1; j<X; j+=0.1){
                yval = anSol.func(j-0.1, h);
                double app = ((SolutionMethod)sol).func(j, h);
                double exact = anSol.func(j, h);
                double eg = Math.abs(exact - app);
                if(eg>egMax)    //search for maximum value of error
                    egMax = eg;
            }

            if (Double.isInfinite(egMax) || Double.isNaN(egMax)){   //check if the value overflows
                l.setVisible(true); //exception message
                l.setTextFill(Color.RED);
            }

            if(test(egMax))
                datasEr.add(new XYChart.Data(i, egMax));

        }

        series5.setData(datasEr);
        return series5;
    }



    //Compute function depending on method, is implemented in inherited classes
    protected double func(double i, double h){
        return 0.0;
    }

    //check if the value overflows
    protected boolean test(double y) {
        return !(Double.isInfinite(y) || Double.isNaN(y));
    }

}
