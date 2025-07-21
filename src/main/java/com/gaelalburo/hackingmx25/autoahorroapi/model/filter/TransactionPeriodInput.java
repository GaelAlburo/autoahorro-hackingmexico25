package com.gaelalburo.hackingmx25.autoahorroapi.model.filter;

import com.gaelalburo.hackingmx25.autoahorroapi.model.parse.EnrichedExpense;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * Representa la entrada para la validación de restricciones temporales,
 * incluyendo los periodos tipo Q, P, K, el sueldo mensual, y la lista de transacciones.
 */
@Data
public class TransactionPeriodInput {
    @Schema(description = "Lista de periodos tipo Q donde se fija el remanente por transacción")
    @NotNull(message = "Q periods must be provided")
    @Size(min = 1, message = "There must be at least one Q period")
    @Valid
    private List<QPeriod> q;

    @Schema(description = "Lista de periodos tipo P donde se suma un extra al remanente")
    @NotNull(message = "P periods must be provided")
    @Size(min = 1, message = "There must be at least one P period")
    @Valid
    private List<PPeriod> p;

    @Schema(description = "Lista de periodos tipo K para resumen (no afecta validez)")
    @NotNull(message = "K periods must be provided")
    @Size(min = 1, message = "There must be at least one K period")
    @Valid
    private List<KPeriod> k;

    @Schema(description = "Sueldo mensual", example = "50000")
    @NotNull(message = "Wage is required")
    private Double wage;

    @Schema(description = "Lista de transacciones enriquecidas a validar")
    @NotNull(message = "Transactions list is required")
    @Valid
    private List<EnrichedExpense> transactions;
}
