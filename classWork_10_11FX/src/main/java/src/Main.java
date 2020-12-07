package src;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root=FXMLLoader.load(getClass().getResource("edit.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
