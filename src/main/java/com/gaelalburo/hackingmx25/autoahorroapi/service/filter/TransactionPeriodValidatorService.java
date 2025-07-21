package com.gaelalburo.hackingmx25.autoahorroapi.service.filter;

import com.gaelalburo.hackingmx25.autoahorroapi.model.filter.TransactionPeriodInput;
import com.gaelalburo.hackingmx25.autoahorroapi.model.filter.TransactionPeriodResult;

/**
 * Servicio que contiene la lógica de validación de transacciones
 * en función de periodos especiales definidos como Q, P y K.
 */
public interface TransactionPeriodValidatorService {
    /**
     * Valida una lista de transacciones de acuerdo a las reglas de los periodos Q, P y K.
     * Aplica correcciones a los remanentes en caso de periodos P, valida restricciones fijas en Q,
     * y mantiene la lógica de resumen por K (sin invalidar).
     *
     * @param input Objeto que contiene los periodos Q, P, K, el sueldo y las transacciones
     * @return Objeto con listas separadas de transacciones válidas e inválidas
     */
    TransactionPeriodResult validateByPeriods(TransactionPeriodInput input);
}
