package com.alojamientos.backend.domain;

import java.time.LocalDate;

public class Reserva {
    private String id;
    private Arrendatario arrendatario;
    private Alojamiento alojamiento;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private int numPersonas;
    private String estado; 
    private Pago pago;

    public Reserva(String id, Arrendatario arrendatario, Alojamiento alojamiento, LocalDate fechaEntrada, LocalDate fechaSalida, int numPersonas) {
        this.id = id;
        this.arrendatario = arrendatario;
        this.alojamiento = alojamiento;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.numPersonas = numPersonas;
        this.estado = "Pendiente";
    }

    public String getId() { return id; }
    public Arrendatario getArrendatario() { return arrendatario; }
    public Alojamiento getAlojamiento() { return alojamiento; }
    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public LocalDate getFechaSalida() { return fechaSalida; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setPago(Pago pago) { this.pago = pago; }
}
