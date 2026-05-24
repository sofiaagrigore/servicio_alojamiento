package com.alojamientos.backend.domain;

public class Arrendador {
    private String nombre;
    private String email;

    public Arrendador(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }
    
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
}