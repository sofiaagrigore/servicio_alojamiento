package com.alojamientos.backend.service.fake;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class BatchService {
    public Map<String, Integer> getDatosHistoricos(String alojamientoId) {
        return Map.of("visitas_historicas", 1500, "likes_historicos", 330);
    }
}
