package com.gaelalburo.hackingmx25.autoahorroapi.model.performance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Representa el reporte de performance del sistema.
 * Incluye información sobre el tiempo de ejecución desde el inicio,
 * la memoria utilizada en KB y la cantidad de hilos activos.
 */
@Data
@AllArgsConstructor
@Schema(description = "Reporte de performance de la aplicación")
public class PerformanceReport {
    @Schema(description = "Tiempo desde el inicio de la aplicación en formato HH:mm:ss:SSS", example = "00:15:20:435")
    private String time;

    @Schema(description = "Memoria utilizada en kilobytes (KB)", example = "10240.00")
    private String memory;

    @Schema(description = "Número de hilos activos", example = "10")
    private int threads;
}
