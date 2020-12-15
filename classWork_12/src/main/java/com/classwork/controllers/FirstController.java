package com.classwork.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FirstController implements Initializable {
    @FXML private BarChart<String, Number> bar;
    @FXML private LineChart<Number, Number> lineChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        punktA();
        punktB();
    }

    @FXML
    private void onNextTaskClick(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("secondA.fxml"));
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    void punktA() {
        String[] namesStr = {"Шишов", "Куроносов", "Кулебякина", "Букоедов"};
        Number[] weights = {41, 60, 44, 75};
        Number[] heights = {142,168,150,180};

        XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();

        for (int i = 0; i < namesStr.length; i++) {
           series1.getData().add(new XYChart.Data<String, Number>(namesStr[i], weights[i]));
           series2.getData().add(new XYChart.Data<String, Number>(namesStr[i], heights[i]));
        }
        series1.setName("Weight");
        series2.setName("Height");
        bar.getData().add(series1);
        bar.getData().add(series2);
    }

    void punktB() {
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        for (double i = -Math.PI/2; i < Math.PI/2; i+=0.01) {
            series1.getData().add(new XYChart.Data<>(i,(Math.sin( 2 * i))));
        }
        series1.setName("sin(2x)");
        lineChart.getData().add(series1);

        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        for (double i = -2*Math.PI; i < -Math.PI/2; i+=0.01) {
            series2.getData().add(new XYChart.Data<>(i,(1/i)));
        }
        series2.setName("1/x");
        lineChart.getData().add(series2);

        XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
        for (double i = Math.PI/2; i < 2*Math.PI; i+=0.01) {
            series3.getData().add(new XYChart.Data<>(i,(1/i)));
        }
        series3.setName("sqrt(x^3 - 1)");
        lineChart.getData().add(series3);
    }
}
