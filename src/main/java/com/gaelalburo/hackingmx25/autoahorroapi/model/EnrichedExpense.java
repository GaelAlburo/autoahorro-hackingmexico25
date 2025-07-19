package com.gaelalburo.hackingmx25.autoahorroapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Transaccion enriquecida de un gasto, incluye los campos calculados ceiling y remanent.
 */
@Schema(description = "Transaccion enriquecida incluyendo ceiling y remanent")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrichedExpense {
    /**
     * Fecha original del gasto
     */
    @Schema(
            description = "La fecha original del gasto",
            example = "2023-10-12T20:15:00"
    )
    private LocalDateTime date;

    /**
     * Cantidad original del gasto
     */
    @Schema(
            description = "La cantidad original gastada",
            example = "250.0"
    )
    private Double amount;

    /**
     * El siguiente múltiplo superior de 100 de la cantidad
     * Usado para sugerir un umbral de ahorro
     */
    @Schema(
            description = "La cantidad redondeada de ceiling (siguiente múltiplo de 100)",
            example = "300.0"
    )
    private Double ceiling;

    /**
     * La diferencia entre el valor ceiling y la cantidad original
     * Representa la cantidad sugerida de auto-ahorro
     */
    @Schema(
            description = "La diferencia entre ceiling y la cantidad original (ahorro sugerido)",
            example = "50.0"
    )
    private Double remanent;
}
