package com.alojamientos.backend.repository;

import org.springframework.stereotype.Repository;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DisponibilidadRepository {
    private final ConcurrentHashMap<String, Boolean> db = new ConcurrentHashMap<>();
    
    public boolean findByAlojamientoId(String id) { return db.getOrDefault(id, true); }
    public void saveDisponibilidad(String id, boolean disponible) { db.put(id, disponible); }
}