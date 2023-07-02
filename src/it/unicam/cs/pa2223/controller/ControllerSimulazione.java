package it.unicam.cs.pa2223.controller;


import it.unicam.cs.pa2223.model.Posizione;
import it.unicam.cs.pa2223.model.robot.CondizioneRobot;
import it.unicam.cs.pa2223.model.robot.Robot;
import it.unicam.cs.pa2223.utilities.FollowMeParserHandler;
import it.unicam.cs.pa2223.utilities.ShapeData;
import it.unicam.cs.pa2223.view.IView;

import java.util.ArrayList;
import java.util.List;

public class ControllerSimulazione implements FollowMeParserHandler {

    private IView<String> view;
    private ControllerRobot controllerRobot;
    private ControllerAmbiente controllerAmbiente;
    private double deltaTime;
    private StatoSimulazione stato;
    private String ambientePath;
    private String robotProgramPath;
    private int repeatToken = 0;
    private boolean untilToken = false;
    private String untilLabel = null;

    public ControllerSimulazione(String robotProgramPath) {
        this.robotProgramPath = robotProgramPath;
    }

    public void setControllerRobot(ControllerRobot controllerRobot) {
        this.controllerRobot = controllerRobot;
    }

    public void setControllerAmbiente(ControllerAmbiente controllerAmbiente) {
        this.controllerAmbiente = controllerAmbiente;
    }

    public void setDeltaTime(double deltaTime) {
        this.deltaTime = deltaTime;
    }

    public void nuovaSimulazione() {
        view.message("\nFile IAmbiente selezionato: " + ambientePath + "\n");
        view.message("Numero listaRobot inseriti nella simulazione: " + controllerRobot.getLista().size() + "\n");
        stato = StatoSimulazione.ON;
        while (stato.equals(StatoSimulazione.ON)) {
            gestisciComando();
        }
    }

    private void gestisciComando() {

    }


    private boolean simulazioneInCorso() {
        return stato() != StatoSimulazione.OFF;
    }


    public StatoSimulazione stato() {
        return stato;
    }


    @Override
    public void parsingStarted() {
        System.out.println("ParsingStarted");

    }

    @Override
    public void parsingDone() {
        System.out.println("ParsingDone");

    }

    @Override
    public void moveCommand(double[] args) {
        System.out.println("Move");
        while (untilToken) {
            for (int i = 0; i < controllerRobot.getLista().size(); i++) {
                movimento(args, i);
                System.out.println(i + " moved to " + controllerRobot.getLista().get(i).getPosizione().toString());
            }
            if (controllaLabel(untilLabel)) {
                untilToken = false;
                break;
            }
        }
        do {
                for (int i = 0; i < controllerRobot.getLista().size(); i++) {
                    movimento(args, i);
                    System.out.println(i + " moved to " + controllerRobot.getLista().get(i).getPosizione().toString());

                }
        } while (repeatToken > 1);
        System.out.println("moving end");
    }

    private void movimento(double[] args, int idRobot) {

        double x = args[0];
        double y = args[1];
        double s = args[2];
        double dt = this.deltaTime;

        // Verifica che al massimo una delle coordinate x e y sia diversa da 0
//        if ((x != 0 && y == 0) || (x == 0 && y != 0)) {
        double magnitude = Math.sqrt(x * x + y * y);
        double xAssoluto = x / magnitude;
        double yAssoluto = y / magnitude;

        // Simula il movimento del robot
        double distanza = s * dt; // dt rappresenta l'intervallo di tempo
        double deltaX = xAssoluto * distanza;
        double deltaY = yAssoluto * distanza;
        Robot robot = controllerRobot.getLista().get(idRobot);
        robot.setPosizione(new Posizione(x + deltaX, y + deltaY));
        robot.setVelocita(s);
        robot.setDestinazione(new Posizione(x, y));
//        } else {
//            // Gestisci l'errore se le coordinate non soddisfano le condizioni richieste
//            System.out.println("Coordinate non valide. Almeno una delle due deve essere diversa da 0");
//        }
    }

