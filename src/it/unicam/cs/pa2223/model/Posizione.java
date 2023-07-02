package it.unicam.cs.pa2223.model;

import java.util.Objects;

/**
 * Rappresenta le coordinate in una griglia usate per localizzare una <code>Cella</code>
 */
public class Posizione {

    private double x;
    private double y;


    /**
     * Crea una posizione che si riferisce ai valori di <code>x</code> e <code>y</code> forniti
     * @param x valore <code>x</code> della posizione
     * @param y valore <code>y</code> della posizione
     */
    public Posizione(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Restituisce la coordinata <code>x</code> della <code>Posizione</code>
     * @return la coordinata <code>x</code> della <code>Posizione</code>
     */
    public double getX() {
        return x;
    }

    /**
     * Restituisce la coordinata <code>y</code> della <code>Posizione</code>
     * @return la coordinata <code>y</code> della <code>Posizione</code>
     */
    public double getY() {
        return y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Posizione that = (Posizione) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Posizione{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
