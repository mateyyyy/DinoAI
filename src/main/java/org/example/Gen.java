package org.example;

import java.util.Random;

public class Gen {

    boolean source_hidden_layer;
    int id_source_neuron;
    int id_target_neuron;
    float peso;
    Random rand = new Random();

    public Gen(){
        source_hidden_layer = rand.nextInt(2)>0.5;
        peso = rand.nextFloat(-1,1);
        if (source_hidden_layer) {
            id_source_neuron = rand.nextInt(7);
            id_target_neuron = rand.nextInt(2);
        }
        else{
            id_source_neuron = rand.nextInt(3);
            id_target_neuron = rand.nextInt(7);
        }
    }


    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float mutarPeso(){
        return (peso + rand.nextFloat((float) -0.1, (float) 0.1));
    }


    public int getSN() {
        return id_source_neuron;
    }

    public int getTN() {
        return id_target_neuron;
    }

    public boolean isSource_hidden_layer() {
        return source_hidden_layer;
    }
}
