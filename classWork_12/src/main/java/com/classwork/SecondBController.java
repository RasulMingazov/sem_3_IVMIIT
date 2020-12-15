package com.classwork;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;

public class SecondBController implements Initializable {
    @FXML private LineChart<Number, Number> aprilLineChart, allLineChart;
    ObservableList<Temperature> data = FXCollections.observableArrayList();
    @FXML private ComboBox<String> monthBox;
    @FXML private BarChart<String, Number> barChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monthBox.getItems().addAll(Month.JANUARY.toString(), Month.FEBRUARY.toString(), Month.MARCH.toString(),
                Month.APRIL.toString(), Month.MAY.toString(), Month.JUNE.toString(), Month.JULY.toString(),
                Month.AUGUST.toString(), Month.SEPTEMBER.toString(), Month.OCTOBER.toString(), Month.NOVEMBER.toString(),
                Month.DECEMBER.toString());
    }

    @FXML
    private void onPrevTaskClick(ActionEvent e) throws IOException {
        chController("secondA.fxml", e);
    }

    @FXML
    private void onNextTaskClick(ActionEvent e) throws IOException {

        chController("third.fxml", e);
    }

    void chController (String s, ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void transferData(ObservableList<Temperature> dataS) {
        data.addAll(dataS);
        punktA();
        punktC();
    }

    void punktA() {
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        for (Temperature datum : data) {
            if (datum.date.getMonth() == Month.APRIL) {
                series1.getData().add(new XYChart.Data<>(datum.date.getDayOfMonth(), datum.temp));
            }
        }
        series1.setName("Данные за апрель");
        aprilLineChart.getData().add(series1);
    }

    @FXML
    void onMonthBoxClick(ActionEvent e) {  //punktB()
        allLineChart.getData().clear();
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        for (Temperature datum : data) {
            if (datum.date.getMonth().toString().equals(monthBox.getValue().toString())) {
                series2.getData().add(new XYChart.Data<>(datum.date.getDayOfMonth(), datum.temp));
            }
        }
        series2.setName("Все данные");
        allLineChart.getData().add(series2);
    }

    void punktC() {
        String[] months = monthBox.getItems().toArray(new String[0]);
        Number[] monthMiddle = new Number[12];

        int allTemp = 0;
        int dayCounter = 0;
        int monthInd = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).date.getDayOfMonth() == 1 || (data.get(i).date.getMonth() == Month.DECEMBER && data.get(i).date.getDayOfMonth() == 31)) {
                if (data.get(i).date.getMonth() != Month.JANUARY) {
                    monthMiddle[monthInd] =
                            allTemp / dayCounter;
                }
                allTemp = 0;
                dayCounter = 0;
                monthInd++;
            }
            allTemp += data.get(i).temp;
            dayCounter++;
        }

        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        for (int i = 0; i < months.length; i++) {
            series.getData().add(new XYChart.Data<String, Number>(months[i], monthMiddle[i]));
        }
        series.setName("Среднямесячная температура");
        barChart.getData().add(series);
    }
}
