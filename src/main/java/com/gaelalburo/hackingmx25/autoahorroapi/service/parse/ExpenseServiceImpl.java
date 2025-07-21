package com.gaelalburo.hackingmx25.autoahorroapi.service.parse;

import com.gaelalburo.hackingmx25.autoahorroapi.model.parse.EnrichedExpense;
import com.gaelalburo.hackingmx25.autoahorroapi.model.parse.ExpenseInput;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    /**
     * Enriquece una lista de gastos calculando el techo y el remanente de cada uno.
     *
     * Para cada gasto, calcula:
     * - ceiling: el siguiente múltiplo de 100 mayor o igual al monto.
     * - remanent: la diferencia entre el ceiling y el monto.
     *
     * @param expenses Lista de gastos
     * @return Lista de gastos enriquecidos con monto, ceiling y remanente.
     */
    @Override
    public List<EnrichedExpense> enrichExpenses(List<ExpenseInput> expenses) {
        return expenses.stream()
                .map(e -> {
                    double amount = e.getAmount();
                    double ceiling = Math.ceil(amount / 100.0) * 100;
                    double remanent = ceiling - amount;

                    return new EnrichedExpense(
                            e.getDate(),
                            amount,
                            ceiling,
                            remanent
                    );
                })
                .collect(Collectors.toList());
    }
}
