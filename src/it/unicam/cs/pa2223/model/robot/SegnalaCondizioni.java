package it.unicam.cs.pa2223.model.robot;

public class SegnalaCondizioni {

    private boolean statoSegnalazione;
    private CondizioneRobot condizione;

    public SegnalaCondizioni(boolean segnalazione, CondizioneRobot condizione) {
        this.statoSegnalazione = segnalazione;
        this.condizione = condizione;
    }

    public boolean isSegnalazione() {
        return statoSegnalazione;
    }

    public CondizioneRobot getCondizioneRobot() {
        return condizione;
    }

    public void setSegnalazione(boolean segnalazione) {
        this.statoSegnalazione = segnalazione;
    }

    public void setCondizione(CondizioneRobot condizione) {
        this.condizione = condizione;
    }
}
