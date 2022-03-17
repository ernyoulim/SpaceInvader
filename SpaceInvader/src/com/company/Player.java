package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class Player  {
    private int posx;
    private int posy;
    private int hit;
    private Image image;
    private int width;


    public  Player(int x,int y){
        posx = x;
        posy = y;
        width = 20;

        var playerImg = "images/player.png";
        var ii = new ImageIcon(playerImg);

        image = ii.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT);
        //setImg(ii.getImage());
    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public void setImg(Image img){
        image = img;
    }

    public Image getImage(){
        return image;
    }

    public int getWidth() {
        return width;
    }
}