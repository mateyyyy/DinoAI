package org.example;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.max;

public class Dino {
    JLabel dino;
    Boolean live = true;
    int originalY = 365;
    ImageIcon icon;
    ImageIcon icon1;
    ImageIcon icon2;
    ImageIcon blend;
    float[] inputs = new float[3];
    Genoma brain;
    boolean saltar = false;
    boolean agachado = false;

    Image image;
    double puntaje = 0;

    boolean imageState = true; //true 1, false 2

    double i=1;
    double lastI = 1;


    double x = 0;  // variable para agacharse
    double i1;

    public Dino(){
        dino = new JLabel();
        dino.setBounds(50, originalY, 65, 70);
        brain = new Genoma();

        icon = new ImageIcon("dino.png");
        image = icon.getImage();
        Image newImg = image.getScaledInstance(70, 70,  Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);

        dino.setIcon(icon);

    }

    float ReLU(float x) {
        return max(0, x);
    }

    public void puntajeMas(){
        puntaje = puntaje + 0.1;
    }

    public int getPuntaje() {
        return (int) puntaje;
    }

    void isSaltar(int cactusX, int vel){
        if(!saltar) {
            inputs[0] = dino.getY();
            inputs[1] = cactusX - dino.getX();
            inputs[2] = vel;
            brain.feed_forward(inputs);
            if (brain.sesgosOutput[0] != 0) {
                saltar = true;
            } else if (brain.sesgosOutput[1] != 0) {
                agachado = true;
            }
        }
    }

    public JLabel GetDino(){
        return dino;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }

    public void setPuntaje(int puntaje){
        this.puntaje = puntaje;
    }

    public boolean isALive(){
        return live;
    }

    public void cloneBrain(){
        brain.copia();
    }

    void setDefaultIcon(){
        dino.setIcon(icon);
    }

    void setLocation(int x, int y){
        dino.setLocation(x, y);
    }

    public double getx() {
        return x;
    }

    public int getY() {
        return dino.getY();
    }

    public boolean isSaltar() {
        return saltar;
    }

    public boolean isAgachado() {
        return agachado;
    }

    public void setSaltar(boolean saltar) {
        this.saltar = saltar;
    }

    public void setAgachado(boolean agachado) {
        this.agachado = agachado;
    }

    public double getI() {
        return i;
    }

    public void setI(double i) {
        this.i = i;
    }

    public double getLastI() {
        return lastI;
    }

    public void setLastI(double lastI) {
        this.lastI = lastI;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getI1() {
        return i1;
    }

    public void setI1(double i1) {
        this.i1 = i1;
    }

}
