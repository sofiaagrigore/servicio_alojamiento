package com.alojamientos.backend.service.core;

import com.alojamientos.backend.domain.*;
import com.alojamientos.backend.repository.ReservasRepository;
import com.alojamientos.backend.repository.ResenasRepository;
import com.alojamientos.backend.service.fake.MessageBroker;
import com.alojamientos.backend.service.fake.NotificacionesService;
import com.alojamientos.backend.service.fake.PagosService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServicesStructuralTest {

    // --- Mocks para ReservasService ---
    @Mock private ReservasRepository reservasRepository;
    @Mock private DisponibilidadService disponibilidadService;
    @Mock private AlojamientosService alojamientosService;
    @Mock private ArrendatariosService arrendatariosService;
    @Mock private PagosService pagosService;
    @Mock private NotificacionesService notificacionesService;
    @Mock private MessageBroker messageBroker;

    @InjectMocks
    private ReservasService reservasService;

    // --- Mocks para ResenasService ---
    @Mock private ResenasRepository resenasRepository;
    @Mock private ReservasService mockReservasService;

    @InjectMocks
    private ResenasService resenasService;

    // =========================================================================
    // PRUEBAS ESTRUCTURALES: ReservasService.confirmarReserva(...)
    //
    // Caminos del código real (ReservasService.java):
    //   C1: salida <= entrada  -> IllegalArgumentException "La fecha de salida debe ser posterior a la fecha de entrada."
    //   C2: entrada < hoy      -> IllegalArgumentException "La fecha de entrada no puede ser en el pasado."
    //   C3: numPersonas < 1    -> IllegalArgumentException (tercer guard)
    //   C4: !disponibilidad    -> RuntimeException "Alojamiento no disponible."
    //   C5: pago != Completado -> no existe este camino en el código real (PagosService.fake siempre devuelve Completado)
    //   C6: flujo completo OK  -> Reserva con estado "Confirmada"
    // =========================================================================

    @Test
    public void testConfirmarReserva_Camino1_FechaSalidaNoEsPosteriorAEntrada() {
        // C1: salida igual a entrada => debe lanzar IllegalArgumentException
        LocalDate mismaFecha = LocalDate.now().plusDays(2);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            reservasService.confirmarReserva("1", "1", mismaFecha, mismaFecha, 2)
        );
        assertEquals("La fecha de salida debe ser posterior a la fecha de entrada.", ex.getMessage());
    }

    @Test
    public void testConfirmarReserva_Camino2_FechaEntradaEnElPasado() {
        // C2: entrada anterior a hoy => debe lanzar IllegalArgumentException
        // Nota: este guard está DESPUÉS del de fechas, así que salida debe ser > entrada
        LocalDate entradaPasada = LocalDate.now().minusDays(2);
        LocalDate salidaPasada  = LocalDate.now().minusDays(1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            reservasService.confirmarReserva("1", "1", entradaPasada, salidaPasada, 2)
        );
        assertEquals("La fecha de entrada no puede ser en el pasado.", ex.getMessage());
    }

    @Test
    public void testConfirmarReserva_Camino3_NumeroPersonasMenorQueUno() {
        // C3: numPersonas = 0 => IllegalArgumentException
        // Las fechas son válidas para no activar guards anteriores
        LocalDate entrada = LocalDate.now().plusDays(1);
        LocalDate salida  = LocalDate.now().plusDays(3);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            reservasService.confirmarReserva("1", "1", entrada, salida, 0)
        );
        assertEquals("El número de personas debe ser al menos 1.", ex.getMessage());
    }

    @Test
    public void testConfirmarReserva_Camino4_AlojamientoNoDisponible() {
        // C4: disponibilidad = false => RuntimeException
        LocalDate entrada = LocalDate.now().plusDays(1);
        LocalDate salida  = LocalDate.now().plusDays(4);

        when(disponibilidadService.comprobarDisponibilidad("1")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            reservasService.confirmarReserva("1", "1", entrada, salida, 2)
        );
        assertEquals("Alojamiento no disponible.", ex.getMessage());
    }

    @Test
    public void testConfirmarReserva_Camino5_FlujoCompletoExitoso() {
        // C5: todos los guards pasan => Reserva con estado "Confirmada"
        LocalDate entrada = LocalDate.now().plusDays(1);
        LocalDate salida  = LocalDate.now().plusDays(4);

        Alojamiento alojamiento = new Alojamiento("1", 100.0);
        DatosBancarios db = new DatosBancarios("Juan", "ES12", "TARJETA", "1234");
        Arrendatario arrendatario = new Arrendatario("1", "Juan", "juan@x.com", db);
        Pago pagoCompletado = new Pago("Completado", 315.0);

        when(disponibilidadService.comprobarDisponibilidad("1")).thenReturn(true);
        when(arrendatariosService.getArrendatario("1")).thenReturn(arrendatario);
        when(alojamientosService.getAlojamiento("1")).thenReturn(alojamiento);
        when(pagosService.procesarPago(any(), anyDouble())).thenReturn(pagoCompletado);

        Reserva resultado = reservasService.confirmarReserva("1", "1", entrada, salida, 2);

        assertNotNull(resultado);
        assertEquals("Confirmada", resultado.getEstado());
        assertEquals(pagoCompletado, resultado.getPago());

        // Verificaciones estructurales de efectos colaterales obligatorios
        verify(reservasRepository, times(1)).save(resultado);
        verify(disponibilidadService, times(1)).bloquearFechas("1");
        verify(notificacionesService, times(1)).notificarConfirmacion(resultado);
    }

    // =========================================================================
    // PRUEBAS ESTRUCTURALES: ResenasService.guardarResena(...)
    //
    // Caminos del código real (ResenasService.java):
    //   C1: reserva == null           -> RuntimeException "No tienes una estancia finalizada en este alojamiento."
    //   C2: puntuacion < 1 o > 5      -> IllegalArgumentException "Datos de la reseña inválidos."
    //   C3: comentario vacío/nulo     -> IllegalArgumentException "Datos de la reseña inválidos."
    //   C4: flujo completo OK         -> Resena guardada y retornada
    // =========================================================================

    @Test
    public void testGuardarResena_Camino1_SinEstanciaFinalizada() {
        // C1: no existe reserva confirmada para ese arrendatario y alojamiento
        when(mockReservasService.getReservaPorArrendatarioYAlojamiento("1", "1")).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            resenasService.guardarResena("1", "1", 5, "Todo perfecto")
        );
        assertEquals("No tienes una estancia finalizada en este alojamiento.", ex.getMessage());
    }

    @Test
    public void testGuardarResena_Camino2_PuntuacionPorDebajoDeLimite() {
        // C2a: puntuacion = 0 (< 1)
        Reserva mockReserva = mock(Reserva.class);
        when(mockReservasService.getReservaPorArrendatarioYAlojamiento("1", "1")).thenReturn(mockReserva);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            resenasService.guardarResena("1", "1", 0, "Malo")
        );
        assertEquals("Datos de la reseña inválidos.", ex.getMessage());
    }

    @Test
    public void testGuardarResena_Camino2b_PuntuacionPorEncimaDeLimite() {
        // C2b: puntuacion = 6 (> 5)
        Reserva mockReserva = mock(Reserva.class);
        when(mockReservasService.getReservaPorArrendatarioYAlojamiento("1", "1")).thenReturn(mockReserva);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            resenasService.guardarResena("1", "1", 6, "Genial")
        );
        assertEquals("Datos de la reseña inválidos.", ex.getMessage());
    }

    @Test
    public void testGuardarResena_Camino3_ComentarioVacio() {
        // C3: comentario con solo espacios en blanco
        Reserva mockReserva = mock(Reserva.class);
        when(mockReservasService.getReservaPorArrendatarioYAlojamiento("1", "1")).thenReturn(mockReserva);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            resenasService.guardarResena("1", "1", 4, "     ")
        );
        assertEquals("Datos de la reseña inválidos.", ex.getMessage());
    }

    @Test
    public void testGuardarResena_Camino4_FlujoCompletoExitoso() {
        // C4: reserva existe, puntuación válida (1-5), comentario no vacío => Resena guardada
        Reserva mockReserva = mock(Reserva.class);
        when(mockReservasService.getReservaPorArrendatarioYAlojamiento("1", "1")).thenReturn(mockReserva);

        Resena resenaCreada = resenasService.guardarResena("1", "1", 4, "Muy limpio y cómodo");

        assertNotNull(resenaCreada);
        assertEquals("1", resenaCreada.getAlojamientoId());
        assertEquals(4, resenaCreada.getPuntuacion());
        assertEquals("Muy limpio y cómodo", resenaCreada.getComentario());

        verify(resenasRepository, times(1)).save(resenaCreada);
    }
}
