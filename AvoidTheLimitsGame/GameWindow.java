package AvoidTheLimitsGame;

import javax.swing.*;

public class GameWindow {

    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 700;
    private static final int CHARACTER_WIDTH = 20;
    private static final int CHARACTER_HEIGHT = 20;
    private static final int CHARACTER_AT_BEGINNING = 100;
    private static final int CHARACTER_MOVEMENT = 5;
    private static final int TIMER_DELAY = 8;


    public static void main(String[] args){
        //setup frame
        JFrame window = new JFrame();
        window.setBounds(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        window.setTitle("Avoid the limits - Neural Network Demo");
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setup game
        AvoidTheLimits avoidTheLimits = new AvoidTheLimits(WINDOW_WIDTH, WINDOW_HEIGHT, CHARACTER_WIDTH, CHARACTER_HEIGHT, CHARACTER_AT_BEGINNING, CHARACTER_MOVEMENT, TIMER_DELAY);

        //add game to frame
        window.add(avoidTheLimits);
        window.setVisible(true);
    }

}

