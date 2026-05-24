package com.alojamientos.backend.service.fake;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class VelocidadService {
    public Map<String, Integer> getEventosRecientes(String alojamientoId) {
        return Map.of("visitas_recientes", 40, "likes_recientes", 12);
    }
}