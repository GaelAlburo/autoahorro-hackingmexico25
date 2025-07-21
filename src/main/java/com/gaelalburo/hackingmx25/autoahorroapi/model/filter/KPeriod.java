package com.gaelalburo.hackingmx25.autoahorroapi.model.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Representa un periodo tipo K que agrupa transacciones para fines de resumen de inversión.
 * No afecta la validez de la transacción.
 */
@Data
public class KPeriod {
    @Schema(description = "Fecha y hora de inicio del periodo K", example = "2023-01-01 00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start;

    @Schema(description = "Fecha y hora de fin del periodo K", example = "2023-12-31 23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end;
}
