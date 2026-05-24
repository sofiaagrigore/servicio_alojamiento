package com.alojamientos.backend.repository;

import com.alojamientos.backend.domain.Arrendatario;
import com.alojamientos.backend.domain.DatosBancarios;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ArrendatariosRepository {
    private final ConcurrentHashMap<String, Arrendatario> db = new ConcurrentHashMap<>();
    
    public ArrendatariosRepository() {
        db.put("1", new Arrendatario("1", "Juan Perez", "juan@example.com", 
            new DatosBancarios("Juan Perez", "ES123456789", "TARJETA")));
    }
    public Arrendatario findById(String id) { return db.get(id); }
}