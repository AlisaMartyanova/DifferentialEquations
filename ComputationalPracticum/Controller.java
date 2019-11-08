package sample;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import jdk.nashorn.internal.runtime.ParserException;

import java.lang.Exception;

import java.lang.reflect.InvocationTargetException;

public class Controller{

    @FXML LineChart chart1, chart2, chart3;

    @FXML TextField x0_id, y0_id, X_id, N_id, N_id1, N_id2;

    @FXML CheckBox buttE, buttIE, buttRK, buttA;

    @FXML Label buttEr, label1, label3, Er2, ErN, inp;

    double x0, y0, X, N, N1, N2;

    XYChart.Series series1 = new XYChart.Series();
    XYChart.Series series2 = new XYChart.Series();
    XYChart.Series series3 = new XYChart.Series();
    XYChart.Series series4 = new XYChart.Series();

    XYChart.Series series5 = new XYChart.Series();
    XYChart.Series series6 = new XYChart.Series();
    XYChart.Series series7 = new XYChart.Series();

    XYChart.Series series8 = new XYChart.Series();
    XYChart.Series series9 = new XYChart.Series();
    XYChart.Series series10 = new XYChart.Series();



    //Initialization of values, necessary for computing functions
    private void Initialize(TextField x0, TextField y0, TextField X,
                            TextField N, TextField N1, TextField N2) {

        try{
            this.x0 = Double.parseDouble(x0.getText());
            this.y0 = Double.parseDouble(y0.getText());
            this.X = Double.parseDouble(X.getText());
            this.N = Double.parseDouble(N.getText());
            this.N1 = Double.parseDouble(N1.getText());
            this.N2 = Double.parseDouble(N2.getText());
        } catch ( Exception ex){  //check on invalid input
            inp.setVisible(true);   //error message
            inp.setTextFill(Color.RED);
        }


    }


    //default values, necessary for each plot
    public void SetValues(XYChart.Series series1, XYChart.Series series2, XYChart.Series series3, String name){

        chart1.setCreateSymbols(false); //remove bend points
        chart2.setCreateSymbols(false);
        chart3.setCreateSymbols(false);

        chart1.setAnimated(false);
        chart2.setAnimated(false);
        chart3.setAnimated(false);

        chart1.getData().add(series1);  //add series of points into plot
        chart2.getData().add(series2);
        chart3.getData().add(series3);

        series1.setName(name);      //add name to each graph
        series2.setName(name);
        series3.setName(name);
    }


    //after click on button Plot program launches from here
    public void Launch(ActionEvent event){

        chart1.getData().clear();
        chart2.getData().clear();
        chart3.getData().clear();

        label3.setVisible(false);
        inp.setVisible(false);

        //error verification
        if (x0_id.getText().trim().isEmpty()  || y0_id.getText().trim().isEmpty() ||
                X_id.getText().trim().isEmpty() || N_id.getText().trim().isEmpty()){

            Er2.setVisible(true);   //error message
            Er2.setTextFill(Color.RED);
        }
        else {
            Er2.setVisible(false);

            if (buttIE.isSelected() == false && buttE.isSelected() == false &&
                    buttRK.isSelected() == false && buttA.isSelected() == false){

                buttEr.setVisible(true);    //error message
                buttEr.setTextFill(Color.RED);
            }
            else{
                buttEr.setVisible(false);

                if((buttIE.isSelected() || buttE.isSelected() || buttRK.isSelected()) &&
                        (N_id1.getText().trim().isEmpty() || N_id2.getText().trim().isEmpty())){

                    if (buttA.isSelected())
                        LaunchExact();

                    ErN.setVisible(true);   //error message
                    ErN.setTextFill(Color.RED);

                }
                else{
                    ErN.setVisible(false);
                    if (buttA.isSelected())
                        LaunchExact();
                    if (buttE.isSelected())
                        LaunchEuler();
                    if (buttIE.isSelected())
                        LaunchImprovedEuler();
                    if (buttRK.isSelected())
                        LaunchRungeKutta();
                }

            }
        }

    }



    public void LaunchEuler(){

            series1.getData().clear();
            series5.getData().clear();
            series8.getData().clear();

            Solution method = new EulerMethod();

            Initialize(x0_id, y0_id, X_id, N_id, N_id1, N_id2);

            series1 = method.Solution(x0, y0, X, N, label3, method);
            series5 = method.TotalError(x0, y0, X, label3, N1, N2, method);
            series8 = method.LocalError(x0, y0, X, N, label3, method);

            SetValues(series1, series5, series8, "Euler");

    }

    public void LaunchImprovedEuler(){

        series2.getData().clear();
        series7.getData().clear();
        series9.getData().clear();

        Solution method = new ImprovedEulerMethod();

        Initialize(x0_id, y0_id, X_id, N_id, N_id1, N_id2);

        series2 = method.Solution(x0, y0, X, N, label3, method);
        series7 = method.TotalError(x0, y0, X, label3, N1, N2, method);
        series9 = method.LocalError(x0, y0, X, N, label3, method);

        SetValues(series2, series7, series9, "Improved Euler");

    }

    public void LaunchRungeKutta(){
        series3.getData().clear();
        series6.getData().clear();
        series10.getData().clear();

        Solution method = new RungeKuttaMethod();

        Initialize(x0_id, y0_id, X_id, N_id, N_id1, N_id2);

        series3 = method.Solution(x0, y0, X, N, label3, method);
        series6 = method.TotalError(x0, y0, X, label3, N1, N2, method);
        series10 = method.LocalError(x0, y0, X, N, label3, method);

        SetValues(series3, series6, series10, "Runge Kutta");


    }

    public void LaunchExact(){
        series4.getData().clear();

        Solution method = new ExactSolution();

        try{
            x0 = Double.parseDouble(x0_id.getText());
            y0 = Double.parseDouble(y0_id.getText());
            X = Double.parseDouble(X_id.getText());
            N = Double.parseDouble(N_id.getText());

        } catch (Exception e){
            inp.setVisible(true);   //error message
            inp.setTextFill(Color.RED);
        }

        //before computing exact solution, needs to find constant depending on x0 and y0
        ((ExactSolution) method).findConst(x0, y0);

        series4 = method.Solution(x0, y0, X, N, label3, method);

        chart1.getData().add(series4);
        chart1.setAnimated(false);
        chart1.setCreateSymbols(false);

        series4.setName("Exact");

    }



}