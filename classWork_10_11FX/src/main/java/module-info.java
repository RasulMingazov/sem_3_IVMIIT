module org.example {
    requires javafx.controls;
    requires javafx.fxml;


    opens src to javafx.fxml;
    exports src;
}