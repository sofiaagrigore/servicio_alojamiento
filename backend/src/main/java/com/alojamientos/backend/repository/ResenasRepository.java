package com.alojamientos.backend.repository;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.alojamientos.backend.domain.Resena;

@Repository
public class ResenasRepository {
    private final ConcurrentHashMap<String, Resena> db = new ConcurrentHashMap<>();
    
    public void save(Resena resena) { db.put(resena.getId(), resena); }
    
    public long countByAlojamientoId(String alojamientoId) {
        return db.values().stream().filter(r -> r.getAlojamientoId().equals(alojamientoId)).count();
    }
}
