package com.example.prajil;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class PlaySceneController implements Initializable {

    @FXML
    Stage stage;
    @FXML
    Scene scene;
    @FXML
    Parent root;

    Timeline stickGrowTimeline;

    @FXML
    Line stick;
    private HelperStick helperStick;

    @FXML
    ImageView hero;
    private HelperHero helperHero;

    @FXML
    Rectangle basePlatform;
    private HelperPlatform helperBasePlatform;

    @FXML
    Rectangle targetPlatform;
    private HelperPlatform helperTargetPlatform;

    @FXML
    TextField score;

    GameLoop gameLoop;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helperHero = new HelperHero(hero);
        helperStick = new HelperStick(stick );
        helperHero.setHelperStick(helperStick);
        helperStick.setHelperHero(helperHero);
        // stick and hero both need references of each other, euta lai pachi initialize garidiyo.

        helperBasePlatform =new HelperPlatform(basePlatform);
        helperTargetPlatform = new HelperPlatform(targetPlatform);
//        helperHero.setHelperTargetPlatform(helperTargetPlatform);


        gameLoop= new GameLoop(helperStick, helperHero, helperTargetPlatform, helperBasePlatform, score);
        gameLoop.generatePlatforms();
    }

    @FXML
    public void switchToPause(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("pauseScene.fxml")));
        stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void growStick(){
        // if condition, to not let the stick grow again if we press mouse again
        stickGrowTimeline = new Timeline(new KeyFrame(Duration.millis(2.5), e -> {helperStick.increaseStickLength();}));
        stickGrowTimeline.setCycleCount(Timeline.INDEFINITE);
        if(!helperStick.isAlreadyStickGrown()) {
            stickGrowTimeline.play();
        }
    }

    @FXML
    public void releaseStick(){
        if(!helperStick.isAlreadyStickGrown()) {
            stickGrowTimeline.stop();            // stick growing stops
            helperStick.setAlreadyStickGrown(true);
            helperStick.rotateStick();           // stick rotates
        }
    }

}


