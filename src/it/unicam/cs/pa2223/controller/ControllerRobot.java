package it.unicam.cs.pa2223.controller;


import it.unicam.cs.pa2223.model.Posizione;
import it.unicam.cs.pa2223.model.robot.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ControllerRobot implements IController<Robot>{

    private List<Robot> listaRobot = new ArrayList<>();

    public ControllerRobot(int numRobot) {
        robotConstructor(numRobot);
    }

    private void robotConstructor(int n){
        double x;
        double y;
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            x = random.nextDouble() * 2 - 1;
            y = random.nextDouble() * 2 - 1;
            listaRobot.add(new Robot(new Posizione(x, y), i));
        }
    }

    @Override
    public List<Robot> getLista() {
        return listaRobot;
    }
}
