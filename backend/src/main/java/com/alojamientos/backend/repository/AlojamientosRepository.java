package com.alojamientos.backend.repository;

import com.alojamientos.backend.domain.Alojamiento;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AlojamientosRepository {
    private final ConcurrentHashMap<String, Alojamiento> db = new ConcurrentHashMap<>();
    
    public AlojamientosRepository() {
        db.put("1", new Alojamiento("1", 75.00));
    }
    public Alojamiento findById(String id) { return db.get(id); }
}