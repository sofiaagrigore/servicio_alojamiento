package com.alojamientos.backend.domain;

public class Cotizacion {
    private double precioEstancia;
    private double impuestos;
    private double tarifaServicio;
    private double precioFinal;

    public Cotizacion(double precioEstancia, double impuestos, double tarifaServicio, double precioFinal) {
        this.precioEstancia = precioEstancia;
        this.impuestos = impuestos;
        this.tarifaServicio = tarifaServicio;
        this.precioFinal = precioFinal;
    }

    public double getPrecioEstancia() { return precioEstancia; }
    public double getImpuestos() { return impuestos; }
    public double getTarifaServicio() { return tarifaServicio; }
    public double getPrecioFinal() { return precioFinal; }
}