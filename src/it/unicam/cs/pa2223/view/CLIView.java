package it.unicam.cs.pa2223.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class CLIView implements IView<String> {

    @Override
    public void message(String stringMessage) {
        System.out.println(stringMessage);
    }


    @Override
    public String ask(String message) {
        message(message);
        return fetch();
    }

    private String fetch() {
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return bufferReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public int fetchChoice(String msg, int upperBound) {
        message(msg);
        int input;
        while (true) {
            input = fetchInt();
            if (1 <= input && input <= upperBound)
                break;
            else
                message("Devi inserire un numero che si trova tra 1 e " + upperBound + "(compresi)");
        }
        return input;
    }

    @Override
    public int fetchInt() {
        int result = 0;
        boolean inside = true;
        while (inside) {
            try {
                result = Integer.parseInt(fetch());
                inside = false;
            } catch (NumberFormatException | NullPointerException e) {
                message("Devi inserire un numero intero");
            }
        }
        return result;
    }


    @Override
    public boolean fetchBool() {
        String input;
        while (true) {
            input = fetch();
            if (input.isEmpty()) return true;
            else if (Arrays.asList("y","Y","Yes","YES","yes").contains(input)) return true;
            else if (Arrays.asList("n","N","No","NO","no").contains(input)) return false;
            else message("Errore di inserimento: le scelte possibili sono [y, Y, Yes, yes] oppure [n, N, No, NO, no]");
        }
    }
}
