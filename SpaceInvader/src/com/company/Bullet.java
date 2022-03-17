package com.company;

import javax.swing.*;
import java.awt.*;

public class Bullet {
    private int posx;
    private int posy;
    private boolean hit = false;
    private Image image;

    public Bullet(int x,int y){
        posx = x;
        posy = y;

        var playerImg = "images/bullet.png";
        var ii = new ImageIcon(playerImg);

        image = ii.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT);
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return posy;
    }

    public int getPosx() {
        return posx;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public Image getImage() {
        return image;
    }
}