    @Override
    public void moveRandomCommand(double[] args) {

        System.out.println("Move");
        while (untilToken) {
            for (int i = 0; i < controllerRobot.getLista().size(); i++) {
                muoviRandom(args, i);
                controllaLabel(untilLabel);
                System.out.println(i + " moved to " + controllerRobot.getLista().get(i).getPosizione().toString());
            }
            if (controllaLabel(untilLabel)) {
                untilToken = false;
                break;
            }
        }
        do {
            for (int i = 0; i < controllerRobot.getLista().size(); i++) {
                muoviRandom(args, i);
                System.out.println(i + " moved to " + controllerRobot.getLista().get(i).getPosizione().toString());

            }
        } while (repeatToken > 1);
        System.out.println("moving end");
    }

    private void muoviRandom(double[] args, int id) {
        args[0] = Math.random() * (args[2] - args[1]) + args[1];
        args[1] = Math.random() * (args[4] - args[3]) + args[3];
        movimento(args, id);
    }

    private boolean controllaLabel(String labelTarget) {
        List<Robot> listaRobot = controllerRobot.getLista();
        List<ShapeData> listaAree = controllerAmbiente.getFormeConLabel(labelTarget);

        // Controlla se qualche robot occupa una posizione dentro la specifica label
        boolean occupata = false;
        for (Robot robot : listaRobot) {
            for (ShapeData area : listaAree) {
                if (area.label().equals(labelTarget)) {
                    if (area.shape().equals("CIRCLE")) {
                        double distanza = calcolaDistanza(robot.getPosizione(), new Posizione(area.args()[1], area.args()[2]));
                        if (distanza <= area.args()[3]) {
                            untilToken = false;
                            return true;
                        }
                    } else if (area.shape().equals("RECTANGLE")) {
                        if (occupazioneRettangolo(robot.getPosizione(), area.args())) {
                            untilToken = false;
                            return true;
                        }
                    }
                }
            }
        }
        untilToken = false;
        return occupata;
    }

    public static boolean occupazioneRettangolo(Posizione posizione, double[] args) {
        double x = args[0];
        double y = args[1];
        double minX = x - args[2] / 2;
        double minY = y - args[3] / 2;
        double maxX = x + args[2] / 2;
        double maxY = y + args[3] / 2;
        return posizione.getX() >= minX && x <= maxX && posizione.getY() >= minY && y <= maxY;
    }

    @Override
    public void signalCommand(String label) {
        System.out.println("Signal Command " + label);
        for (Robot robot : controllerRobot.getLista()) {
            // todo smelly
            CondizioneRobot condizione = CondizioneRobot.fromString(label);
            if (condizione != null) {
                robot.segnalaCondizione(label);
            } else {
                System.out.println("Errore: condizione del robot non trovata");
            }
        }
    }

    @Override
    public void unsignalCommand(String label) {
        System.out.println("Unsignal Command " + label);

        // todo potenzialmente smelly
        for (Robot robot : controllerRobot.getLista()) {
            CondizioneRobot condizione = CondizioneRobot.fromString(label);
            if (!robot.controllaSegnalazione(label)) {
                System.out.println("La condizione " + condizione + " non sta venendo segnalata dal robot");
                return;
            }
            if (condizione != null) {
                robot.interrompiSegnalazione(label);
            } else {
                System.out.println("Errore: condizione del robot non trovata");
            }
        }
    }

    @Override
    public void followCommand(String label, double[] args) {

        System.out.println("Following " + label);
        while (untilToken) {
            for (int i = 0; i < controllerRobot.getLista().size(); i++) {
                // Generare casualmente le coordinate x e y all'interno dell'intervallo [x1, x2] e [y1, y2]
                segui(args, i, label);
                controllaLabel(untilLabel);
                System.out.println(i + " moved to " + controllerRobot.getLista().get(i).getPosizione().toString());

            }
        }
            do {
                    for (int i = 0; i < controllerRobot.getLista().size(); i++) {
                        segui(args, i, label);
                        System.out.println(i + " moved to " + controllerRobot.getLista().get(i).getPosizione().toString());

                    }

            } while (repeatToken > 1);

    }

