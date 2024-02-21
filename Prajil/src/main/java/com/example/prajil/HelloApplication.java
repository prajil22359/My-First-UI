package com.example.prajil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("StickHero");
//        stage.setWidth(500);
//        stage.setHeight(600);
        stage.setResizable(false);

        // changing icon
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.jpg"))); // null huna diyena.
        stage.getIcons().add(icon);
        //

        stage.setScene(scene);
        stage.show();

        GameOver.setStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}