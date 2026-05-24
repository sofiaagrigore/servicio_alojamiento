package com.alojamientos.backend.domain;

public class Pago {
    private String estado; 
    private double importe;

    public Pago(String estado, double importe) {
        this.estado = estado;
        this.importe = importe;
    }
    public String getEstado() { return estado; }
}