package it.unicam.cs.pa2223;


import it.unicam.cs.pa2223.controller.ControllerAmbiente;
import it.unicam.cs.pa2223.controller.ControllerRobot;
import it.unicam.cs.pa2223.controller.ControllerSimulazione;
import it.unicam.cs.pa2223.utilities.FollowMeParser;
import it.unicam.cs.pa2223.utilities.FollowMeParserException;
import it.unicam.cs.pa2223.utilities.ShapeData;
import it.unicam.cs.pa2223.view.IView;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class App {

    private IView<String> view;
    private static String fileAmbiente = "src/it/unicam/cs/pa2223/resources/area.txt";
    private static String fileProgram1 = "src/it/unicam/cs/pa2223/resources/program1.txt";
    private static Path pathAmbiente = Paths.get(fileAmbiente);
    private static Path pathProgram1 = Paths.get(fileProgram1);
    private static String fileProgram2 = "src/it/unicam/cs/pa2223/resources/program2.txt";
    private static Path pathProgram2 = Paths.get(fileProgram2);
    private static String fileProgram3 = "src/it/unicam/cs/pa2223/resources/program3.txt";
    private static Path pathProgram3 = Paths.get(fileProgram3);

    private static int numRobot = 10;

    public static void main(String[] args) throws FollowMeParserException, IOException {

        impostaArgs(args);

        // Primo esempio
        ControllerSimulazione controller = new ControllerSimulazione(fileProgram1);

        FollowMeParser parser = new FollowMeParser(controller);

        List<ShapeData> listaAmbiente = parser.parseEnvironment(pathAmbiente);

        ControllerAmbiente controllerAmbiente = new ControllerAmbiente(listaAmbiente);
        ControllerRobot controllerRobot = new ControllerRobot(numRobot);
        controller.setControllerAmbiente(controllerAmbiente);
        controller.setControllerRobot(controllerRobot);
        parser.parseRobotProgram(pathProgram1);

        controller = new ControllerSimulazione(fileProgram2);

        parser = new FollowMeParser(controller);

        // Secondo Esempio

        controllerAmbiente = new ControllerAmbiente(listaAmbiente);
        controllerRobot = new ControllerRobot(numRobot);
        controller.setControllerAmbiente(controllerAmbiente);
        controller.setControllerRobot(controllerRobot);
        parser.parseRobotProgram(pathProgram2);

        // Terzo Esempio

        controller = new ControllerSimulazione(fileProgram3);

        parser = new FollowMeParser(controller);


        controllerAmbiente = new ControllerAmbiente(listaAmbiente);
        controllerRobot = new ControllerRobot(numRobot);
        controller.setControllerAmbiente(controllerAmbiente);
        controller.setControllerRobot(controllerRobot);
        parser.parseRobotProgram(pathProgram3);
    }

    private static void impostaArgs(String[] args) {
        if(args.length == 3){
            fileAmbiente = args[0];
            fileProgram1 = args[1];
            try{
                numRobot = Integer.parseInt(args[2]);
            }
            catch (NumberFormatException e){
                numRobot = 3;
            }
        }
    }
}