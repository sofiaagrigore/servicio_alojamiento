package com.alojamientos.backend.service.core;

import com.alojamientos.backend.repository.DisponibilidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisponibilidadService {
    @Autowired private DisponibilidadRepository disponibilidadRepository;

    public boolean comprobarDisponibilidad(String id) { return disponibilidadRepository.findByAlojamientoId(id); }
    public void bloquearFechas(String id) { disponibilidadRepository.saveDisponibilidad(id, false); }
    public void liberarFechas(String id) { disponibilidadRepository.saveDisponibilidad(id, true); }

    public void onEvento(String evento, String alojamientoId) {
        System.out.println("[DISPONIBILIDAD - ASÍNCRONO] Liberando fechas de caché Key/Value Store");
        liberarFechas(alojamientoId);
    }
}
