package com.example.questGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GoodbyeView extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("goodbye-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 230, 230);
        stage.setTitle("Результат");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
