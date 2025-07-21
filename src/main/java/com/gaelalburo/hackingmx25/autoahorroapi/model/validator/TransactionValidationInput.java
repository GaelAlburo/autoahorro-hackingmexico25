package com.gaelalburo.hackingmx25.autoahorroapi.model.validator;

import com.gaelalburo.hackingmx25.autoahorroapi.model.parse.EnrichedExpense;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Datos de entrada para validar transacciones de acuerdo el salario
 */
@Data
@NoArgsConstructor
public class TransactionValidationInput {
    /**
     * Sueldo reportado
     * Se usa como base para determinar el porcentaje máximo de inversión permitida
     */
    @Schema(description = "Sueldo mensual", example = "500000")
    @NotNull(message = "Wage is required")
    @Min(value = 0, message = "Wage must me non-negative")
    private Double wage;

    /**
     * Lista de transacciones enriquecidas a validar
     */
    @Schema(description = "Lista de transacciones a validar")
    @NotNull(message = "List of transactions is required")
    @Size(min = 1, message = "There must be at least one transaction in the list")
    @Valid
    private List<EnrichedExpense> transactions;
}
