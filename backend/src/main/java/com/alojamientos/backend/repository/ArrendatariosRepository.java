package com.alojamientos.backend.repository;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.alojamientos.backend.domain.Arrendatario;
import com.alojamientos.backend.domain.DatosBancarios;

@Repository
public class ArrendatariosRepository {
    private final ConcurrentHashMap<String, Arrendatario> db = new ConcurrentHashMap<>();
    
    public ArrendatariosRepository() {
        db.put("1", new Arrendatario("1", "Juan Perez", "juan@example.com", 
            new DatosBancarios("Juan Perez", "ES12345678901234567890", "TARJETA", "7890")));
            
        db.put("2", new Arrendatario("2", "Ana Garcia", "ana.garcia@example.com", 
            new DatosBancarios("Ana Garcia", "ES98765432109876543210", "TARJETA", "3210")));
            
        db.put("3", new Arrendatario("3", "Luis Martinez", "luis.martinez@example.com", 
            new DatosBancarios("Luis Martinez", "ES55555555555555555555", "TRANSFERENCIA", "5555")));
            
        db.put("4", new Arrendatario("4", "Maria Smith", "maria.smith@example.com", 
            new DatosBancarios("Maria Smith", "GB22222222222222222222", "TARJETA", "2222")));
            
        db.put("5", new Arrendatario("5", "Carlos Ruiz", "carlos.ruiz@empresa.com", 
            new DatosBancarios("Carlos Ruiz", "ES11111111111111111111", "TRANSFERENCIA", "1111")));
    }
    
    public Arrendatario findById(String id) { 
        return db.get(id); 
    }
}