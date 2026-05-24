package com.alojamientos.backend.controller;

import com.alojamientos.backend.domain.*;
import com.alojamientos.backend.service.core.ReservasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/reservas")
public class ReservasRESTController {

    @Autowired private ReservasService reservasService;

    @GetMapping("/disponibilidad/{alojamientoId}")
    public ResponseEntity<Cotizacion> getDisponibilidadYCotizacion(@PathVariable String alojamientoId) {
        Cotizacion cotizacion = reservasService.generarCotizacion(alojamientoId, LocalDate.now(), LocalDate.now().plusDays(3));
        return ResponseEntity.ok(cotizacion);
    }

    @PostMapping
    public ResponseEntity<?> confirmarReserva(@RequestBody ReservaRequest request) {
        try {
            Reserva reserva = reservasService.confirmarReserva(
                request.getAlojamientoId(),
                request.getArrendatarioId(),
                request.getFechaEntrada(),
                request.getFechaSalida(),
                request.getNumPersonas()
            );
            return ResponseEntity.ok(Map.of("reservaId", reserva.getId(), "estado", reserva.getEstado()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{reservaId}")
    public ResponseEntity<?> cancelarReserva(@PathVariable String reservaId) {
        try {
            Reserva reserva = reservasService.cancelarReserva(reservaId);
            return ResponseEntity.ok(Map.of("reservaId", reserva.getId(), "estado", reserva.getEstado()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}