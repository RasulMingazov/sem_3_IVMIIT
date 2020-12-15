package com.classwork.controllers;

import com.classwork.pojo.DayTemp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ThirdController implements Initializable {
    @FXML
    private TableView<DayTemp> table;
    @FXML private TableColumn<DayTemp, LocalDate> date;
    @FXML private TableColumn<DayTemp, LocalTime> time;
    @FXML private TableColumn<DayTemp, Integer> humidity;
    @FXML private TableColumn<DayTemp, Double> windSpeed, temp;
    @FXML private LineChart<Number, Number> changesLineChart;
    @FXML private LineChart<Number, Number> changesLineChartForHumidity;
    ObservableList<DayTemp> dayTemps;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dayTemps = getData();
        feelTableView();
        feelLineChart();
        feelLineChartForHumidity();
    }

    @FXML
    private void onPrevTaskClick(ActionEvent e) throws IOException {
        chController("secondB.fxml", e);
    }

    private void chController (String s, ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
        Stage stage = (Stage) ((javafx.scene.Node)e.getSource()).getScene().getWindow();
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private ObservableList<DayTemp> getData() {
        ObservableList<DayTemp> dayTemps = FXCollections.observableArrayList();
        LocalDate date;
        LocalTime time;
        double temperature = 0;
        int humidity = 0;
        double windSpeed = 0.0;

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File("/Users/a1/Downloads/temp.xml"));
            Node root = document.getDocumentElement();
            NodeList times = ((Element) root).getElementsByTagName("time");
            for (int i = 0; i < times.getLength(); i++) {
                Node timeItem = times.item(i);
                NamedNodeMap attributes = timeItem.getAttributes();
                String dateAndTimeStr =  attributes.getNamedItem("from").getNodeValue();
                String[] dateAndTimeStrArr = dateAndTimeStr.split("T");
                String[] dateArr = dateAndTimeStrArr[0].split("-");
                date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]));
                String[] timeArr = dateAndTimeStrArr[1].split(":");
                if (timeArr[0].charAt(0) == '0') {
                    time = LocalTime.of(Integer.parseInt(String.valueOf(timeArr[0].charAt(1))), 0);
                }
                else  {
                    time = LocalTime.of(Integer.parseInt(timeArr[0]), 0);
                }
                NodeList temperatures = ((Element) timeItem).getElementsByTagName("temperature");
                for (int j = 0; j < temperatures.getLength(); j++) {
                    Node temp = temperatures.item(j);
                    NamedNodeMap attributesT = temp.getAttributes();
                    String valueOfTemp = attributesT.getNamedItem("value").getNodeValue();
                    temperature = Double.parseDouble(valueOfTemp);
                }
                NodeList humidities = ((Element) timeItem).getElementsByTagName("humidity");
                for (int j = 0; j < humidities.getLength(); j++) {
                    Node temp = humidities.item(j);
                    NamedNodeMap attributesH = temp.getAttributes();
                    String valueOfHum = attributesH.getNamedItem("value").getNodeValue();
                    humidity = Integer.parseInt(valueOfHum);
                }
                NodeList speeds = ((Element) timeItem).getElementsByTagName("windSpeed");
                for (int j = 0; j < speeds.getLength(); j++) {
                    Node temp = speeds.item(j);
                    NamedNodeMap attributesS = temp.getAttributes();
                    String valueOfSpeed = attributesS.getNamedItem("mps").getNodeValue();
                    windSpeed = Double.parseDouble(valueOfSpeed);
                }
                dayTemps.add(new DayTemp(date,time,temperature,humidity,windSpeed));
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return dayTemps;
    }
    private void feelTableView() {
        time.setCellValueFactory(new PropertyValueFactory<DayTemp, LocalTime>("time"));
        date.setCellValueFactory(new PropertyValueFactory<DayTemp, LocalDate>("date"));
        temp.setCellValueFactory(new PropertyValueFactory<DayTemp, Double>("temp"));
        humidity.setCellValueFactory(new PropertyValueFactory<DayTemp, Integer>("humidity"));
        windSpeed.setCellValueFactory(new PropertyValueFactory<DayTemp, Double>("windSpeed"));
        table.setItems(dayTemps);
    }

    private void feelLineChart() {
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        for (int i = 0; i < dayTemps.size(); i++) {
            series1.getData().add(new XYChart.Data<>(i, dayTemps.get(i).getTemp()));
            series2.getData().add(new XYChart.Data<>(i, dayTemps.get(i).getWindSpeed()));
        }
        series1.setName("Изменения температуры");
        series2.setName("Изменения cкорости ветра");
        changesLineChart.getData().add(series1);
        changesLineChart.getData().add(series2);
    }
    private void feelLineChartForHumidity() {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < dayTemps.size(); i++) {
            series.getData().add(new XYChart.Data<>(i, dayTemps.get(i).getHumidity()));
        }
        series.setName("Изменения влажности");
        changesLineChartForHumidity.getData().add(series);

    }
}
