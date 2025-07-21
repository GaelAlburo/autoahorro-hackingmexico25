package com.gaelalburo.hackingmx25.autoahorroapi.service.validator;

import com.gaelalburo.hackingmx25.autoahorroapi.model.parse.EnrichedExpense;
import com.gaelalburo.hackingmx25.autoahorroapi.model.validator.InvalidTransaction;
import com.gaelalburo.hackingmx25.autoahorroapi.model.validator.TransactionValidationResult;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.*;

@Service
public class TransactionValidatorImpl implements TransactionValidatorService{

    private static final double MAX_INVESTMENT_PERCENTAGE = 0.10;

    /**
     * Valida una lista de transacciones enriquecidas según reglas específicas.
     *
     * Validaciones realizadas:
     * - No debe haber transacciones duplicadas (mismo monto y fecha).
     * - El monto no puede ser mayor que el techo (ceiling).
     * - El techo debe ser el siguiente múltiplo de 100 mayor o igual al monto.
     * - El remanente debe ser la diferencia correcta entre techo y monto.
     * - La suma del remanente por mes no debe exceder el 10% del sueldo.
     *
     * @param wage Salario mensual
     * @param transactions Lista de transacciones enriquecidas a validar
     * @return Resultado con listas de transacciones válidas e inválidas
     */
    @Override
    public TransactionValidationResult validatedTransactions(Double wage, List<EnrichedExpense> transactions) {
        Set<String> seen = new HashSet<>(); // Detecta duplicados
        Map<YearMonth, Double> monthlyRemanents = new HashMap<>(); // Remanent por mes

        List<EnrichedExpense> valid = new ArrayList<>();
        List<InvalidTransaction> invalid = new ArrayList<>();

        double maxMonthly = wage * MAX_INVESTMENT_PERCENTAGE; // Cantidad máxima al mes

        for (EnrichedExpense e : transactions){
            String uniqueKey = e.getDate() + "|" + e.getAmount();

            // Validación 1 - Duplicados
            if (!seen.add(uniqueKey)){
                invalid.add(buildInvalid(e, "Duplicate transaction"));
                continue;
            }

            // Validación 2 - Consistencia de valores
            // 2.1 - amount < ceiling
            if (e.getAmount() > e.getCeiling()){
                invalid.add(buildInvalid(e, "Invalid amount: amount exceeds ceiling"));
                continue;
            }

            // 2.2 - ceiling correcto
            double expectedCeiling = Math.ceil(e.getAmount() / 100.0) * 100;
            if (expectedCeiling != e.getCeiling()){
                invalid.add(buildInvalid(e, "Invalid ceiling: should be next multiple of 100 above amount"));
                continue;
            }

            // 2.3 - remanent correcto
            double expectedRemanent = expectedCeiling - e.getAmount();
            if (expectedRemanent != e.getRemanent()){
                invalid.add(buildInvalid(e, "Invalid remanent"));
                continue;
            }

            // Validación 3 - Remanente mensual total no exceda 10% del sueldo
            YearMonth month = YearMonth.from(e.getDate()); // Obtiene año y mes\

            // Se actualiza el remanent de ese mes y año:
            double total = monthlyRemanents.getOrDefault(month, 0.0);

            // Si al actualizar el remanent del mes sobrepasa el límite se invalida
            if (total + e.getRemanent() > maxMonthly) {
                invalid.add(buildInvalid(e, "Monthly remanent limit exceeded (max \" + maxMonthly + \")\""));
                continue;
            }

            // Pasó todas las validaciones
            monthlyRemanents.put(month, total + e.getRemanent());
            valid.add(e);
        }

        return new TransactionValidationResult(valid, invalid);
    }

    /**
     * Crea un objeto InvalidTransaction con los datos de una transacción y un mensaje de error.
     *
     * @param e La transacción enriquecida que se considera inválida.
     * @param msg Mensaje que explica la razón de la invalidez.
     * @return Un objeto InvalidTransaction con la información proporcionada.
     */
    private InvalidTransaction buildInvalid(EnrichedExpense e, String msg) {
        InvalidTransaction invalid = new InvalidTransaction();
        invalid.setDate(e.getDate());
        invalid.setAmount(e.getAmount());
        invalid.setCeiling(e.getCeiling());
        invalid.setRemanent(e.getRemanent());
        invalid.setMessage(msg);
        return invalid;
    }
}
