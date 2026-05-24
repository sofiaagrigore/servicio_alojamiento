package com.alojamientos.backend.domain;

public class ResenaRequest {
    private String reservaId;
    private String arrendatarioId;
    private int puntuacion;
    private String comentario;

    public String getReservaId() { return reservaId; }
    public String getArrendatarioId() { return arrendatarioId; }
    public int getPuntuacion() { return puntuacion; }
    public String getComentario() { return comentario; }
}