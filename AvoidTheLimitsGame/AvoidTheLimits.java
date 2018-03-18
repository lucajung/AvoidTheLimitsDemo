package AvoidTheLimitsGame;

import NeuralNetwork.NeuralNetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AvoidTheLimits extends JPanel implements KeyListener, ActionListener {

    private Timer timer;
    private int epochs = 0;
    private int windowWidth;
    private int windowHeight;
    private int movement;
    private int characterWidth;
    private int characterHeight;
    private ArrayList<Character> character;

    public AvoidTheLimits(int windowWidth, int windowHeight, int characterWidth, int characterHeight, int characterAtBeginning, int movement, int timerDelay){
        //setup
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        //frame dimensions
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.characterWidth = characterWidth;
        this.characterHeight = characterHeight;
        this.movement = movement;

        character = new ArrayList<>();

        //timer
        timer = new Timer(timerDelay,this);
        timer.start();

        //add character to the game
        addCharacter(characterAtBeginning);
    }

    public void addCharacter(int numberOfCharacter) {
        character.clear();
        for (int i = 0; i < numberOfCharacter; i++) {
            //generate random Neural Network for each character
            NeuralNetwork characterBrain = new NeuralNetwork(4, new int[]{8}, 4);
            characterBrain.connectLayerWithRandomWeights();

            //add new character to game
            character.add(new Character(((this.windowWidth / 2) - (this.characterWidth / 2)), ((this.windowHeight / 2) - (this.characterHeight / 2)), this.characterWidth, this.characterHeight, characterBrain));
        }
    }

    public void paint(Graphics g){
        //print background
        g.setColor(Color.black);
        g.fillRect(0,0,this.windowWidth,this.windowHeight);

        //print info
        g.setColor(Color.white);
        g.drawString("Epochs: " + Integer.toString(epochs), 40, 40);

        //print each character if he's alive
        g.setColor(Color.blue);
        for (int i = 0; i < character.size(); i++) {
            if(character.get(i).isAlive()) {
                g.fillRect(character.get(i).getX(), character.get(i).getY(), character.get(i).getWidth(), character.get(i).getHeight());
            }
        }

        g.dispose();
    }

    //asks each character to change direction
    private void askCharacter(){
        for (int i = 0; i < character.size(); i++) {
            if(character.get(i).isAlive()) {
                character.get(i).askNeuralNetwork(getDistancesToBorder(character.get(i)));
            }
        }
    }

    private double[] getDistancesToBorder(Character character){
        return new double[]{character.getX(), windowWidth - character.getX(), character.getY(), windowWidth - character.getY()};
    }

    private boolean touchesBoundary(Character character){
        if((character.getX() + character.getWidth() >= windowWidth || character.getX() <= 0) || (character.getY() + character.getHeight()) >= windowHeight || (character.getY() <= 0)){
            return true;
        }
        else {
            return false;
        }
    }

    //mutates each characters neural network
    public void mutateAllCharacter() {
        epochs++;
        for (int i = 0; i < character.size(); i++) {
            character.get(i).getNeuralNetwork().mutate(70);
            character.get(i).resetCharacter();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        askCharacter();
        boolean isOneAlive = false;

        //check if character is dead
        for (int i = 0; i < character.size(); i++) {
            if(touchesBoundary(character.get(i))){
                character.get(i).died();
            }
            else {
                isOneAlive = true;
            }
        }

        //mutate if all are dead
        if(!isOneAlive){
            mutateAllCharacter();
        }

        //move Character in right direction
        for (int i = 0; i < character.size(); i++) {
            if(character.get(i).getDirection() == 1){
                character.get(i).moveOnXAxis(movement);
            }
            else if(character.get(i).getDirection() == 3){
                character.get(i).moveOnXAxis(-movement);
            }
            else if(character.get(i).getDirection() == 2){
                character.get(i).moveOnYAxis(movement);
            }
            else if(character.get(i).getDirection() == 0){
                character.get(i).moveOnYAxis(-movement);
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
