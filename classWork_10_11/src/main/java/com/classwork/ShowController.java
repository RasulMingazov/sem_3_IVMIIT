package com.classwork;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowController implements Initializable {

    @FXML
    private Label login, password, name, address, birthday, sex, city,flyChecker,color;
    @FXML
    private ListView<String> phones;
    @FXML
    private ImageView image;
    @FXML
    private Button close;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        close.setOnAction(actionEvent -> {
            Platform.exit();});
    }
    public void transferMessage(User user) {
        Image imageR = new Image(user.getImage());
        image.setImage(imageR);
        login.setText("Логин: " + user.getLogin());
        password.setText("Пароль: " + user.getPassword());
        name.setText("Имя: " + user.getName());
        color.setText("Цвет: " + user.getColor());
        address.setText("Адресс: " + user.getAddress());
        sex.setText("Пол: " + user.getSex());
        city.setText("Город: " + user.getCity());
        birthday.setText("Дата Рождения: " + user.getBirthday());
        if (user.isFlyChecker()) {
            flyChecker.setText("Умеет летать");
        }
        else {
            flyChecker.setText("Не умеет летать");
        }
        phones.setItems(user.getPhones());
    }
}
