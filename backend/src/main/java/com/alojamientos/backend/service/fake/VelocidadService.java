package com.alojamientos.backend.service.fake;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class VelocidadService {
    public Map<String, Integer> getEventosRecientes(String alojamientoId) {
        if (alojamientoId.equals("1")) {
        return Map.of("visitas_recientes", 40, "likes_recientes", 12);
    } else if (alojamientoId.equals("2")) {
        return Map.of("visitas_recientes", 15, "likes_recientes", 5);
    }
    return Map.of("visitas_recientes", 0, "likes_recientes", 0);
    }
}
