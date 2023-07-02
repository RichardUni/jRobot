package it.unicam.cs.pa2223.model.robot;


import it.unicam.cs.pa2223.model.Posizione;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un <code>Robot</code> di default nella simulazione
 */
public class Robot {

    private Posizione posizione;
    private Posizione destinazione;
    private double velocita;
    private List<SegnalaCondizioni> listaCondizioni;
    private int idRobot;
    private boolean segnala = false;


    public Robot(Posizione posizione, int idRobot) {
        this.idRobot = idRobot;
        this.posizione = posizione;
        this.velocita = 0;
        List<SegnalaCondizioni> lista = new ArrayList<>();
        SegnalaCondizioni rosso= new SegnalaCondizioni(false, CondizioneRobot.ROSSO1_);
        SegnalaCondizioni giallo = new SegnalaCondizioni(false, CondizioneRobot.GIALLO2_);
        SegnalaCondizioni blu = new SegnalaCondizioni(false, CondizioneRobot.BLU3_);
        lista.add(rosso);
        lista.add(giallo);
        lista.add(blu);
        listaCondizioni = lista;
        }


    public boolean controllaSegnalazione(String label){
        boolean segnalazione = false;
        for(SegnalaCondizioni i : listaCondizioni){
            if (i.getCondizioneRobot().getCondizione().equals(label)) {
                segnalazione = true;
                break;
            }
        }
        return segnalazione;
    }

    public void segnalaCondizione(String label){
        SegnalaCondizioni condizione;
        switch(label){
            case "ROSSO" :
                condizione = listaCondizioni.get(1);
                condizione.setSegnalazione(true);
                break;
            case "GIALLO" :
                condizione = listaCondizioni.get(2);
                condizione.setSegnalazione(true);
                break;
            case "BLU" :
                condizione = listaCondizioni.get(3);
                condizione.setSegnalazione(true);
                break;
            default :
                System.out.println("Errore: label non riconosciuta");
                break;
        }
    }

    public void interrompiSegnalazione(String label){
        SegnalaCondizioni condizione;
        switch(label){
            case "ROSSO" :
                condizione = listaCondizioni.get(1);
                condizione.setSegnalazione(false);
                break;
            case "GIALLO" :
                condizione = listaCondizioni.get(2);
                condizione.setSegnalazione(false);
                break;
            case "BLU" :
                condizione = listaCondizioni.get(3);
                condizione.setSegnalazione(false);
                break;
            default :
                System.out.println("Errore: label non riconosciuta");
                break;
        }
    }



    public Posizione getPosizione() {
        return posizione;
    }

    public List<SegnalaCondizioni> getListaCondizioni() {
        return listaCondizioni;
    }

    public int getIdRobot() {
        return idRobot;
    }

    public boolean isSegnala() {
        return segnala;
    }

    public void setPosizione(Posizione posizione) {
        this.posizione = posizione;
    }

    public void setListaCondizioni(List<SegnalaCondizioni> listaCondizioni) {
        this.listaCondizioni = listaCondizioni;
    }

    public void setIdRobot(int idRobot) {
        this.idRobot = idRobot;
    }

    public void setSegnala(boolean segnala) {
        this.segnala = segnala;
    }

    public double getVelocita() {
        return velocita;
    }

    public void setVelocita(double velocita) {
        this.velocita = velocita;
    }

    public Posizione getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(Posizione destinazione) {
        this.destinazione = destinazione;
    }
}
