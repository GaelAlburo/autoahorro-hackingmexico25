package com.gaelalburo.hackingmx25.autoahorroapi.model.filter;

import com.gaelalburo.hackingmx25.autoahorroapi.model.parse.EnrichedExpense;
import com.gaelalburo.hackingmx25.autoahorroapi.model.validator.InvalidTransaction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Resultado de la validación de restricciones temporales, incluyendo transacciones válidas e inválidas.
 */
@Data
@AllArgsConstructor
public class TransactionPeriodResult {
    @Schema(description = "Transacciones válidas después de aplicar las validaciones")
    private List<EnrichedExpense> valid;

    @Schema(description = "Transacciones inválidas con explicación del error")
    private List<InvalidTransaction> invalid;
}