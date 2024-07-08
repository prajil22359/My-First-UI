package com.example.prajil;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.io.IOException;
import java.security.Key;
import java.util.Objects;

//import static com.example.prajil.GameOver.stage;
import static com.example.prajil.GameOver.stage;
import static java.lang.Math.abs;

public class HelperStick {
    private Line stick;
    private HelperHero helperHero;
    private boolean alreadyStickGrown;
    private double size;


    public HelperStick(Line stick) {
        this.stick=stick;
        alreadyStickGrown=false;
    }


    public void increaseStickLength(){
//
//        System.out.println("hey"+stick.getEndY());
        stick.setEndY(stick.getEndY()-1);
    }

    public boolean isAlreadyStickGrown() {
        return alreadyStickGrown;
    }

    public void setAlreadyStickGrown(boolean alreadyStickGrown) {
        this.alreadyStickGrown = alreadyStickGrown;
    }

    //for rotating the stick
    public void rotateStick() {
        Timeline rotationTimeline = new Timeline(new KeyFrame(Duration.millis(5), e -> rotatingAngleByAngle()));
        rotationTimeline.setCycleCount(90);
        rotationTimeline.play();
        rotationTimeline.setOnFinished(e->helperHero.moves());     // character moves;
        // hero should be able to rotate now.
    }

    public void rotatingAngleByAngle() {

        Rotate rotateStick = new Rotate(1, stick.getStartX(), stick.getStartY());
        stick.getTransforms().add(rotateStick);
    }

    public double getSize(){
        return -stick.getEndY();
    }
    public double getX(){
        return stick.getLayoutX();
    }
    public void setX(double x){stick.setLayoutX(x);}

    public void setHelperHero(HelperHero helperHero) {
        this.helperHero=helperHero;
    }
    public HelperStick resetStick(){
        System.out.println("yes");

        Line line = new Line();
        line.setLayoutX(123.0);
        line.setLayoutY(330.0);
        line.setStrokeWidth(5.0);
        Pane root = (Pane) stick.getScene().getRoot();

        root.getChildren().add(line);
        this.stick=line;

        setAlreadyStickGrown(false);
        return this;
    }
}

