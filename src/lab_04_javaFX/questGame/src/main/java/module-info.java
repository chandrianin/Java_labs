module com.example.questGame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.questGame to javafx.fxml;
    exports com.example.questGame;
}