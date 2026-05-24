package com.alojamientos.backend.repository;

import com.alojamientos.backend.domain.Reseña;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ResenasRepository {
    private final ConcurrentHashMap<String, Reseña> db = new ConcurrentHashMap<>();
    
    public void save(Reseña reseña) { db.put(reseña.getId(), reseña); }
    
    public long countByAlojamientoId(String alojamientoId) {
        return db.values().stream().filter(r -> r.getAlojamientoId().equals(alojamientoId)).count();
    }
}
