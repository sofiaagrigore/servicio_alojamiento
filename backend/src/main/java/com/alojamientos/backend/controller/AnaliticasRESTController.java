package com.alojamientos.backend.controller;

import com.alojamientos.backend.service.core.AnaliticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/analiticas")
public class AnaliticasRESTController {

    @Autowired private AnaliticasService analiticasService;

    @GetMapping("/{alojamientoId}")
    public Map<String, Object> getAnaliticas(@PathVariable String alojamientoId) {
        return analiticasService.getAnaliticas(alojamientoId);
    }
}
