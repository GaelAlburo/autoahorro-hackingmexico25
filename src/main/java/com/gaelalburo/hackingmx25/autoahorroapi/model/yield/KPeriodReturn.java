package com.gaelalburo.hackingmx25.autoahorroapi.model.yield;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Representa el resumen de ahorro o retorno para un periodo tipo K.
 */
@Data
@AllArgsConstructor
@Schema(description = "Resumen de ahorro o retorno por periodo tipo K")
public class KPeriodReturn {
    @Schema(description = "Fecha de inicio del periodo", example = "2023-01-01T00:00")
    private LocalDateTime start;

    @Schema(description = "Fecha de fin del periodo", example = "2023-12-31T23:59")
    private LocalDateTime end;

    @Schema(description = "Retorno generado o cantidad invertida durante el periodo", example = "1281.64")
    private double amount;
}
