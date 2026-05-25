package com.alojamientos.backend.service.fake;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class BatchService {
    public Map<String, Integer> getDatosHistoricos(String alojamientoId) {
        if (alojamientoId.equals("1")) {
        return Map.of("visitas_historicas", 1500, "likes_historicos", 330);
    } else if (alojamientoId.equals("2")) {
        return Map.of("visitas_historicas", 800, "likes_historicos", 150);
    }
    return Map.of("visitas_historicas", 0, "likes_historicos", 0);
    }
}
