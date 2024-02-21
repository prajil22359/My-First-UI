package com.example.prajil;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;

public class HelperHero {
    ImageView hero;
    HelperStick helperStick;
    HelperPlatform helperTargetPlatform;

    GameLoop gameLoop;
    double gapBtwnPlatforms;
    double initialPosition;
    Timeline walkTimeline = new Timeline();
    Timeline fallTimeline = new Timeline();


    public HelperHero(ImageView hero){
        this.hero=hero;
        initialPosition=hero.getX();
    }

    public void setHelperStick(HelperStick helperStick) {
        this.helperStick = helperStick;
    }
    public void setHelperTargetPlatform(HelperPlatform helperTargetPlatform) {this.helperTargetPlatform=helperTargetPlatform;}
    public void setGapBtwnPlatforms(double gapBtwnPlatforms) {this.gapBtwnPlatforms=gapBtwnPlatforms;}
    public double getGapBtwnPlatforms() {return this.gapBtwnPlatforms;}
    // 2 cases
       //if stick length is less than gap or more than gap+ target platform width,
    //      travel through the stick and then taking one more step, fall.
      // else if within the target platform( not less than gap, not more than gap + platform width ),
    //      travel till the end of target platform, i.e move gap+ targetplatform width

    public void moves() {
        walkTimeline=new Timeline(new KeyFrame(Duration.millis(2),e->{hero.setX(hero.getX()+1);}));
        int error=1; // itna dikhta nahi he.
//        System.out.println(helperStick.getSize());
//        System.out.println(this.gapBtwnPlatforms);
//        System.out.println("hero"+helperTargetPlatform.getPlatformSize());

        //  if button pressed when moving, I should make the hero to move upside down.


        //when hero has to fall
        if(helperStick.getSize()<this.gapBtwnPlatforms-error | helperStick.getSize()>(this.gapBtwnPlatforms+ helperTargetPlatform.getPlatformSize()+error)){
            walkTimeline.setCycleCount((int)helperStick.getSize()+50);
            walkTimeline.play();
            walkTimeline.setOnFinished(e->fall());

        }

        else{
        // when hero does not fall
            walkTimeline.setCycleCount((int)gapBtwnPlatforms+(int)helperTargetPlatform.getPlatformSize());
            walkTimeline.play();
            walkTimeline.setOnFinished(e-> gameLoop.loop());
            gameLoop.increaseScoreInPlayScene();

        }
    }

    public void setX(double x){
        hero.setX(x);
    }
    public double getX() {return hero.getX();}

    public void fall(){
        fallTimeline= new Timeline(new KeyFrame(Duration.millis(2), e->{hero.setY(hero.getY()+1);}));
        fallTimeline.setCycleCount(300);
        fallTimeline.play();
        fallTimeline.setOnFinished(e-> {
            try {
                new GameOver(gameLoop.getCurrentScore());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    public void setGameLoop(GameLoop gameLoop){
        this.gameLoop=gameLoop;
    }

}

