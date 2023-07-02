package it.unicam.cs.pa2223.model.robot;

public enum CondizioneRobot {

    ROSSO1_("ROSSO"),

    GIALLO2_("GIALLO"),

    BLU3_("BLU");

    private final String condizione;

    CondizioneRobot(String condizione) {
        this.condizione = condizione;
    }

    public String getCondizione() {
        return condizione;
    }

    public static CondizioneRobot fromString(String label) {
        for (CondizioneRobot valore : CondizioneRobot.values()) {
            if (valore.getCondizione().equals(label)) {
                return valore;
            }
        }
        return null;  // Ritorna null se la stringa non corrisponde a nessuna etichetta dell'enum
    }
}

