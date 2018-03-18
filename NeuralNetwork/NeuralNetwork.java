package NeuralNetwork;

import java.io.Serializable;

public class NeuralNetwork implements Serializable{

    private double[][] networkNeurons;
    private double[][] networkError;
    private double[][][] networkWeights;
    private double[][] biasWeights;

    public NeuralNetwork(int inputNeurons, int[] hiddenNeurons, int outputNeurons){
        networkNeurons = new double[hiddenNeurons.length + 2][];
        biasWeights = new double[hiddenNeurons.length + 1][];
        networkError = new double[hiddenNeurons.length + 2][];
        networkWeights = new double[hiddenNeurons.length + 1][][];
        networkNeurons[0] = new double[inputNeurons];
        networkError[0] = new double[inputNeurons];
        for (int i = 0; i < hiddenNeurons.length; i++) {
            networkNeurons[i + 1] = new double[hiddenNeurons[i]];
            biasWeights[i] = new double[hiddenNeurons[i]];
            networkError[i + 1] = new double[hiddenNeurons[i]];
            if(i == 0) {
                networkWeights[i] = new double[inputNeurons][hiddenNeurons[i]];
            }else {
                networkWeights[i] = new double[hiddenNeurons[i - 1]][hiddenNeurons[i]];
            }
        }
        networkWeights[networkNeurons.length - 2] = new double[hiddenNeurons[hiddenNeurons.length - 1]][outputNeurons];
        networkNeurons[networkNeurons.length - 1] = new double[outputNeurons];
        biasWeights[hiddenNeurons.length] = new double[outputNeurons];
        networkError[networkError.length - 1] = new double[outputNeurons];
    }

    public void connectLayerWithRandomWeights(){
        for (int i = 0; i < networkWeights.length; i++) {
            for (int j = 0; j < networkWeights[i].length; j++) {
                for (int k = 0; k < networkWeights[i][j].length; k++) {
                    networkWeights[i][j][k] = getRandomDouble(-0.8,0.8);
                }
            }
        }
    }

    private double getRandomDouble(double minValue, double maxValue){
        return minValue + Math.random() * (maxValue - minValue);
    }

    private int getRandomInt(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public void mutate(int iterations){
        for (int i = 0; i < iterations; i++) {
            int randomLayer = getRandomInt(0, networkWeights.length - 1);
            int randomNeuron = getRandomInt(0, networkWeights[randomLayer].length - 1);
            int randomConnection = getRandomInt(0, networkWeights[randomLayer][randomNeuron].length - 1);
            networkWeights[randomLayer][randomNeuron][randomConnection] += getRandomDouble(-0.2, 0.2);
        }
    }

    public double[] getPrediction(double[] inputValues){
        networkNeurons[0] = inputValues;
        for (int i = 1; i < networkNeurons.length; i++) {
            for (int j = 0; j < networkNeurons[i].length; j++) {
                double newValue = biasWeights[i - 1][j];
                for (int k = 0; k < networkNeurons[i - 1].length; k++) {
                    newValue += networkNeurons[i - 1][k] * networkWeights[i - 1][k][j];
                }
                networkNeurons[i][j] = sigmoidFunction(newValue);
            }
        }
        return networkNeurons[networkNeurons.length - 1];
    }

    public void trainNeuralNetwork(double[] input, double[] target, double learningRate){
        getPrediction(input);
        calculateError(target);
        addDeltaToWeights(learningRate);
    }

    private void calculateError(double[] target){
        for (int i = networkNeurons.length - 1; i >= 0; i--) {
            if(i == networkNeurons.length - 1){
                for (int j = 0; j < networkNeurons[i].length; j++) {
                    networkError[i][j] = sigmoidDerivativeFunction(networkNeurons[i][j]) * (networkNeurons[i][j] - target[j]);
                }
            }
            else{
                for (int j = 0; j < networkNeurons[i].length; j++) {
                    double sum = 0.0;
                    for (int k = 0; k < networkNeurons[i + 1].length; k++) {
                        sum += networkError[i + 1][k] * networkWeights[i][j][k];
                    }
                    networkError[i][j] = sigmoidDerivativeFunction(networkNeurons[i][j]) * sum;
                }
            }
        }
    }

    private void addDeltaToWeights(double learningRate){
        for (int i = 1; i < networkNeurons.length; i++) {
            for (int j = 0; j < networkNeurons[i].length; j++) {
                for (int k = 0; k < networkNeurons[i - 1].length; k++) {
                    double delta = -learningRate * networkNeurons[i - 1][k] * networkError[i][j];
                    networkWeights[i - 1][k][j] += delta;
                }
            }
        }
    }

    private static double sigmoidFunction(double x){
        return 1 / (1 + Math.exp(-x));
    }

    private static double sigmoidDerivativeFunction(double x){
        return sigmoidFunction(x) * (1 - sigmoidFunction(x));
    }

}
