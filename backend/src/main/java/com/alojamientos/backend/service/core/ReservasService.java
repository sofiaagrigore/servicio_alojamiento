package com.alojamientos.backend.service.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alojamientos.backend.domain.Alojamiento;
import com.alojamientos.backend.domain.Arrendatario;
import com.alojamientos.backend.domain.Cotizacion;
import com.alojamientos.backend.domain.Pago;
import com.alojamientos.backend.domain.Reserva;
import com.alojamientos.backend.repository.ReservasRepository;
import com.alojamientos.backend.service.fake.MessageBroker;
import com.alojamientos.backend.service.fake.NotificacionesService;
import com.alojamientos.backend.service.fake.PagosService;

@Service
public class ReservasService {

    @Autowired private ReservasRepository reservasRepository;
    @Autowired private DisponibilidadService disponibilidadService;
    @Autowired private AlojamientosService alojamientosService;
    @Autowired private ArrendatariosService arrendatariosService;
    @Autowired private PagosService pagosService;
    @Autowired private NotificacionesService notificacionesService;
    @Autowired private MessageBroker messageBroker;

    public Cotizacion generarCotizacion(String alojamientoId, LocalDate entrada, LocalDate salida) {
        Alojamiento alojamiento = alojamientosService.getAlojamiento(alojamientoId);
        long noches = ChronoUnit.DAYS.between(entrada, salida);
        double precioEstancia = alojamiento.getPrecioPorNoche() * noches;
        double impuestos = precioEstancia * 0.10;
        double tarifaServicio = 15.00; 
        return new Cotizacion(precioEstancia, impuestos, tarifaServicio, (precioEstancia + impuestos + tarifaServicio));
    }

    public Reserva confirmarReserva(String alojamientoId, String arrendatarioId, LocalDate entrada, LocalDate salida, int numPersonas) {
        
        if (salida.isBefore(entrada) || salida.isEqual(entrada)) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada.");
        }
        if (entrada.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de entrada no puede ser en el pasado.");
        }
        if (numPersonas < 1) {
            throw new IllegalArgumentException("El número de personas debe ser al menos 1.");
        }

        if (!disponibilidadService.comprobarDisponibilidad(alojamientoId)) {
            throw new RuntimeException("Alojamiento no disponible.");
        }

        Arrendatario arrendatario = arrendatariosService.getArrendatario(arrendatarioId);
        Cotizacion cotizacion = generarCotizacion(alojamientoId, entrada, salida);
        
        if (!disponibilidadService.comprobarDisponibilidad(alojamientoId)) {
            throw new RuntimeException("Alojamiento no disponible.");
        }

        Pago pago = pagosService.procesarPago(arrendatario, cotizacion.getPrecioFinal());
        Alojamiento alojamiento = alojamientosService.getAlojamiento(alojamientoId);
        
        Reserva nuevaReserva = new Reserva(UUID.randomUUID().toString(), arrendatario, alojamiento, entrada, salida, numPersonas);
        nuevaReserva.setEstado("Confirmada");
        nuevaReserva.setPago(pago);
        
        reservasRepository.save(nuevaReserva);
        disponibilidadService.bloquearFechas(alojamientoId);
        notificacionesService.notificarConfirmacion(nuevaReserva);
        
        return nuevaReserva;
    }

    public Reserva cancelarReserva(String reservaId) {
        Reserva reserva = reservasRepository.findById(reservaId);
        if (reserva == null) throw new RuntimeException("Reserva no encontrada");

        long horasAntelacion = ChronoUnit.HOURS.between(LocalDateTime.now(),reserva.getFechaEntrada().atStartOfDay());
        if (horasAntelacion < 48) {
            throw new RuntimeException("Faltan menos de 48 horas para el check-in.");
        }

        reserva.setEstado("Cancelada");
        reservasRepository.save(reserva);
        
        messageBroker.publicarEvento("RESERVA_CANCELADA", reserva.getId(), reserva.getAlojamiento().getId(), reserva.getArrendatario().getId(), reserva.getPago().getImporte());

        return reserva;
    }
    
    public Reserva getReserva(String id) { return reservasRepository.findById(id); }

    public Reserva getReservaPorArrendatarioYAlojamiento(String arrendatarioId, String alojamientoId) {
        return reservasRepository.findByArrendatarioIdAndAlojamientoId(arrendatarioId, alojamientoId);
    }
}