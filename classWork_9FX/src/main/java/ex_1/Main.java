package ex_1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main extends Application {
    private final Map<String, String> users = map();

    private static Map<String, String> map() {
        Map<String, String> users = new HashMap<>();
        users.put("yourboss", "1000000");
        users.put("cheburashka", "gena");
        users.put("igogo","ogogi");
        users.put("iminmaxa", "0000001");
        users.put("bibigonchu","777777");
        users.put("kis","milk");
        users.put("rediska","vegetable");
        users.put("milly","on");
        return users;
    }
    private boolean checkUser(String login, String pass) {
        Set<Map.Entry<String, String>> set = users.entrySet();

        for (Map.Entry<String, String> user : set) {
            if (login.equals(user.getKey()) && pass.equals(user.getValue())) {
                return true;
            }
        }
        return false;
    }

    TextField fieldLogin;
    PasswordField fieldPassword;

    @Override
    public void start(Stage stage) {
        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(25);
        root.setVgap(15);

        Label labelT = new Label("Введите свой логин и пароль");
        root.add(labelT, 0, 0, 2, 1);

        Label labelLogin = new Label("login");
        fieldLogin = new TextField();

        Label labelPassword = new Label("password");
        fieldPassword = new PasswordField();

        Button mainOkButton = new Button("Ok");
        mainOkButton.setPrefWidth(60);
        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(60);

        GridPane.setHalignment(labelLogin, HPos.RIGHT);
        GridPane.setHalignment(labelLogin, HPos.RIGHT);
        GridPane.setHalignment(mainOkButton, HPos.RIGHT);
        GridPane.setHalignment(fieldLogin, HPos.LEFT);
        GridPane.setHalignment(fieldPassword, HPos.LEFT);
        GridPane.setHalignment(cancelButton, HPos.LEFT);
        root.add(labelLogin, 0, 1);
        root.add(labelPassword,0,2);
        root.add(mainOkButton, 0, 3);
        root.add(fieldLogin, 1, 1);
        root.add(fieldPassword, 1, 2);
        root.add(cancelButton, 1, 3);

        Scene scene = new Scene(root, 300, 190);
        stage.setTitle("Вход");
        stage.setScene(scene);
        stage.show();

        mainOkButton.setOnAction(new EventHandler<ActionEvent>() {
            int k = 0;
            @Override
            public void handle(ActionEvent event) {
                String login = fieldLogin.getText();
                String pass = fieldPassword.getText();
                if (checkUser(login, pass)) {
                    GridPane root = new GridPane();
                    root.setPadding(new Insets(20));
                    root.setHgap(25);
                    root.setVgap(15);

                    Label sucLabel = new Label("Есть такой пользователь");
                    Button okButton = new Button("Ok");
                    okButton.setPrefWidth(60);

                    GridPane.setHalignment(sucLabel, HPos.LEFT);
                    GridPane.setHalignment(okButton, HPos.RIGHT);
                    root.add(sucLabel, 0, 0);
                    root.add(okButton, 1, 1);

                    Scene scene = new Scene(root, 300, 100);
                    Stage sucWindow = new Stage();
                    sucWindow.setScene(scene);
                    sucWindow.initModality(Modality.WINDOW_MODAL);

                    sucWindow.initOwner(stage);

                    sucWindow.setX(stage.getX() + 200);
                    sucWindow.setY(stage.getY() + 100);

                    sucWindow.show();

                    okButton.setOnAction(eventHello -> {

                        Label helloLabel = new Label("Приветствую Вас, " + login);
                        helloLabel.setTextFill(Color.web("#5b9432"));

                        StackPane root1 = new StackPane();
                        root1.getChildren().add(helloLabel);
                        Scene secondScene = new Scene(root1, 230, 100);

                        stage.setTitle("Вы в системе");
                        stage.setScene(secondScene);
                        sucWindow.close();
                    });
                }
                else {
                    if (++k < 3) {
                        GridPane root = new GridPane();
                        root.setPadding(new Insets(20));
                        root.setHgap(25);
                        root.setVgap(15);

                        Label loseLabel = new Label("Пользователь не найден или неверный пароль");
                        Button closeButton = new Button("Close");

                        GridPane.setHalignment(loseLabel, HPos.LEFT);
                        GridPane.setHalignment(closeButton, HPos.LEFT);

                        root.add(loseLabel, 0,0);
                        root.add(closeButton, 2,2);

                        Scene secondScene = new Scene(root, 420, 120);

                        Stage errorWindow = new Stage();
                        errorWindow.setTitle("Error");
                        errorWindow.setScene(secondScene);
                        errorWindow.initModality(Modality.WINDOW_MODAL);
                        errorWindow.initOwner(stage);
                        errorWindow.setX(stage.getX() + 200);
                        errorWindow.setY(stage.getY() + 100);
                        errorWindow.show();

                        closeButton.setOnAction(eventClose -> errorWindow.close());
                    }
                    else {
                        stage.close();
                    }
                }
            }
        });
        cancelButton.setOnAction(event -> stage.close());
    }
}