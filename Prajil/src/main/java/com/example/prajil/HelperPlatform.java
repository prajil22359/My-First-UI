package com.example.prajil;

import javafx.scene.shape.Rectangle;

public class HelperPlatform {
    Rectangle platform;
    public HelperPlatform(Rectangle platform){
        this.platform=platform;
    }

    public double getPlatformSize(){
        return platform.getWidth();
    }
    public void setPlatformSize(double width){
        platform.setWidth(width);
    }
    public double getPlatformX(){
        return platform.getX();
    }
    public void setPlatformX(double x){
        platform.setX(x);
    }

}
