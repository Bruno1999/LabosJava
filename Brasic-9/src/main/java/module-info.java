module com.example.brasic7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.brasic7 to javafx.fxml;
    exports com.example.brasic7;
}