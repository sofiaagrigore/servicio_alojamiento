package com.alojamientos.backend.domain;

public class Reseña {
    private String id;
    private String alojamientoId;
    private String arrendatarioId;
    private int puntuacion;
    private String comentario;

    public Reseña(String id, String alojamientoId, String arrendatarioId, int puntuacion, String comentario) {
        this.id = id;
        this.alojamientoId = alojamientoId;
        this.arrendatarioId = arrendatarioId;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
    }

    public String getId() { return id; }
    public String getAlojamientoId() { return alojamientoId; }
    public int getPuntuacion() { return puntuacion; }
    public String getComentario() { return comentario; }
}