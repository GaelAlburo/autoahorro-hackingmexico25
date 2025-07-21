package com.gaelalburo.hackingmx25.autoahorroapi.controller;

import com.gaelalburo.hackingmx25.autoahorroapi.model.performance.PerformanceReport;
import com.gaelalburo.hackingmx25.autoahorroapi.service.performance.PerformanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 5. Reporte de Performance
 * Reporta métricas de ejecución del sistema como tiempo de respuesta, uso de memoria
 * y número de hilos utilizados
 * Endpoint: /blackrock/challenge/v1/performance
 */
@RestController
@RequestMapping("/blackrock/challenge/v1")
@Tag(name = "Performance", description = "Reporte de métricas de ejecución del sistema")
public class PerformanceController {
    private final PerformanceService performanceService;

    @Autowired
    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    /**
     * Endpoint para consultar el estado actual de performance del sistema.
     * Devuelve información sobre el tiempo desde el arranque, memoria usada
     * y número de hilos activos.
     *
     * @return ResponseEntity con los datos del reporte de performance
     */
    @GetMapping("/performance")
    @Operation(summary = "Obtiene métricas del sistema", description = "Devuelve tiempo de ejecución, memoria y número de hilos activos.")
    public ResponseEntity<PerformanceReport> getPerformanceReport() {
        PerformanceReport report = performanceService.generateReport();
        return ResponseEntity.ok(report);
    }
}
