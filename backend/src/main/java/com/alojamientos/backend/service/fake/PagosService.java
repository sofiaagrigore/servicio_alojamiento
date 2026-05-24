package com.alojamientos.backend.service.fake;

import org.springframework.stereotype.Service;

import com.alojamientos.backend.domain.Arrendatario;
import com.alojamientos.backend.domain.Pago;

@Service
public class PagosService {
    public Pago procesarPago(Arrendatario arrendatario, double precioFinal) {
        System.out.println("[FAKE PAGOS] Procesando cobro de " + precioFinal + "€ a " + arrendatario.getNombre());
        return new Pago("Completado", precioFinal);
    }

    public void onEvento(String evento, String arrendatarioId, double importe) {
        System.out.println("[FAKE PAGOS] Reembolso de " + importe + "€ al usuario " + arrendatarioId);
    }
}
