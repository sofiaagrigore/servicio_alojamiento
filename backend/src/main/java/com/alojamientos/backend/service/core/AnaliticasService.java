package com.alojamientos.backend.service.core;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alojamientos.backend.service.fake.BatchService;
import com.alojamientos.backend.service.fake.VelocidadService;

@Service
public class AnaliticasService {

    @Autowired private BatchService batchService;
    @Autowired private VelocidadService velocidadService;
    @Autowired private ResenasService resenasService;

    public Map<String, Object> getAnaliticas(String alojamientoId) {
        Map<String, Integer> historicos = batchService.getDatosHistoricos(alojamientoId);
        Map<String, Integer> recientes = velocidadService.getEventosRecientes(alojamientoId);
        long numeroResenas = resenasService.getNumeroResenas(alojamientoId);

        return Map.of(
            "alojamientoId", alojamientoId,
            "metricas", Map.of(
                "visitas_totales", historicos.get("visitas_historicas") + recientes.get("visitas_recientes"),
                "likes_totales", historicos.get("likes_historicos") + recientes.get("likes_recientes"),
                "numero_reseñas", numeroResenas
            )
        );
    }
}
