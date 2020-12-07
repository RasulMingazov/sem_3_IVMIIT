package src;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditController implements Initializable{

    @FXML
    private TextField login, password, name, address, phone;
    @FXML
    private DatePicker birthday;
    @FXML
    private ToggleGroup sex;
    @FXML
    private ComboBox<String> cityBox;
    @FXML
    private ListView<String> phonesLV;
    @FXML
    private Hyperlink change;
    @FXML
    private CheckBox flyChecker;
    @FXML
    private ColorPicker color;
    @FXML
    private Button save, add, delete;
    @FXML
    private ImageView image;
    private ObservableList<String> phonesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        phonesList = FXCollections.observableArrayList();
        cityBox.getItems().addAll("Москва","Санкт-Петербург", "Киров", "Уфа", "Барнаул", "Казань");
    }
    @FXML
    private void onSaveButtonClick(ActionEvent event) throws Exception {
        try {

            if (login.getText().equals("") || password.getText().equals("") || name.getText().equals("") ||
                    cityBox.getValue() == null || address.getText().equals("") || phonesLV == null)
                throw new Exception();
            User user = new User(
                    login.getText(),
                    password.getText(),
                    name.getText(),
                    birthday.getValue().toString(),
                    ((RadioButton) sex.getSelectedToggle()).getText(),
                    cityBox.getValue(),
                    address.getText(),
                    color.getValue().toString(),
                    flyChecker.isSelected(),
                    phonesLV.getItems(),
                    image.toString());

            Stage stage = new Stage();
            Scene scene = new Scene(new Group(new Text(user.toString())));
            stage.setScene(scene);
            stage.setTitle("User data");
            stage.setWidth(300);
            stage.setHeight(300);
            stage.show();
        }
        catch (Exception e) {

            Alert a1 = new Alert(Alert.AlertType.NONE,
                    "Заполните все столбцы",ButtonType.APPLY);
            a1.show();
        }

    }
    @FXML
    private void onAddButtonClick(ActionEvent event) {
        phonesList.add(phone.getText());
        phonesLV.getItems().add(phone.getText());
        phone.setText("");

    }
    @FXML
    private void onDeleteButtonClick(ActionEvent event) {
        phonesList.remove(phonesList.size()-1);
        phonesLV.setItems(phonesList);
    }
    @FXML
    private void changeImage(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser= new FileChooser();
        File file = fileChooser.showOpenDialog(((Node)event.getTarget()).getScene().getWindow());
        if (file != null){
            image.setImage(new Image(file.toURI().toString()));
        }
    }

}
