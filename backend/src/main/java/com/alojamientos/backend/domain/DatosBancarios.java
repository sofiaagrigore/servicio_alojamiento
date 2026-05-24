package com.alojamientos.backend.domain;

public class DatosBancarios {
    private String titular;
    private String iban;
    private String metodoPago;
    private String terminacion;

    public DatosBancarios() {}

    public DatosBancarios(String titular, String iban, String metodoPago, String terminacion) {
        this.titular = titular;
        this.iban = iban;
        this.metodoPago = metodoPago;
        this.terminacion = terminacion;
    }

    public String getTitular() { return titular; }
    public String getIban() { return iban; }
    public String getMetodoPago() { return metodoPago; }
    public String getTerminacion() { return terminacion; }
}