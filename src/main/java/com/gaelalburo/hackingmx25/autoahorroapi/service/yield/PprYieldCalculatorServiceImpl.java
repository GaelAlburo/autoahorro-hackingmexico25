package com.gaelalburo.hackingmx25.autoahorroapi.service.yield;

import com.gaelalburo.hackingmx25.autoahorroapi.model.filter.KPeriod;
import com.gaelalburo.hackingmx25.autoahorroapi.model.parse.EnrichedExpense;
import com.gaelalburo.hackingmx25.autoahorroapi.model.yield.KPeriodReturn;
import com.gaelalburo.hackingmx25.autoahorroapi.model.yield.YieldInput;
import com.gaelalburo.hackingmx25.autoahorroapi.model.yield.YieldResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación del cálculo de rendimientos para el plan personal de retiro (PPR).
 * Incluye incentivo fiscal del 10% sobre el salario anual, sin intereses.
 */
@Slf4j
@Service("pprYieldCalculator")
public class PprYieldCalculatorServiceImpl implements YieldCalculatorService {

    private static final double ANNUAL_INTEREST = 0.0711;

    @Override
    public YieldResult calculateYield(YieldInput input) {
        int years = Math.max(65 - input.getAge(), 5);
        double totalAmount = input.getTransactions().stream().mapToDouble(EnrichedExpense::getAmount).sum();
        double totalCeiling = input.getTransactions().stream().mapToDouble(EnrichedExpense::getCeiling).sum();

        Map<KPeriod, Double> savingsByK = calculateSavingsByPeriod(input.getTransactions(), input.getK());
        List<KPeriodReturn> returns = new ArrayList<>();

        double totalInvested = 0;
        double totalFinal = 0;

        for (Map.Entry<KPeriod, Double> entry : savingsByK.entrySet()) {
            double base = entry.getValue();

            // Incentivo fiscal: se agrega como devolución extra al final
            double cappedInvestment = Math.min(base, input.getWage() * 12 * 0.10);
            double finalAmount = cappedInvestment * Math.pow(1 + ANNUAL_INTEREST, years);
            double extraReturn = cappedInvestment;

            double totalWithReturn = finalAmount + extraReturn;
            double inflationAdjusted = totalWithReturn / Math.pow(1 + input.getInflation() / 100, years);

            returns.add(new KPeriodReturn(entry.getKey().getStart(), entry.getKey().getEnd(), round(inflationAdjusted)));
            totalInvested += base;
            totalFinal += inflationAdjusted;
        }

        return new YieldResult(round(totalAmount), round(totalCeiling), round(totalInvested), round(totalFinal), returns);
    }

    private Map<KPeriod, Double> calculateSavingsByPeriod(List<EnrichedExpense> transactions, List<KPeriod> kPeriods) {
        Map<KPeriod, Double> savings = new LinkedHashMap<>();
        for (KPeriod period : kPeriods) {
            double sum = transactions.stream()
                    .filter(t -> isWithin(t.getDate(), period.getStart(), period.getEnd()))
                    .mapToDouble(EnrichedExpense::getRemanent)
                    .sum();
            savings.put(period, sum);
        }
        return savings;
    }

    /**
     * Verifica si una fecha dada está dentro de un rango de fechas (inclusive).
     *
     * @param date  la fecha que se desea verificar.
     * @param start la fecha de inicio del rango.
     * @param end   la fecha de fin del rango.
     * @return {@code true} si la fecha es igual a {@code start}, igual a {@code end}, o se encuentra entre ambas;
     *         {@code false} en caso contrario.
     *
     * Metodo generado con IA:
     * Prompt: "Write a Java method that checks if a given LocalDateTime
     * is within a start and end range (inclusive). Return true if it is equal
     * to the start or end, or falls between them."
     */
    private boolean isWithin(LocalDateTime date, LocalDateTime start, LocalDateTime end) {
        return (date.isEqual(start) || date.isAfter(start)) && (date.isEqual(end) || date.isBefore(end));
    }

    /**
     * Redondea un valor decimal a dos cifras decimales.
     *
     * @param value El valor decimal que se desea redondear.
     * @return Valor redondeado a dos decimales.
     */
    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
