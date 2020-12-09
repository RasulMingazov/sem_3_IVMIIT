package com.classwork;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private TableView<User> table;
    @FXML private TableColumn<User, String> login, password, name, birthday, sex, city, address, color;
    @FXML private TableColumn<User, Boolean> flyChecker;
    @FXML private TableColumn<User, ListProperty<String>> phones;
    @FXML
    private MenuBar menuBar;
    String defaultURL = "data.xml";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @FXML
    public void onNewFieldClick(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
        try {
            Parent root = loader.load();
            EditController editController = loader.getController();
            editController.transferMessageForNewUser(table.getSelectionModel().getSelectedItem(),
                    table.getSelectionModel().getSelectedIndex(),
                    table.getItems() ,defaultURL);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("New User Window");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void onDisdeleteFieldClick(ActionEvent event) {
        callAlert("Режим удаления вылючен");
        table.setOnMouseClicked(null);
    }
    @FXML
    public void onDeleteFieldClick(ActionEvent event) {
        ObservableList<User> users = table.getItems();

        callAlert("Режим удаления включен");
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int index = table.getSelectionModel().getSelectedIndex();
                users.remove(index);
                feelTableView(users);

            }
        });
    }
    @FXML
    public void onSaveAsClick(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);
            Stage stage = (Stage) menuBar.getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);
            ObservableList<User> users = table.getItems();
            if (file != null) {
                new EditController().createNewDocument(users, file.getAbsolutePath());
            }
    }
    @FXML
    public void onSaveClick(ActionEvent event) {
        ObservableList<User> users = table.getItems();
        new EditController().createNewDocument(users, defaultURL);
    }

    @FXML
    public void onAppInfoClick(ActionEvent event) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("app_info.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("AboutApp");
            stage.setScene(new Scene(root));
            stage.show();
    }

    @FXML
    public void onExitFieldClick() {
        Platform.exit();
    }

    @FXML
    public void onOpenFieldClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) menuBar.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        defaultURL = file.getAbsolutePath();
        ObservableList<User> users = getFileData(defaultURL);
        feelTableView(users);
    }

    @FXML
    public void onChangeFieldClick(ActionEvent event) {
        callAlert("Режим редактирования включен");
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
                try {
                    Parent root = loader.load();
                    EditController editController = loader.getController();
                    editController.transferMessage(table.getSelectionModel().getSelectedItem(),
                            table.getSelectionModel().getSelectedIndex(),
                            table.getItems() ,defaultURL);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Edit Window");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void onDisсhangeFieldClick(ActionEvent event) {
        callAlert("Режим редактирования вылючен");
        table.setOnMouseClicked(null);
    }

    private void callAlert(String mes) {
        Alert alert = new Alert(Alert.AlertType.NONE,
                mes, ButtonType.APPLY);
        alert.show();
    }

    private ObservableList<User> getFileData(String url) {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(url));
            Node root = document.getDocumentElement();

            NodeList usersNL = ((Element) root).getElementsByTagName("user");
            for (int i = 0; i < usersNL.getLength(); i++) {
                Node nNode = usersNL.item(i);
                Element eElement = (Element) nNode;

                String str = eElement.getElementsByTagName("phones").item(0).getTextContent();
                String str1 = str.substring(1, str.length() - 1);
                String[] str2 = str1.split(", ");
                ArrayList<String> phones = new ArrayList<>(Arrays.asList(str2));

                User user = new User(
                        eElement.getAttribute("login"),
                        eElement.getElementsByTagName("password").item(0).getTextContent(),
                        eElement.getElementsByTagName("name").item(0).getTextContent(),
                        eElement.getElementsByTagName("birthday").item(0).getTextContent(),
                        eElement.getElementsByTagName("sex").item(0).getTextContent(),
                        eElement.getElementsByTagName("city").item(0).getTextContent(),
                        eElement.getElementsByTagName("address").item(0).getTextContent(),
                        eElement.getElementsByTagName("color").item(0).getTextContent(),
                        Boolean.parseBoolean(eElement.getElementsByTagName("flyChecker").item(0).getTextContent()),
                        phones,
                        eElement.getElementsByTagName("image").item(0).getTextContent());
                    users.add(user);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    private void feelTableView(ObservableList<User> users) {
        login.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        password.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        birthday.setCellValueFactory(new PropertyValueFactory<User, String>("birthday"));
        sex.setCellValueFactory(new PropertyValueFactory<User, String>("sex"));
        city.setCellValueFactory(new PropertyValueFactory<User, String>("city"));
        address.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
        color.setCellValueFactory(new PropertyValueFactory<User, String>("color"));
        flyChecker.setCellValueFactory(new PropertyValueFactory<User, Boolean>("flyChecker"));
        phones.setCellValueFactory(new PropertyValueFactory<User, ListProperty<String>>("phones"));
        table.setItems(users);
    }
}
