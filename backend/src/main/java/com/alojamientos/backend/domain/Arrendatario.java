package com.alojamientos.backend.domain;

public class Arrendatario {
    private String id;
    private String nombre;
    private String email;
    private DatosBancarios datosBancarios;

    public Arrendatario(String id, String nombre, String email, DatosBancarios datosBancarios) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.datosBancarios = datosBancarios;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public DatosBancarios getDatosBancarios() { return datosBancarios; }
}