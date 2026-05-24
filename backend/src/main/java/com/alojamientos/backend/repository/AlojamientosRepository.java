package com.alojamientos.backend.repository;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.alojamientos.backend.domain.Alojamiento;

@Repository
public class AlojamientosRepository {
    private final ConcurrentHashMap<String, Alojamiento> db = new ConcurrentHashMap<>();
    
    public AlojamientosRepository() {
        db.put("1", new Alojamiento("1", 75.00));
        db.put("2", new Alojamiento("2", 120.00));
        db.put("3", new Alojamiento("3", 45.50));
        db.put("4", new Alojamiento("4", 250.00));
        db.put("5", new Alojamiento("5", 60.00));
    }
    
    public Alojamiento findById(String id) { 
        return db.get(id); 
    }
}