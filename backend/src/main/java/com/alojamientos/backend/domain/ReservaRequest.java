package com.alojamientos.backend.domain;

import java.time.LocalDate;

public class ReservaRequest {
    private String alojamientoId;
    private String arrendatarioId;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private int numPersonas;

    public String getAlojamientoId() { return alojamientoId; }
    public String getArrendatarioId() { return arrendatarioId; }
    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public LocalDate getFechaSalida() { return fechaSalida; }
    public int getNumPersonas() { return numPersonas; }
}