    private void segui(double[] args, int i, String label) {
        double distanza = args[0];
        double s = args[1];
        Robot robot = controllerRobot.getLista().get(i);
        List<Robot> robotVicini = cercaRobotConLabelVicini(label, distanza);
        if (robotVicini.isEmpty()) {
            double[] args2 = {-distanza, distanza, -distanza, distanza, s};
            moveRandomCommand(args2);
        } else {
            Posizione mediaPosizioni = calcolaMediaPosizioni(robotVicini);
            // Calcolare la direzione verso la posizione media
            double direzioneX = mediaPosizioni.getX() - robot.getPosizione().getX();
            double direzioneY = mediaPosizioni.getY() - robot.getPosizione().getY();

            // Normalizzare la direzione
            double magnitudine = Math.sqrt(direzioneX * direzioneX + direzioneY * direzioneY);
            if (magnitudine != 0) {
                direzioneX /= magnitudine;
                direzioneY /= magnitudine;
            }
            // Effettuare il movimento verso la posizione media
            movimento(new double[]{direzioneX, direzioneY, s}, i);
        }
    }

    private Posizione calcolaMediaPosizioni(List<Robot> robotVicini) {
        double sommaX = 0;
        double sommaY = 0;
        for (Robot robot : robotVicini) {
            sommaX += robot.getPosizione().getX();
            sommaY += robot.getPosizione().getY();
        }
        double mediaX = sommaX / robotVicini.size();
        double mediaY = sommaY / robotVicini.size();
        return new Posizione(mediaX, mediaY);
    }

    private List<Robot> cercaRobotConLabelVicini(String label, double dist) {
        List<Robot> robotVicini = new ArrayList<>();
        controllerRobot.getLista().stream()
                .filter(robot1 -> robot1.controllaSegnalazione(label))
                .forEach(robot1 -> controllerRobot.getLista().stream()
                        .filter(robot2 -> !robot1.equals(robot2) && calcolaDistanza(robot1.getPosizione(), robot2.getPosizione()) <= dist)
                        .forEach(robotVicini::add));
        return robotVicini;
    }

    private double calcolaDistanza(Posizione p1, Posizione p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public void stopCommand() {
        System.out.println("Stop Command");

        for (Robot robot : controllerRobot.getLista()) {
            robot.setVelocita(0);
        }
    }

    @Override
    public void continueCommand(int s) {

        System.out.println("Continue Command");
        for (int z = 0; z < s; z++) {

            while (untilToken) {
            for (int i = 0; i < controllerRobot.getLista().size(); i++) {
                Robot robot = controllerRobot.getLista().get(i);
                double args[] = {robot.getDestinazione().getX(), robot.getDestinazione().getY(), robot.getVelocita()};
                if(robot.getDestinazione()!=null) {

                    movimento(args, i);
                System.out.println(i + " moved to " + controllerRobot.getLista().get(i).getPosizione().toString());
                } else{
                    System.out.println("Impossibile continuare, destinazione null");
                }
            }
            if (controllaLabel(untilLabel)) {
                untilToken = false;
                break;
            }
        }
        do {
            for (int i = 0; i < controllerRobot.getLista().size(); i++) {
                Robot robot = controllerRobot.getLista().get(i);
                double args[] = {robot.getDestinazione().getX(), robot.getDestinazione().getY(), robot.getVelocita()};
                movimento(args, i);
                System.out.println(i + " moved to " + controllerRobot.getLista().get(i).getPosizione().toString());

            }
        } while (repeatToken > 1);
        System.out.println("continue command over");

        }
    }

    @Override
    public void repeatCommandStart(int n) {
        System.out.println("Repeat Command");

        repeatToken = n;
    }

    @Override
    public void untilCommandStart(String label) {
        System.out.println("Until Command");

        untilToken = true;
        untilLabel = label;
    }

    @Override
    public void doForeverStart() {
        System.out.println("Do forever Command");

        repeatToken = Integer.MAX_VALUE;
    }

    @Override
    public void doneCommand() {
        System.out.println("Done Command");

        repeatToken = 0;
    }
}