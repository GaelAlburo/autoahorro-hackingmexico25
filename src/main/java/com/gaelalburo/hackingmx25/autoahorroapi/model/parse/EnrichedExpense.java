package com.gaelalburo.hackingmx25.autoahorroapi.model.parse;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    /**
     * Cantidad original del gasto
     */
    @Schema(
            description = "La cantidad original gastada",
            example = "250.0"
    )
    @NotNull(message = "Amount is required")
    //@Min(value = 0, message = "Amount must be non-negative")
    private Double amount;

    /**
     * El siguiente múltiplo superior de 100 de la cantidad
     * Usado para sugerir un umbral de ahorro
     */
    @Schema(
            description = "La cantidad redondeada de ceiling (siguiente múltiplo de 100)",
            example = "300.0"
    )
    @NotNull(message = "Ceiling is required")
    //@Min(value = 0, message = "Ceiling must be non-negative")
    private Double ceiling;

    /**
     * La diferencia entre el valor ceiling y la cantidad original
     * Representa la cantidad sugerida de auto-ahorro
     */
    @Schema(
            description = "La diferencia entre ceiling y la cantidad original (ahorro sugerido)",
            example = "50.0"
    )
    @NotNull(message = "Remanent is required")
    //@Min(value = 0, message = "Remanent must be non-negative")
    private Double remanent;
}
