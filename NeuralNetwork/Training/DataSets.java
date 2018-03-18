package NeuralNetwork.Training;

import java.util.ArrayList;

public class DataSets {

    private ArrayList<double[]> input;
    private ArrayList<double[]> target;

    public DataSets(){
        this.input = new ArrayList<>();
        this.target = new ArrayList<>();
    }

    public void addDataSet(double[] input, double[] target){
        this.input.add(input);
        this.target.add(target);
    }

    public double[][] getDataSet(int index){
        return new double[][]{this.input.get(index),this.target.get(index)};
    }

    public int size(){
        return this.input.size();
    }
}

