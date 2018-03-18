package AvoidTheLimitsGame;

import NeuralNetwork.NeuralNetwork;

public class Character {

    private int width;
    private int height;
    private int xPosition;
    private int yPosition;
    private int xPositionInit;
    private int yPositionInit;
    private boolean alive = true;
    private NeuralNetwork neuralNetwork;
    private double distanceAfterDirectionChange = 0;
    private int direction = (int)(Math.random() * 4);

    public Character(int x, int y, int characterWidth, int characterHeight, NeuralNetwork neuralNetwork){
        this.xPosition = x;
        this.yPosition = y;
        this.xPositionInit = x;
        this.yPositionInit = y;
        this.width = characterWidth;
        this.height = characterHeight;
        this.neuralNetwork = neuralNetwork;
    }

    public NeuralNetwork getNeuralNetwork(){
        return this.neuralNetwork;
    }

    public void resetCharacter(){
        setX(xPositionInit);
        setY(yPositionInit);
        setAlive(true);
    }

    public void askNeuralNetwork(double[] distances){
        double[] predictions = this.neuralNetwork.getPrediction(distances);
        int highestIndex = 0;
        for (int i = 0; i < predictions.length; i++) {
            highestIndex = predictions[i] > predictions[highestIndex] ? i : highestIndex;
        }
        //do not change direction instantaneously
        if(distanceAfterDirectionChange >= 50 || (distanceAfterDirectionChange * -1) >= 50) {
            distanceAfterDirectionChange = 0;
            //opposite direction is not allowed
            if(getDirection() -  highestIndex != 2 && getDirection() -  highestIndex != -2){
                setDirection(highestIndex);
            }
        }
    }

    public void died(){
        alive = false;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void moveOnXAxis(int number){
        xPosition += number;
        distanceAfterDirectionChange += number;
    }

    public void moveOnYAxis(int number){
        yPosition += number;
        distanceAfterDirectionChange += number;
    }

    public int getDirection(){
        return direction;
    }

    public int getX(){
        return xPosition;
    }

    public int getY(){
        return yPosition;
    }

    public void setX(int x){
        this.xPosition = x;
    }

    public void setY(int y){
        this.yPosition = y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean isAlive(){
        return alive;
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

}
