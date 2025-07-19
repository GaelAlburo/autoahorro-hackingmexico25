package com.gaelalburo.hackingmx25.autoahorroapi.service;

import com.gaelalburo.hackingmx25.autoahorroapi.model.EnrichedExpense;
import com.gaelalburo.hackingmx25.autoahorroapi.model.ExpenseInput;

import java.util.List;

/**
 * Lógica de negocio para enriquecer transaccioes
 */
public interface ExpenseService {
    /**
     * Toma una lista de gastos y enriquece cada una agregando los campos ceiling y remanent
     * @param expenses Lista de gastos
     * @return Lista de transacciones enriquecidas
     */
    List<EnrichedExpense> enrichExpenses(List<ExpenseInput> expenses);
}
