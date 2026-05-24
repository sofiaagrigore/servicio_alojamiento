package com.alojamientos.backend.controller;

import com.alojamientos.backend.domain.ResenaRequest;
import com.alojamientos.backend.domain.Resena;
import com.alojamientos.backend.service.core.ResenasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/resenas")
public class ResenasRESTController {

    @Autowired private ResenasService resenasService;

    @GetMapping("/verificar/{reservaId}")
    public ResponseEntity<?> verificarReservaParaResena(@PathVariable String reservaId) {
        try {
            resenasService.verificarPosibilidadDeResena(reservaId); // <-- Sin ñ
            return ResponseEntity.ok(Map.of("status", "Permitido"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{alojamientoId}")
    public ResponseEntity<?> escribirResena(@PathVariable String alojamientoId, @RequestBody ResenaRequest request) {
        try {
            resenasService.verificarPosibilidadDeResena(request.getReservaId()); // <-- Sin ñ
            
            Resena resena = resenasService.guardarResena(
                alojamientoId,
                request.getArrendatarioId(),
                request.getPuntuacion(),
                request.getComentario()
            );
            return ResponseEntity.ok(resena);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
