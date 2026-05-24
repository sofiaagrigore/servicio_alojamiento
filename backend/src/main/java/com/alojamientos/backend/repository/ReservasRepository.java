package com.alojamientos.backend.repository;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.alojamientos.backend.domain.Reserva;

@Repository
public class ReservasRepository {
    private final ConcurrentHashMap<String, Reserva> db = new ConcurrentHashMap<>();
    
    public void save(Reserva reserva) { db.put(reserva.getId(), reserva); }
    public Reserva findById(String id) { return db.get(id); }
    
    public Reserva findByArrendatarioIdAndAlojamientoId(String arrendatarioId, String alojamientoId) {
    return db.values().stream()
        .filter(r -> r.getArrendatario().getId().equals(arrendatarioId)
                  && r.getAlojamiento().getId().equals(alojamientoId)
                  && r.getEstado().equals("Confirmada"))
        .findFirst()
        .orElse(null);
}
}
