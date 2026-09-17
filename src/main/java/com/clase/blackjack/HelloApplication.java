package com.clase.blackjack;

import Vista.Vista;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        Vista root = new Vista();

        Scene scene = new Scene(root, 1000, 850);

        stage.setFullScreen(true);

        stage.setTitle("Mesa de Blackjack");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}