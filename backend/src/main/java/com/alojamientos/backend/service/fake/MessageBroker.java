package com.alojamientos.backend.service.fake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.alojamientos.backend.service.core.DisponibilidadService;

@Service
public class MessageBroker {
    
    @Autowired @Lazy private DisponibilidadService disponibilidadService;
    @Autowired private PagosService pagosService;
    @Autowired private NotificacionesService notificacionesService;

    public void publicarEvento(String evento, String reservaId, String alojamientoId, String arrendatarioId, double importe) {
        System.out.println("[FAKE BROKER] Evento publicado: " + evento);
        disponibilidadService.onEvento(evento, alojamientoId);
        pagosService.onEvento(evento, arrendatarioId, importe);
        notificacionesService.onEvento(evento, reservaId);
    }
}
