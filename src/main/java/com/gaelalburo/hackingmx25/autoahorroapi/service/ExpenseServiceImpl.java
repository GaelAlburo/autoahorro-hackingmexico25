package com.gaelalburo.hackingmx25.autoahorroapi.service;

import com.gaelalburo.hackingmx25.autoahorroapi.model.EnrichedExpense;
import com.gaelalburo.hackingmx25.autoahorroapi.model.ExpenseInput;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService{
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
