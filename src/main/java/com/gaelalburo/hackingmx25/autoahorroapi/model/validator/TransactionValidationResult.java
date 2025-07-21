package com.gaelalburo.hackingmx25.autoahorroapi.model.validator;

import com.gaelalburo.hackingmx25.autoahorroapi.model.parse.EnrichedExpense;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Salida que agrupa las transacciones tanto válidas como inválidas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionValidationResult {
    /**
     * Lista de transacciones consideradas como válidas
     */
    @Schema(description = "Lista de transaccion válidas")
    private List<EnrichedExpense> valid;

    /**
     * Lista de transacciones inválidas, con un campo adicional message
     */
    @Schema(description = "Lista de transacciones inválidas con su mensaje de error")
    private List<InvalidTransaction> invalid;
}
