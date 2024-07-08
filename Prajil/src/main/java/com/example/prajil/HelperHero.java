package com.example.prajil;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.IOException;

public class HelperHero {
    ImageView hero;
    HelperStick helperStick;
    HelperPlatform helperTargetPlatform;
    HelperPlatform helperBasePlatform;
    private boolean heroRotatable;
    private boolean heroUp;
    GameLoop gameLoop;
    double gapBtwnPlatforms;
    double initialPosition;
    Timeline walkTimeline;
//    Timeline fallTimeline = new Timeline();
//    Timeline rotateTimeline = new Timeline();


    public HelperHero(ImageView hero){
        this.hero=hero;

        this.heroRotatable=false;
        this.heroUp=true;

        initialPosition=hero.getX();
    }

    public void setHelperStick(HelperStick helperStick) {
        this.helperStick = helperStick;
    }
    public void setHelperTargetPlatform(HelperPlatform helperTargetPlatform) {this.helperTargetPlatform=helperTargetPlatform;}
    public void setHelperBasePlatform(HelperPlatform helperBasePlatform) {this.helperBasePlatform=helperBasePlatform;}

    
    public void setGapBtwnPlatforms(double gapBtwnPlatforms) {this.gapBtwnPlatforms=gapBtwnPlatforms;}
    public double getGapBtwnPlatforms() {return this.gapBtwnPlatforms;}
    // 2 cases
       //if stick length is less than gap or more than gap+ target platform width,
    //      travel through the stick and then taking one more step, fall.
      // else if within the target platform( not less than gap, not more than gap + platform width ),
    //      travel till the end of target platform, i.e. move gap+ targetplatform width


    public void walk(){

        if(gameLoop.isHeroOnPlatform(helperBasePlatform) || gameLoop.isHeroOnPlatform(helperTargetPlatform)  ){
            setHeroRotatable(false);
        }
        else{
            setHeroRotatable(true);
        }

        hero.setX(hero.getX()+1);

        // check if he eats the cherry
        gameLoop.checkIfCherryEaten();

        // check if he hits the platform. i.e. character is down faced and reaches platform.
        if(hero.getLayoutX() + hero.getX() + 50 ==(helperTargetPlatform.getPlatformX())  && heroUp == false ){
            System.out.println(" yes hit on the platform");
            walkTimeline.stop();
            setHeroRotatable(false);
            fall();
        }

    }


    public void moves() {

        walkTimeline=new Timeline(new KeyFrame(Duration.millis(3),e->{walk();}));
        int error=1; // itna dikhta nahi he.

        AudioClip runningSound = new AudioClip(getClass().getResource("running.mp3").toString());
        runningSound.setRate(2.0);
        runningSound.setVolume(1.0);
        runningSound.play();

        //when hero has to fall
        if(helperStick.getSize()<this.gapBtwnPlatforms-error | helperStick.getSize()>(this.gapBtwnPlatforms+ helperTargetPlatform.getPlatformSize()+error)){
            walkTimeline.setCycleCount((int)helperStick.getSize()+50);
            // 50 is width of character, for allowing the whole character fall.
            walkTimeline.play();
            walkTimeline.setOnFinished(e->{
                runningSound.stop();
                fall();
            });
        }

        else{
        // when hero does not fall
            walkTimeline.setCycleCount((int)gapBtwnPlatforms+(int)helperTargetPlatform.getPlatformSize());
            walkTimeline.play();
            walkTimeline.setOnFinished(e-> {
                runningSound.stop();
                gameLoop.loop();
            });
            gameLoop.increaseScoreInPlayScene(1);
        }

    }

    public void setX(double x){
        hero.setX(x);
    }
    public double getX() {return hero.getX();}

    public void fall(){

        Timeline stickFall = new Timeline(new KeyFrame(Duration.millis(3), e -> helperStick.rotatingAngleByAngle()));
        stickFall.setCycleCount(60);

        AudioClip fallSound = new AudioClip(getClass().getResource("stickHitSound.mp3").toString());


        Timeline fallTimeline= new Timeline(new KeyFrame(Duration.millis(2),
                e->{
                    hero.setY(hero.getY()+1);
                    hero.setRotate(hero.getRotate() + 1); // Increase the angle gradually
                }));

        fallTimeline.setCycleCount(300);
        stickFall.play();
        fallTimeline.play();
        fallSound.play(100);

        fallTimeline.setOnFinished(e-> {
            try {
                gameLoop.saveCherryCount();
                new GameOver(gameLoop.getCurrentScore());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public boolean isHeroRotatable() {
        return heroRotatable;
    }

    public void setHeroRotatable(boolean heroRotatable) {
        this.heroRotatable = heroRotatable;
    }

    public boolean isHeroUp() {
        return heroUp;
    }

    public void setHeroUp(boolean heroUp) {
        this.heroUp = heroUp;
    }

    public void setGameLoop(GameLoop gameLoop){
        this.gameLoop=gameLoop;
    }

}

