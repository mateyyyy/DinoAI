package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Genoma {
    ArrayList<Gen> genes = new ArrayList<>();
    Random rand = new Random();
    float[][] matrixPesosInput = new float[3][7];
    float[][] matrixPesosOutput = new float[7][2];
    float[] sesgosHidden = new float[7];
    float[] sesgosOutput = new float[2];
    int cantGenes = 26; // Aumenta la cantidad de genes

    public Genoma(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 7; j++){
                matrixPesosInput[i][j] = 0;
            }
        }
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 2; j++){
                matrixPesosOutput[i][j] = 0;
            }
        }
        for (int i = 0; i < 7; i++){
            sesgosHidden[i] = rand.nextFloat(-2, 1);
        }
        for (int i = 0; i < 2; i++){
            sesgosOutput[i] = rand.nextFloat(-2, 1);
        }

        for(int i = 0; i <= cantGenes; i++){
            genes.add(new Gen());
            if (genes.get(i).isSource_hidden_layer()){
                matrixPesosOutput[genes.get(i).getSN()][genes.get(i).getTN()] = genes.get(i).getPeso();
            }
            else {
                matrixPesosInput[genes.get(i).getSN()][genes.get(i).getTN()] = genes.get(i).getPeso();
            }
        }
    }

    public void mutar() {
        for (Gen gen : genes) {
            if (rand.nextFloat(1) <= 0.1) { // 10% de probabilidad de mutar
                gen.mutarPeso();
            }
        }
        for (int i = 0; i < sesgosHidden.length; i++) {
            if (rand.nextFloat(1) <= 0.1) { // 10% de probabilidad de mutar
                sesgosHidden[i] += rand.nextFloat(-0.1f, 0.1f);
            }
        }
        for (int i = 0; i < sesgosOutput.length; i++) {
            if (rand.nextFloat(1) <= 0.1) { // 10% de probabilidad de mutar
                sesgosOutput[i] += rand.nextFloat(-0.1f, 0.1f);
            }
        }
    }

    public float Relu(float valor){
        return Math.max(0,valor);
    }

    public void feed_forward(float[] inputs){
        float peso = 0;
        float total = 0;
        float[] hiddenOutputs = new float[7];

        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 3; j++){
                peso = matrixPesosInput[j][i];
                total += peso * inputs[j];
            }
            total += sesgosHidden[i];
            hiddenOutputs[i] = Relu(total);
            total = 0;
        }

        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 7; j++){
                peso = matrixPesosOutput[j][i];
                total += peso * hiddenOutputs[j];
            }
            total += sesgosOutput[i];
            sesgosOutput[i] = sigmoid(total);
            total = 0;
        }
    }

    private float sigmoid(float x) {
        return 1 / (1 + (float)Math.exp(-x));
    }
    public Genoma copia(){
        return this;
    }



}
