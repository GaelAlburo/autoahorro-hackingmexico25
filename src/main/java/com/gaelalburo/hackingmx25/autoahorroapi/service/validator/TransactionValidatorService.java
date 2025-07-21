package com.gaelalburo.hackingmx25.autoahorroapi.service.validator;

import com.gaelalburo.hackingmx25.autoahorroapi.model.parse.EnrichedExpense;
import com.gaelalburo.hackingmx25.autoahorroapi.model.validator.TransactionValidationResult;

import java.util.List;

/**
 * Lógica de negocio para validar transacciones
 */
public interface TransactionValidatorService {
    /**
     * Toma una lista de transacciones y un sueldo, valida cada una de las transacciones de acuerdo
     * a las 3 validaciones establecidas.
     * @param wage Salario mensual
     * @param transactions Lista de transacciones enriquecidas
     * @return Objeto con transacciones válidas e inválidas
     */
    TransactionValidationResult validatedTransactions(Double wage, List<EnrichedExpense> transactions);
}
