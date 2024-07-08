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
import javafx.scene.media.AudioClip;
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

    @FXML
    TextField cherryCount;

    @FXML
    ImageView cherryToPick;

    GameLoop gameLoop;


    private AudioClip buttonClickSound = new AudioClip(getClass().getResource("click-button.mp3").toString());;
    private AudioClip stickGrowSound = new AudioClip(getClass().getResource("stickSound.mp3").toString());
    Timeline growSoundTimeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helperHero = new HelperHero(hero);
        helperStick = new HelperStick(stick );
        helperHero.setHelperStick(helperStick);
        helperStick.setHelperHero(helperHero);
        // stick and hero both need references of each other, euta lai pachi initialize garidiyo.

        helperBasePlatform =new HelperPlatform(basePlatform);
        helperTargetPlatform = new HelperPlatform(targetPlatform);

        cherryToPick.setVisible(false);
        this.cherryCount.setEditable(false);
        this.score.setEditable(false);

        gameLoop= new GameLoop(helperStick, helperHero, helperTargetPlatform, helperBasePlatform, score, cherryToPick, cherryCount);
        gameLoop.generatePlatforms();

    }

    @FXML
    public void switchToPause(ActionEvent event) throws IOException {
        buttonClickSound.play();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("pauseScene.fxml")));
        stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void growStick(){
        buttonClickSound.play();
        // if condition, to not let the stick grow again if we press mouse again
        stickGrowTimeline = new Timeline(new KeyFrame(Duration.millis(1.5), e -> {helperStick.increaseStickLength();}));
        stickGrowTimeline.setCycleCount(Timeline.INDEFINITE);

        growSoundTimeline = new Timeline();
        KeyFrame keyFrameGrowSound = new KeyFrame(Duration.seconds(0.05), e-> stickGrowSound.play(0.5));
        growSoundTimeline.getKeyFrames().add(keyFrameGrowSound);
        growSoundTimeline.setCycleCount(Timeline.INDEFINITE);

        if(!helperStick.isAlreadyStickGrown()) {
            growSoundTimeline.play();
            stickGrowTimeline.play();
        }
    }

    @FXML
    public void releaseStick(){
        if(!helperStick.isAlreadyStickGrown()) {
            stickGrowTimeline.stop();            // stick growing stops
            growSoundTimeline.stop();
            helperStick.setAlreadyStickGrown(true);
            helperStick.rotateStick();           // stick rotates
        }
    }

    @FXML
    public void tryRotatingHero(){
        if(helperHero.isHeroRotatable())
        {
//            System.out.println("tried");
            buttonClickSound.play();
            Timeline timeline;

            if(helperHero.isHeroUp()){
                // if hero is up, clicking would take it down.
                timeline= new Timeline(new KeyFrame(Duration.millis(0.1), e->{hero.setY(hero.getY()+1);}));
            }
            else{
                timeline= new Timeline(new KeyFrame(Duration.millis(0.1), e->{hero.setY(hero.getY()-1);}));
            }
            timeline.setCycleCount((int)helperHero.hero.getFitHeight()-7); // to fit in 7 use gareko ho.
            timeline.play();

            // toggling hero's position between up and down
            timeline.setOnFinished(e -> helperHero.setHeroUp(!helperHero.isHeroUp()));

        }
    }

}


