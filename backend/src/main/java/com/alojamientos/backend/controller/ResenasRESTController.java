package com.alojamientos.backend.controller;

import com.alojamientos.backend.domain.ResenaRequest;
import com.alojamientos.backend.domain.Reseña;
import com.alojamientos.backend.service.core.ResenasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/reseñas")
public class ResenasRESTController {

    @Autowired private ResenasService resenasService;

    @GetMapping("/verificar/{reservaId}")
    public ResponseEntity<?> verificarReservaParaReseña(@PathVariable String reservaId) {
        try {
            resenasService.verificarPosibilidadDeReseña(reservaId);
            return ResponseEntity.ok(Map.of("status", "Permitido"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{alojamientoId}")
    public ResponseEntity<?> escribirReseña(@PathVariable String alojamientoId, @RequestBody ResenaRequest request) {
        try {
            resenasService.verificarPosibilidadDeReseña(request.getReservaId());
            Reseña reseña = resenasService.guardarReseña(
                alojamientoId,
                request.getArrendatarioId(),
                request.getPuntuacion(),
                request.getComentario()
            );
            return ResponseEntity.ok(reseña);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
