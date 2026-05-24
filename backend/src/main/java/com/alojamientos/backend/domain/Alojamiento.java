package com.alojamientos.backend.domain;

public class Alojamiento {
    private String id;
    private double precioPorNoche;

    public Alojamiento(String id, double precioPorNoche) {
        this.id = id;
        this.precioPorNoche = precioPorNoche;
    }

    public String getId() { return id; }
    public double getPrecioPorNoche() { return precioPorNoche; }
}