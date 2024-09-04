package org.example;

import java.awt.Rectangle;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class MyFrame extends JFrame implements KeyListener {
    JLabel cactus;
    JLabel puntuacion;
    double puntaje = 0;
    Timer timer;
    Timer imageMovement;
    ArrayList<Dino> dino = new ArrayList<Dino>();
    int dinosAlive = 100;
    int totalDinos = 100;
    JLabel dinosVivos;
    int mayorPuntaje = 0;

    double i=1;
    double lastI = 1;
    double x = 0;  // variable para agacharse

    int generacion = 0;
    int i1;
    int originalY = 365;
    int secondY = originalY;

    double velocidad = 7;
    ImageIcon icon;
    ImageIcon icon1;
    ImageIcon icon2;
    ImageIcon cactusIcon;

    Image cactusIMG;
    Image image;
    Image image1;
    Image image2;
    boolean flag = false ;

    Dino mayorDino = new Dino();
    Random rand = new Random();
    float[] inputs = new float[5];

    public MyFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.setLayout(null);
        this.addKeyListener(this);

        for (int j=0;j<totalDinos;j++){
            dino.add(new Dino());
        }

        cactusIcon = new ImageIcon("cactus.png");
        cactusIMG = cactusIcon.getImage();
        Image newCac = cactusIMG.getScaledInstance(30, 60,  Image.SCALE_SMOOTH);
        cactusIcon = new ImageIcon(newCac);
        cactus = new JLabel();
        cactus.setBounds(500, 375, 25, 60);
        cactus.setIcon(cactusIcon);

        dinosVivos = new JLabel();
        dinosVivos.setBounds(65, 20, 120, 30 );

        puntuacion = new JLabel();
        puntuacion.setBounds(650, 20, 120, 30 );

        spawnDinos();
        this.add(dinosVivos);
        this.add(cactus);
        this.add(puntuacion);
        this.setBackground(Color.white);

        timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Agacharse();
                Saltar();
                sumarPuntaje();
                feedIa();
                int k = rand.nextInt(totalDinos);
                if (dino.get(k).isALive()) {
                    puntuacion.setText("Puntacion : " + dino.get(k).getPuntaje());
                }
                dinosVivos.setText("Dinos vivos : " + dinosAlive);
                moveCactus();
                hitCactus();
                AllDinosAlive();
            }
        });

        imageMovement = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                velocidad = velocidad+0.1;

            }
        });


        timer.start();
        imageMovement.start();
        this.setVisible(true);

    }

    void Saltar(){
        for (Dino dinos:dino) {
            if (dinos.isSaltar() && dinos.getI() != 44 && dinos.isALive()) {
                dinos.setI1(((Math.pow((dinos.getI() - 22)/2, 2)) - 121));
                dinos.setLocation(dinos.GetDino().getX(), (int) (originalY + dinos.getI1()));
                dinos.setLastI(dinos.getI1());
                dinos.setI(dinos.getI()+1);
            } else if (dinos.getI() == 44) {
                dinos.setSaltar(false);
                dinos.setI(1);
            }
        }
    }

    void feedIa(){
        for (Dino dinos : dino){
            dinos.isSaltar(cactus.getX(), (int) velocidad);
        }
    }

    void Agacharse(){
        for (Dino dinos : dino) {
            if (dinos.isAgachado() && dinos.isALive()) {
                dinos.setSaltar(false);
                dinos.setI(1);
                if (dinos.getx() <= Math.sqrt(dinos.getLastI())) {
                    dinos.setI1((int) -(Math.pow(dinos.getx(), 2) - dinos.getLastI()));
                    dinos.setLocation(dinos.GetDino().getX(), (int) (originalY + dinos.getI1()));
                    dinos.setX(dinos.getx()+1);
                } else {
                    dinos.setAgachado(false);
                    dinos.setX(0);
                }
            } else {
                dinos.setX(0);
            }
        }
    }


    void moveCactus(){
        cactus.setLocation(cactus.getX()-(int) velocidad, cactus.getY());
        if(cactus.getX() <= -50){
            cactus.setLocation(rand.nextInt(400,900), cactus.getY());
        }
    }

    void hitCactus(){
        for (Dino dinos: dino) {
            if (dinos.isALive()) {
                Rectangle dinosaurBounds = dinos.GetDino().getBounds();
                Rectangle cactusBounds = cactus.getBounds();

                if (dinosaurBounds.intersects(cactusBounds)) {
                    dinos.setPuntaje(0);
                    dinos.setDefaultIcon();
                    dinos.setLive(false);
                    this.remove(dinos.GetDino());
                    dinosAlive = dinosAlive - 1;
                }
            }
        }
    }

    void AllDinosAlive(){
        if(dinosAlive <= 0){
            timer.stop();
            imageMovement.stop();

            // Inicializar el puntaje más alto para la nueva generación
            int mayorPuntaje = 0;
            Dino mayorDino = null;

            // Seleccionar el mejor dino de la generación actual
            for (Dino dinos : dino){
                if(dinos.getPuntaje() > mayorPuntaje){
                    mayorPuntaje = dinos.getPuntaje();
                    mayorDino = dinos;
                }
            }

            if(mayorDino != null && mayorPuntaje >= 10) {
                // Reproducción con mutación para mantener diversidad
                for (int i = 0; i < totalDinos; i++) {
                    if (i < totalDinos / 6) {
                        // Copiar el genoma del mejor dinosaurio
                        dino.get(i).brain = mayorDino.brain.copia();
                        // Mutar ligeramente para mantener diversidad
                        dino.get(i).brain.mutar();
                    } else {
                        // Crear nuevos genomas para fomentar la diversidad
                        dino.get(i).brain = new Genoma();
                    }
                }
            } else {
                // Reiniciar el brain de todos los dinos si no se alcanzó el puntaje mínimo
                for (Dino dinos : dino){
                    dinos.brain = new Genoma();
                }
            }

            // Reiniciar variables y dinos para la nueva generación
            velocidad = 7;
            puntaje = 0;
            cactus.setLocation(385, 375);
            spawnDinos();
            timer.start();
            imageMovement.start();
            dinosAlive = totalDinos;
            generacion++;
        }
    }

    // Método para mutar el genoma en la clase Genoma


    // Método para mutar el genoma en la clase Genoma


    void sumarPuntaje(){
        for (Dino dinos : dino){
            dinos.puntajeMas();
        }
    }

    void spawnDinos(){
        for (Dino dinos : dino){
            this.add(dinos.GetDino());
            dinos.setLive(true);
        }
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
