package com.company;

import javax.swing.*;
import java.awt.*;

public class Alien{
    private boolean alive;
    private int posx;
    private int posy;
    private int oriposx;
    private boolean moveL;
    private boolean moveR = true;
    private boolean dying = false;
    private int blink;
    private Image image;
    private int hit;
    private Bomb bomb;

    public Alien(int x, int y){
        posx = x;
        posy = y;
        alive = true;
        oriposx = x;
        hit = 0;
        var playerImg = "images/alien.png";
        var ii = new ImageIcon(playerImg);

        image = ii.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT);
        //setImage(ii.getImage());


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

    public boolean isAlive() {
        return alive;
    }

    public int getOriposx() {
        return oriposx;
    }

    public boolean isMoveL() {
        return moveL;
    }

    public boolean isMoveR() {
        return moveR;
    }

    public void setMoveL(boolean moveL) {
        this.moveL = moveL;
    }

    public void setMoveR(boolean moveR) {
        this.moveR = moveR;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Image getImage(){
        return image;

    }

    public void setDying(boolean dying) {
        if (dying)
            blink = 10;

        this.dying = dying;
    }

    public boolean isDying() {
        return dying;
    }

    public int getBlink(){
        return blink;
    }

    public void setBlink(int blink) {
        this.blink = blink;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getHit() {
        return hit;
    }

    public Bomb getBomb() {
        return bomb;
    }

    public void InitBomb(){
        bomb = new Bomb(posx,posy);
    }

    public void setbomb(){
        bomb = null;
    }

    public class Bomb {
        private int x;
        private int y;
        private Image image;


        public Bomb (int x, int y){
            this.x = x;
            this.y = y;

            var bombImg = "images/bomb.png";
            var ii = new ImageIcon(bombImg);

            image = ii.getImage().getScaledInstance(10,10, Image.SCALE_DEFAULT);
        }

        public int getX(){
            return x;
        }

        public int getY(){
            return y;
        }

        public Image getImage() {
            return image;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
