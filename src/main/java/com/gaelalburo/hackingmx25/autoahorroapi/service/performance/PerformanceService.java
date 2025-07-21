package com.gaelalburo.hackingmx25.autoahorroapi.service.performance;

import com.gaelalburo.hackingmx25.autoahorroapi.model.performance.PerformanceReport;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.time.Instant;

/**
 * Genera un reporte de performance del sistema.
 * Calcula el tiempo de ejecución desde el arranque, la memoria utilizada y
 * el número actual de hilos activos.
 */
@Service
public class PerformanceService {
    private final Instant startTime;

    public PerformanceService() {
        this.startTime = Instant.now();
    }

    /**
     * Genera un reporte con métricas del sistema:
     * - Tiempo de ejecución
     * - Uso de memoria
     * - Hilos activos
     * @return instancia de PerformanceReport con los datos actuales
     *
     * Metodo generado con IA
     * Prompt: "Generate a Java method that returns an object with the system's current runtime performance.
     * The method should calculate: (1) the uptime of the application since its start,
     * formatted as HH:mm:ss:SSS; (2) the memory currently used by the JVM in kilobytes
     * (using Runtime.getRuntime()); and (3) the current number of active threads (using ManagementFactory).
     * Return a DTO with these three values in a single object."
     */
    public PerformanceReport generateReport() {
        // 1. Calcular duración desde arranque
        Duration uptime = Duration.between(startTime, Instant.now());
        long hours = uptime.toHours();
        long minutes = uptime.toMinutes() % 60;
        long seconds = uptime.getSeconds() % 60;
        long millis = uptime.toMillis() % 1000;
        String formattedTime = String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, millis);

        // 2. Memoria usada (Runtime)
        long memoryInBytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        double memoryInKB = memoryInBytes / 1024.0;
        String memoryFormatted = String.format("%.2f", memoryInKB);

        // 3. Número de hilos activos
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        int threadCount = threadMXBean.getThreadCount();

        return new PerformanceReport(formattedTime, memoryFormatted, threadCount);
    }
}
