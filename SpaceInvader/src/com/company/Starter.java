package com.company;

import javax.swing.*;

public class Starter extends JFrame {

    public Starter(){
        add(new Board());
        setTitle("Space Invader");
        setVisible(true);
        setSize(400,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

}
