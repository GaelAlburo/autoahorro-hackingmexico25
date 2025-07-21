package com.gaelalburo.hackingmx25.autoahorroapi.service.filter;

import com.gaelalburo.hackingmx25.autoahorroapi.model.filter.PPeriod;
import com.gaelalburo.hackingmx25.autoahorroapi.model.filter.QPeriod;
import com.gaelalburo.hackingmx25.autoahorroapi.model.filter.TransactionPeriodInput;
import com.gaelalburo.hackingmx25.autoahorroapi.model.filter.TransactionPeriodResult;
import com.gaelalburo.hackingmx25.autoahorroapi.model.parse.EnrichedExpense;
import com.gaelalburo.hackingmx25.autoahorroapi.model.validator.InvalidTransaction;
import com.gaelalburo.hackingmx25.autoahorroapi.model.validator.TransactionValidationResult;
import com.gaelalburo.hackingmx25.autoahorroapi.service.validator.TransactionValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Implementación del servicio de validación de restricciones temporales para transacciones.
 */
@Slf4j
@Service
public class TransactionPeriodValidatorImpl implements TransactionPeriodValidatorService {

    private TransactionValidatorService transactionValidatorService;

    @Autowired
    public TransactionPeriodValidatorImpl(TransactionValidatorService transactionValidatorService) {
        this.transactionValidatorService = transactionValidatorService;
    }

    /**
     * Valida una lista de transacciones según reglas básicas y periodos Q y P.
     *
     * Primero descarta las transacciones con valores negativos en amount, ceiling o remanent.
     * Luego aplica validaciones estándar como duplicados y límite mensual.
     * Después verifica si el remanent cumple con el valor fijo durante los periodos Q.
     * Finalmente, si una transacción cae en un periodo P, le suma el valor extra al remanent.
     *
     * @param input Objeto que contiene los periodos Q, P, K, el sueldo y las transacciones
     * @return Resultado con listas de transacciones válidas e inválidas.
     */
    @Override
    public TransactionPeriodResult validateByPeriods(TransactionPeriodInput input) {
        List<EnrichedExpense> preValid = new ArrayList<>();
        List<InvalidTransaction> invalid = new ArrayList<>();

        for (EnrichedExpense e : input.getTransactions()) {
            if (e.getAmount() < 0) {
                invalid.add(new InvalidTransaction(
                        e.getDate(), e.getAmount(), e.getCeiling(), e.getRemanent(),
                        "Negative amounts are not allowed"));
                continue;
            }
            if (e.getCeiling() < 0) {
                invalid.add(new InvalidTransaction(
                        e.getDate(), e.getAmount(), e.getCeiling(), e.getRemanent(),
                        "Negative ceiling is not allowed"));
                continue;
            }
            if (e.getRemanent() < 0) {
                invalid.add(new InvalidTransaction(
                        e.getDate(), e.getAmount(), e.getCeiling(), e.getRemanent(),
                        "Negative remanent is not allowed"));
                continue;
            }
            preValid.add(e);
        }

        TransactionValidationResult baseValidation = transactionValidatorService.validatedTransactions(
                input.getWage(), preValid
        );

        List<EnrichedExpense> valid = new ArrayList<>();
        invalid.addAll(baseValidation.getInvalid());

        for (EnrichedExpense e : baseValidation.getValid()) {
            // 3. Validación Q: remanent fijo
            Optional<QPeriod> qMatch = input.getQ().stream()
                    .filter(p -> isWithin(e.getDate(), p.getStart(), p.getEnd()))
                    .findFirst();
            if (qMatch.isPresent()) {
                double fixed = qMatch.get().getFixed();
                if (e.getRemanent() != fixed) {
                    invalid.add(new InvalidTransaction(e.getDate(), e.getAmount(), e.getCeiling(), e.getRemanent(),
                            "Remanent must be " + fixed + " during Q period"));
                    continue;
                }
            }

            // 4. Modificación P: suma extra en remanente
            Optional<PPeriod> pMatch = input.getP().stream()
                    .filter(p -> isWithin(e.getDate(), p.getStart(), p.getEnd()))
                    .findFirst();
            if (pMatch.isPresent()) {
                double extra = pMatch.get().getExtra();
                e.setRemanent(e.getRemanent() + extra);
            }

            // Es válida la transacción
            valid.add(e);
        }
        return new TransactionPeriodResult(valid, invalid);
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
}
