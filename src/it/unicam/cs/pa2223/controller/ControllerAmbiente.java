package it.unicam.cs.pa2223.controller;


import it.unicam.cs.pa2223.utilities.ShapeData;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class ControllerAmbiente implements IController<ShapeData> {

    private List<ShapeData> listaAree;

    public ControllerAmbiente(List<ShapeData> listaAree) {
        this.listaAree = listaAree;
    }

    public List<ShapeData> getFormeConLabel(String label) {
        return listaAree.stream()
                .filter(shapeData -> shapeData.label().equals(label))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShapeData> getLista() {
        return listaAree;
    }
}
