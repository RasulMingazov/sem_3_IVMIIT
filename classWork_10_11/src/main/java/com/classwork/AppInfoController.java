package com.classwork;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AppInfoController {
    @FXML
    private Button close;

    @FXML
    public void onCloseClick(ActionEvent event){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
}
