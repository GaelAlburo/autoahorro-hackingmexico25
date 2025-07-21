package com.gaelalburo.hackingmx25.autoahorroapi.model.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Representa un periodo tipo Q donde el remanente por transacción debe ser fijo.
 */
@Data
public class QPeriod {
    @Schema(description = "Monto fijo permitido por transacción en este periodo", example = "0.0")
    //@Min(value = 0, message = "Fixed must be non-negative")
    private Double fixed;

    @Schema(description = "Fecha y hora de inicio del periodo Q", example = "2023-07-01 00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start;

    @Schema(description = "Fecha y hora de fin del periodo Q", example = "2023-07-31 23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end;
}
