package com.alojamientos.backend.service.fake;

import org.springframework.stereotype.Service;

import com.alojamientos.backend.domain.Reserva;

@Service
public class NotificacionesService {
    public void notificarConfirmacion(Reserva reserva) {
        System.out.println("[FAKE NOTIFICACIONES] Correo y Push enviados para reserva " + reserva.getId());
    }

    public void onEvento(String evento, String reservaId) {
        System.out.println("[FAKE NOTIFICACIONES] Notificando cancelación de reserva: " + reservaId);
    }
}