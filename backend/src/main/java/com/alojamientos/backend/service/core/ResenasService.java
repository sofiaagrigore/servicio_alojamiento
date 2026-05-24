package com.alojamientos.backend.service.core;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alojamientos.backend.domain.Resena;
import com.alojamientos.backend.domain.Reserva;
import com.alojamientos.backend.repository.ResenasRepository;

@Service
public class ResenasService {

    @Autowired private ResenasRepository resenasRepository;
    @Autowired private ReservasService reservasService;

    public void verificarPosibilidadDeResena(String reservaId) {
        Reserva reserva = reservasService.getReserva(reservaId);
        if (reserva == null) throw new RuntimeException("Reserva inexistente");

        if (LocalDate.now().isBefore(reserva.getFechaSalida())) {
            throw new RuntimeException("Solo puedes reseñar tras finalizar tu estancia.");
        }
    }

    public Resena guardarResena(String alojamientoId, String arrendatarioId, int puntuacion, String comentario) {
         Reserva reserva = reservasService.getReservaPorArrendatarioYAlojamiento(arrendatarioId, alojamientoId);
        if (reserva == null) {
            throw new RuntimeException("No tienes una estancia finalizada en este alojamiento.");
        }

        if (puntuacion < 1 || puntuacion > 5 || comentario == null || comentario.trim().isEmpty()) {
            throw new IllegalArgumentException("Datos de la reseña inválidos.");
        }
        
        Resena nuevaResena = new Resena(UUID.randomUUID().toString(), alojamientoId, arrendatarioId, puntuacion, comentario);
        resenasRepository.save(nuevaResena);
        return nuevaResena;
    }

    public long getNumeroResenas(String alojamientoId) {
        return resenasRepository.countByAlojamientoId(alojamientoId);
    }
}