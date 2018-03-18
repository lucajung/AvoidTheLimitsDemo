package NeuralNetwork.Testing;

import NeuralNetwork.Training.DataSets;
import NeuralNetwork.NeuralNetwork;

import java.util.Arrays;

public class Testing {

    public static void main(String [] args)
    {
        NeuralNetwork network = new NeuralNetwork(2,new int[]{4},1);
        DataSets trainingData = new DataSets();
        trainingData.addDataSet(new double[]{0.2, 0.2}, new double[]{0.4});
        trainingData.addDataSet(new double[]{0.3, 0.4}, new double[]{0.7});
        network.connectLayerWithRandomWeights();
        System.out.println(Arrays.toString(network.getPrediction(new double[]{0.2, 0.2})));
        System.out.println(Arrays.toString(network.getPrediction(new double[]{0.3, 0.4})));
        for (int i = 0; i < 700000; i++) {
            for (int j = 0; j < trainingData.size(); j++) {
                network.trainNeuralNetwork(trainingData.getDataSet(j)[0], trainingData.getDataSet(j)[1], 0.008);
            }
        }
        System.out.println(Arrays.toString(network.getPrediction(new double[]{0.2, 0.2})));
        System.out.println(Arrays.toString(network.getPrediction(new double[]{0.3, 0.4})));
    }

}
