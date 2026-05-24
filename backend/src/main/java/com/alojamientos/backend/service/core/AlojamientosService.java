package com.alojamientos.backend.service.core;

import com.alojamientos.backend.domain.Alojamiento;
import com.alojamientos.backend.repository.AlojamientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlojamientosService {
    @Autowired private AlojamientosRepository alojamientosRepository;
    public Alojamiento getAlojamiento(String id) { return alojamientosRepository.findById(id); }
}
