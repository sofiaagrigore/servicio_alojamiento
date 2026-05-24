package com.alojamientos.backend.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alojamientos.backend.domain.Arrendatario;
import com.alojamientos.backend.repository.ArrendatariosRepository;

@Service
public class ArrendatariosService {
    @Autowired private ArrendatariosRepository arrendatariosRepository;
    public Arrendatario getArrendatario(String id) { return arrendatariosRepository.findById(id); }
}
