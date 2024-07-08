package com.example.prajil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import javafx.scene.media.AudioClip;

public class HelloApplication extends Application {

    static AudioClip bgMusic;
    static boolean isBgMusicPlaying = false;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("StickHero");
        stage.setResizable(false);

        // changing icon
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.jpg"))); // null huna diyena.
        stage.getIcons().add(icon);

        // Set the scene
        stage.setScene(scene);

        // Show the stage
        stage.show();
        GameOver.setStage(stage);

        bgMusic= new AudioClip(getClass().getResource("arcade.mp3").toString());
        bgMusic.play(0.2);
        isBgMusicPlaying = true;
    }


    public static void main(String[] args) {
        launch();
    }
}
