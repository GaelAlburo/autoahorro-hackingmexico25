package com.gaelalburo.hackingmx25.autoahorroapi.model.validator;

import com.gaelalburo.hackingmx25.autoahorroapi.model.parse.EnrichedExpense;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Extensión de una transaccion enriquecida que incluye el mensaje
 * explicando el porqué se considera como transacción inválida
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class InvalidTransaction extends EnrichedExpense {
    /**
     * Mensaje explicando la razón por la cual la transacción es inválida
     * Se consideran 3 razones por las cuales una transacción puede ser inválida:
     *      1. No debe ser duplicada
     *      2. Consistencia de valores amount, ceiling y remanent.
     *          2.1. amount > ceiling
     *          2.2. ceiling debe ser el mínimo múltiplo de 100 mayor o igual a amount
     *          2.3. remanent = ceiling - amount
     *      3. La suma de los remanentes en un mismo mes no debe superar el 10% del sueldo mensual.
     */
    @Schema(description = "Motivo de invalidez de la transacción", example = "Amount exceeds 10% of monthly wage")
    private String message;

    public InvalidTransaction(LocalDateTime date, Double amount, Double ceiling, Double remanent, String message) {
        super(date, amount, ceiling, remanent);
        this.message = message;
    }
}
