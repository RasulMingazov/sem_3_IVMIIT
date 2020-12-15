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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class SecondAController implements Initializable {
    @FXML private TableView<Temperature> tableA, tableB;
    @FXML private TableColumn<Temperature, LocalDate> dateA;
    @FXML private TableColumn<Temperature, Integer> tempA;
    ObservableList<Temperature> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = getAllData();
        feelTableView(data);
    }

    @FXML
    private void onNextTaskClick(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("secondB.fxml"));
        Parent root = loader.load();
        SecondBController editController = loader.getController();
        editController.transferData(tableA.getItems());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Edit Window");
        stage.show();
    }

    @FXML
    private void onPrevTaskClick(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("first.fxml"));
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private ObservableList<Temperature> getAllData() {
        ObservableList<Temperature> data = FXCollections.observableArrayList();
        try {
            Scanner inp = new Scanner(new File("/Users/a1/Downloads/temp2.csv"));
            while (inp.hasNext()) {
                String[] s = inp.next().split(";");
                String[] dates = s[0].split("\\.");
                LocalDate date = LocalDate.of(
                        Integer.parseInt(dates[2]),
                        Integer.parseInt(dates[1]),
                        Integer.parseInt(dates[0]));
                Temperature temperature = new Temperature(date, Integer.valueOf(s[1]));
                data.add(temperature);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void feelTableView(ObservableList<Temperature> data) {
        tempA.setCellValueFactory(new PropertyValueFactory<Temperature, Integer>("temp"));
        dateA.setCellValueFactory(new PropertyValueFactory<Temperature, LocalDate>("date"));

        tableA.setItems(data);
    }
}