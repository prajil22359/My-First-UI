package com.example.prajil;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;
import static com.example.prajil.GameOver.stage;

public class GameLoop {
    private HelperStick helperStick;
    private HelperHero helperHero;
    private HelperPlatform helperTargetPlatform;
    private HelperPlatform helperBasePlatform;
    TextField score;


    Timeline timelineForAll;

    GameLoop(HelperStick helperStick, HelperHero helperHero, HelperPlatform helperTargetPlatform, HelperPlatform helperBasePlatform, TextField score) {
        this.helperStick = helperStick;
        this.helperHero = helperHero;
        this.helperBasePlatform = helperBasePlatform;
        this.helperTargetPlatform = helperTargetPlatform;
        helperHero.setGameLoop(this);
        helperHero.setHelperTargetPlatform(helperTargetPlatform);
        this.score=score;
    }

    public void generatePlatforms(){
        // base
        Random random=new Random();
            double baseWidth = random.nextInt(73) + 50;
            helperBasePlatform.setPlatformSize(baseWidth);
            double basePosition = helperStick.getX() - baseWidth;
            helperBasePlatform.setPlatformX(basePosition);

        //425 - 123 = 302, 30 minimum gap, bacheko 272 bata width hataera (taaki target platform purai atos),
        //targetPlatform position garincha

        //target

        double targetWidth =random.nextInt(120)+30;
        helperTargetPlatform.setPlatformSize(targetWidth);
        double targetPosition = helperStick.getX()+ 30+ random.nextInt( 272 - (int) targetWidth);
        helperTargetPlatform.setPlatformX(targetPosition);
        helperHero.setGapBtwnPlatforms(helperTargetPlatform.getPlatformX()- (helperBasePlatform.getPlatformX()+ helperBasePlatform.getPlatformSize()));

    }

    //pahilo choti ko lagi ta base platofrm ni banaunu paryo, but then target platform converts to base, only one new platform is created.
    //the base platform and player moves back till it get to the initial stick ko position. background ni same time aghi badcha
    // layout of base platform + width = layout of stick.

    public void loop(){

        timelineForAll= new Timeline(new KeyFrame( Duration.millis(2.5), e->movingTheNodes()));
        timelineForAll.setCycleCount(Timeline.INDEFINITE);
//        timelineForAll.setOnFinished(e-> helperStick.resetStick());
        timelineForAll.play();
    }

    private void createNewPLatform() {
        Rectangle rectangle = new Rectangle();
        rectangle.setX(260.0);
        rectangle.setY(327.0);
        rectangle.setWidth(90.0);
        rectangle.setHeight(200.0);
        rectangle.setArcWidth(5.0);
        rectangle.setArcHeight(5.0);
        rectangle.setFill(Color.web("#111213"));
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        helperBasePlatform=helperTargetPlatform;
        helperTargetPlatform=new HelperPlatform(rectangle);

        Random random=new Random();
        double targetWidth =random.nextInt(120)+30;
        helperTargetPlatform.setPlatformSize(targetWidth);
        double targetPosition = helperStick.getX()+ 30+ random.nextInt( 272 - (int) targetWidth);
        helperTargetPlatform.setPlatformX(targetPosition);

        helperHero.setGapBtwnPlatforms(helperTargetPlatform.getPlatformX()- (helperBasePlatform.getPlatformX()+ helperBasePlatform.getPlatformSize()));
        helperHero.setHelperTargetPlatform(helperTargetPlatform);

        Pane root = (Pane) stage.getScene().getRoot();
        root.getChildren().add(rectangle);
    }

    public void movingTheNodes(){
        if(helperTargetPlatform.getPlatformX()+helperTargetPlatform.getPlatformSize()==123){
            System.out.println("false");
            timelineForAll.stop();
            setHelperStick(helperStick.resetStick());
            createNewPLatform();

        }
        else {
            helperBasePlatform.setPlatformX(helperBasePlatform.getPlatformX() - 1);
            helperTargetPlatform.setPlatformX(helperTargetPlatform.getPlatformX() - 1);
            helperHero.setX(helperHero.getX() - 1);
            helperStick.setX(helperStick.getX() - 1);
        }
    }

    public void setHelperStick(HelperStick helperStick){
        this.helperStick=helperStick;
    }

    public void increaseScoreInPlayScene(){
        score.setText(String.valueOf(Integer.parseInt(score.getText())+1));
    }

    public int getCurrentScore(){
        return Integer.parseInt(score.getText());
    }

}
