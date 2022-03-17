package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Board extends JPanel implements Runnable, MouseListener {
    int ALIEN_INIT_X = 150;
    int ALIEN_INIT_Y = 5;
    int ALIEN_SIZE = 20;
    boolean ingame = true;
    private Dimension d;
    int BOARD_WIDTH=500;
    int BOARD_HEIGHT=500;
    int kill = 0;
    private Thread animator;
    Player player;
    private Bullet bullet;
    private List<Alien> aliens;
    private boolean draw = true;
    private String message = "Game Over";
    private int life = 3;

    public Board(){


            addKeyListener(new TAdapter());
            addMouseListener(this);
            setFocusable(true);
            d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
            setBackground(Color.black);
            player = new Player(BOARD_WIDTH/2,BOARD_HEIGHT-30) ;
            aliens = new ArrayList<>();

            //create 24 allien;
            for(int i=0; i<4; i++)
                for (int j=0; j<6; j++){
                    Alien alien = new Alien(ALIEN_INIT_X + j  * ALIEN_SIZE + j * 20,ALIEN_INIT_Y + i  * ALIEN_SIZE + i * 5) ;
                    aliens.add(alien);
                }



            if (animator == null || !ingame) {
                animator = new Thread(this);
                animator.start();
            }


            setDoubleBuffered(true);

    }

    public void paint(Graphics g)
    {
        super.paint(g);
        if (!ingame || kill == aliens.size() ) {
            gameOver(g);

        }else {

            g.setColor(Color.white);
            g.fillRect(0, 0, d.width, d.height);

            //player

       /* g.setColor(Color.red);
        g.fillRect(player.getPosx(),player.getPosy(),20,20);*/

            //insert rocket image to player
            g.drawImage(player.getImage(), player.getPosx(), player.getPosy(), this);

            //Create Alien
            for (Alien alien : aliens) {
                if (!alien.isAlive())
                    continue;

                if (bullet != null) {
                    if (alien.getPosx() <= bullet.getPosx() && bullet.getPosx() <= alien.getPosx() + 20 && alien.getPosy() <= bullet.getPosy() && bullet.getPosy() <= alien.getPosy() + 20) {
                        //alien.setAlive(false);
                        alien.setDying(true);
                        bullet = null;
                        alien.setHit(alien.getHit()+1);
                    }
                }

                if (alien.getHit() == 2){
                    alien.setAlive(false);
                    continue;
                }

                //g.setColor(Color.black);
                //g.fillRect(alien.getPosx(), alien.getPosy(), ALIEN_SIZE, ALIEN_SIZE);

                //check if alien got shot and should blink
                if (alien.isDying() && alien.getBlink()%2==0){
                        draw = false;
                }
                if (draw)
                    g.drawImage(alien.getImage(), alien.getPosx(), alien.getPosy(), this);

                if (alien.getBlink() != 0)
                    alien.setBlink(alien.getBlink()-1);
                else {
                    alien.setDying(false);
                }

                if (alien.getPosx() <= alien.getOriposx() + 50 && alien.isMoveR()) {
                    alien.setPosx(alien.getPosx() + 1);
                } else if (alien.getPosx() >= alien.getOriposx() + 50 && alien.isMoveR()) {
                    alien.setMoveR(false);
                    alien.setMoveL(true);
                    alien.setPosy(alien.getPosy() + 1);
                } else if (alien.getPosx() >= alien.getOriposx() - 50 && alien.isMoveL()) {
                    alien.setPosx(alien.getPosx() - 1);
                } else if (alien.getPosx() <= alien.getOriposx() - 50 && alien.isMoveL()) {
                    alien.setMoveL(false);
                    alien.setMoveR(true);
                    alien.setPosy(alien.getPosy() + 1);

                }
                if (alien.getBomb() == null){
                    Random random = new Random();
                    int num = random.nextInt(2500);
                    //System.out.println(num);
                    if (num == 5 ){
                       alien.InitBomb();
                       drawBomb(g,alien.getBomb());
                    }
                }else{
                    Alien.Bomb bomb = alien.getBomb();
                    if(player.getPosx() <= bomb.getX()  &&  bomb.getX() <= player.getPosx() + player.getWidth() && player.getPosy() <= bomb.getY()  && bomb.getY() <= player.getPosy() + 20){
                        life--;
                        System.out.println(life);
                        alien.setbomb();
                        continue;
                    }else if(player.getPosx() >= bomb.getX() && bomb.getX() + 10 >= player.getPosx()  &&  bomb.getY() + 10 >= player.getPosy()) {
                        life--;
                        System.out.println(life);
                        alien.setbomb();
                        continue;
                    }

                    drawBomb(g,alien.getBomb());
                    alien.getBomb().setY(alien.getBomb().getY()+1);
                    if(alien.getBomb().getY() >= BOARD_HEIGHT )
                        alien.setbomb();
                }

                draw = true;

            }

            //create Bullet
        /*bullets.removeIf((Bullet bullet) -> bullet.isHit());

        bullets.removeIf((Bullet bullet) -> bullet.getPosy() <= 0);
        if(!bullets.isEmpty()) {
            Iterator<Bullet> it = bullets.iterator();
            while (it.hasNext()){
                Bullet bullet = it.next();
                g.setColor(Color.blue);
                g.fillOval(bullet.getPosx(),bullet.getPosy(),5,5);
                bullet.setPosy(bullet.getPosy()-10);

            }
        }*/
            //draw bullet
            if (bullet != null) {
                if (bullet.getPosy() <= 0) {
                    bullet = null;

                } else {
                    g.setColor(Color.blue);
                    g.fillOval(bullet.getPosx(), bullet.getPosy(), 5, 5);
                    bullet.setPosy(bullet.getPosy() - 10);
                }
            }




            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        }
    }

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

        }

        public void keyPressed(KeyEvent e) {
//System.out.println( e.getKeyCode());
            // message = "Key Pressed: " + e.getKeyCode();
            int key = e.getKeyCode();
            if(key==39)
                player.setPosx(player.getPosx()+10);

            if(key==37)
                player.setPosx(player.getPosx()-10);

            if(key==10) {
                if(bullet == null)
                    bullet = new Bullet(player.getPosx()  , player.getPosy());

                /*bullets.add(new Bullet(player.getPosx(), player.getPosy()));
                System.out.println(bullets.size());*/
            }
            //System.out.println(key);

        }

    }
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }
    public void run() {

        long beforeTime, timeDiff, sleep;

        int animationDelay = 10;
        long time =
                System.currentTimeMillis();
        while (true) {//infinite loop
            // spriteManager.update();
            repaint();
            try {
                time += animationDelay;
                Thread.sleep(Math.max(0,time -
                        System.currentTimeMillis()));
            }catch (InterruptedException e) {
                System.out.println(e);
            }//end catch
        }//end while loop




    }
    public void gameOver(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

        var small = new Font("Helvetica", Font.BOLD, 14);
        var fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
                BOARD_WIDTH / 2);

    }

    public void drawBomb(Graphics g,Alien.Bomb bomb){
        g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), this);

    }


}
