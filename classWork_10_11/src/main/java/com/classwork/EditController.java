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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
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
    private CheckBox flyChecker;
    @FXML
    private ColorPicker color;
    @FXML
    private ImageView image;
    @FXML
    private RadioButton isMen, isWoman;
    private ObservableList<String> phonesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        phonesList = FXCollections.observableArrayList();
        cityBox.getItems().addAll("Москва","Санкт-Петербург", "Киров", "Уфа", "Барнаул", "Казань");
    }

    ObservableList<User> users;
    int changeIndex;
    String defaultURL;
    boolean isForNewUser;

    public void transferMessageForNewUser(User user, int index, ObservableList<User> userss, String defaultURLL) {
        users = userss;
        isForNewUser = true;
        defaultURL = defaultURLL;
    }

    public void transferMessage(User user, int index, ObservableList<User> userss, String defaultURLL) {
        users = userss;
        changeIndex = index;
        defaultURL = defaultURLL;


        login.setText(user.getLogin());
        password.setText(user.getPassword());
        name.setText(user.getName());
        color.setValue(Color.valueOf(user.getColor()));
        address.setText(user.getAddress());
        if (user.getSex().equals("Мужской")) {
            sex.selectToggle(isMen);
        } else {
            sex.selectToggle(isWoman);
        }
        cityBox.setValue(user.getCity());
        String[] date = user.getBirthday().split("-");
        birthday.setValue(LocalDate.of(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2])));
        if (user.isFlyChecker()) {
            flyChecker.setText("Умеет летать");
        }
        else {
            flyChecker.setText("Не умеет летать");
        }
        phonesLV.setItems(user.getPhones());

        File file = new File(user.getImage());
        chImage(file);
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
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
                    image.getImage().getUrl());
            if (!isForNewUser) {
                users.remove(changeIndex);
            }
            users.add(user);
            createNewDocument(users, defaultURL);
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        }
        catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.NONE,
                    "Заполните все столбцы", ButtonType.APPLY);
            alert.show();
        }
    }
    public void createNewDocument(ObservableList<User> users, String defaultURLL) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("users");
            doc.appendChild(rootElement);

            for (User user: users) {

                Element staff = doc.createElement("user");
                rootElement.appendChild(staff);
                staff.setAttribute("login", user.getLogin());

                Element password = doc.createElement("password");
                password.appendChild(doc.createTextNode(user.getPassword()));
                staff.appendChild(password);

                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(user.getName()));
                staff.appendChild(name);

                Element birthday = doc.createElement("birthday");
                birthday.appendChild(doc.createTextNode(user.getBirthday()));
                staff.appendChild(birthday);

                Element sex = doc.createElement("sex");
                sex.appendChild(doc.createTextNode(user.getSex()));
                staff.appendChild(sex);

                Element city = doc.createElement("city");
                city.appendChild(doc.createTextNode(user.getCity()));
                staff.appendChild(city);

                Element address = doc.createElement("address");
                address.appendChild(doc.createTextNode(user.getAddress()));
                staff.appendChild(address);

                Element color = doc.createElement("color");
                color.appendChild(doc.createTextNode(user.getColor()));
                staff.appendChild(color);

                Element flyChecker = doc.createElement("flyChecker");
                flyChecker.appendChild(doc.createTextNode(String.valueOf(user.isFlyChecker())));
                staff.appendChild(flyChecker);


                Element phones = doc.createElement("phones");
                phones.appendChild(doc.createTextNode(String.valueOf(user.getPhones())));
                staff.appendChild(phones);

                Element image = doc.createElement("image");
                image.appendChild(doc.createTextNode(String.valueOf(user.getImage())));
                staff.appendChild(image);


            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(defaultURLL));

            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();

        }
    }
    @FXML
    private void onAddButtonClick(ActionEvent event) {
        phonesList.add(phone.getText());
        phonesLV.setItems(null);
        phonesLV.setItems(phonesList);
        phone.setText("");
    }

    @FXML
    private void onDeleteButtonClick(ActionEvent event) {
        phonesList.remove(phonesLV.getSelectionModel().getSelectedIndex());
        phonesLV.setItems(null);
        phonesLV.setItems(phonesList);
    }

    @FXML
    private void changeImage(ActionEvent event) {
        FileChooser fileChooser= new FileChooser();
        File file = fileChooser.showOpenDialog(((Node)event.getTarget()).getScene().getWindow());
        chImage(file);
    }
    public void chImage(File file) {
        if (file != null){
            image.setImage(new Image(file.toURI().toString()));
        }
    }

}
