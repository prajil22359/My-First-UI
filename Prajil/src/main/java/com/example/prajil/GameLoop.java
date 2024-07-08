package com.example.prajil;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.*;
import java.util.Objects;
import java.util.Random;
import static com.example.prajil.GameOver.stage;


public class GameLoop implements Serializable{
    transient private HelperStick helperStick;
    transient private HelperHero helperHero;
    transient private HelperPlatform helperTargetPlatform;
    transient private HelperPlatform helperBasePlatform;
    transient TextField score;
    transient TextField cherryCount;
    transient ImageView cherryToPick;
    transient Timeline timelineForAll;
    int cherryNo;

    GameLoop  (HelperStick helperStick, HelperHero helperHero, HelperPlatform helperTargetPlatform, HelperPlatform helperBasePlatform, TextField score, ImageView cherryToPick, TextField cherryCount) {
        this.helperStick = helperStick;
        this.helperHero = helperHero;
        this.helperBasePlatform = helperBasePlatform;
        this.helperTargetPlatform = helperTargetPlatform;
        helperHero.setGameLoop(this);
        helperHero.setHelperTargetPlatform(helperTargetPlatform);
        helperHero.setHelperBasePlatform(helperBasePlatform);

        this.score=score;
        this.cherryToPick = cherryToPick;
        this.cherryCount = cherryCount;


        loadCherryCount();

    }

    public void saveCherryCount() { // serialize
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("cherryCount.txt"))) {
            System.out.println(this.cherryNo);
            this.cherryNo = Integer.parseInt(cherryCount.getText());
            System.out.println(cherryNo);
            outputStream.writeObject(this);
            System.out.println("Cherry count saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCherryCount() {  //deserialize
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("cherryCount.txt"))) {
            GameLoop loadedGameLoop = (GameLoop) inputStream.readObject();
            this.cherryNo = loadedGameLoop.cherryNo;
//            System.out.println(cherryNo);
            this.cherryCount.setText(String.valueOf(cherryNo));
        } catch (IOException | ClassNotFoundException e) {
            // Handle the case when the file doesn't exist or cannot be read
            System.out.println("Could not load cherry count from file");
        }
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

    //pahilo choti ko lagi ta base platform ni banaunu paryo, but then target platform converts to base, only one new platform is created.
    //the base platform and player moves back till it get to the initial stick ko position. background ni same time aghi badcha
    // layout of base platform + width = layout of stick.

    public void loop(){
        helperHero.setHeroRotatable(false);
        cherryToPick.setVisible(false);
        timelineForAll= new Timeline(new KeyFrame( Duration.millis(2.5), e->movingTheNodes()));
        timelineForAll.setCycleCount(Timeline.INDEFINITE);
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

        /////////////////////////////////////////
        helperBasePlatform=helperTargetPlatform;
                       ///////////
        helperTargetPlatform=new HelperPlatform(rectangle);
        ////////////////////////////////////////

        Random random=new Random();
        double targetWidth =random.nextInt(120)+30;
        helperTargetPlatform.setPlatformSize(targetWidth);
        double targetPosition = helperStick.getX()+ 30+ random.nextInt( 272 - (int) targetWidth);
        helperTargetPlatform.setPlatformX(targetPosition);

        helperHero.setGapBtwnPlatforms(helperTargetPlatform.getPlatformX()- (helperBasePlatform.getPlatformX()+ helperBasePlatform.getPlatformSize()));
        helperHero.setHelperTargetPlatform(helperTargetPlatform);
        helperHero.setHelperBasePlatform(helperBasePlatform);

        Pane root = (Pane) stage.getScene().getRoot();
        root.getChildren().add(rectangle);
    }

    public void placeCherry(){

        Random random = new Random();

        // Increase probability of visibility: 70% chance to be visible
        boolean isVisible = random.nextDouble() < 0.7; // 70% chance
        cherryToPick.setVisible(isVisible);

        if(isVisible) {
            // Define the range
            double max = helperTargetPlatform.getPlatformX() - cherryToPick.getFitWidth();
            double min = (helperBasePlatform.getPlatformX() + helperBasePlatform.getPlatformSize());

            // Generate a random number between min (inclusive) and max (exclusive)
            double randomDouble = min + (max - min) * random.nextDouble();
            cherryToPick.setLayoutX(randomDouble);
        }
    }

    public void movingTheNodes(){
        // till the nodes don't get to the initial position, timeline doesnot stop
        // and else part will work for moving them back.
        if(helperTargetPlatform.getPlatformX()+helperTargetPlatform.getPlatformSize()==123){
            timelineForAll.stop();
            setHelperStick(helperStick.resetStick());
            createNewPLatform();
            placeCherry();

            // as everything is reset for the game to continue with stick growing available,
            // hero's made unable to go up or down the stick
//            helperHero.setHeroRotatable(false);
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

    public void increaseScoreInPlayScene( int delta){
        score.setText(String.valueOf(Integer.parseInt(score.getText())+ delta));
    }

    public int getCurrentScore(){
        return Integer.parseInt(score.getText());
    }

    public boolean isHeroOnPlatform( HelperPlatform platformX) {
        // Get the bounds in parent coordinates
        Bounds heroBounds = helperHero.hero.getBoundsInParent();
        Bounds platformBounds = platformX.platform.getBoundsInParent();

        // Define a tolerance value
        final double tolerance = 15.0; // Adjust this value as needed

        // Check horizontal bounds with tolerance
        boolean withinHorizontalBounds = heroBounds.getMinX() < platformBounds.getMaxX() - tolerance &&
                heroBounds.getMaxX() > platformBounds.getMinX() + tolerance;

        return withinHorizontalBounds;
    }

    public void checkIfCherryEaten(){
        Bounds heroBounds = helperHero.hero.getBoundsInParent();
        Bounds cherryBounds = cherryToPick.getBoundsInParent();
        boolean doesHeIntersect =  heroBounds.intersects(cherryBounds);
        if ((doesHeIntersect == true) && (helperHero.isHeroUp() == false) && (cherryToPick.isVisible()) ){
            System.out.println("YES HE DOES");

            AudioClip cherryEatenSound = new AudioClip(getClass().getResource("apple_bite.mp3").toString());
            cherryEatenSound.setVolume(1.0);
            cherryEatenSound.setRate(5.0);
            cherryEatenSound.play();

            cherryToPick.setVisible(false);
            increaseScoreInPlayScene(2);
            increaseCherryCount();
        }
    }

    public int getCherryCount(){
        return Integer.parseInt(cherryCount.getText());
    }
    public void increaseCherryCount(){
        cherryCount.setText(String.valueOf(Integer.parseInt(cherryCount.getText()) + 1));
    }

}

