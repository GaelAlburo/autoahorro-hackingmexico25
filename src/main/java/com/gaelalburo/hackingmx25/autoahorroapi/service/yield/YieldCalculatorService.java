package com.gaelalburo.hackingmx25.autoahorroapi.service.yield;

import com.gaelalburo.hackingmx25.autoahorroapi.model.yield.YieldInput;
import com.gaelalburo.hackingmx25.autoahorroapi.model.yield.YieldResult;

/**
 * Servicio para calcular el rendimiento financiero ajustado por inflación,
 * con base en un conjunto de transacciones y periodos definidos.
 */
public interface YieldCalculatorService {
    /**
     * Calcula los rendimientos de una estrategia de inversión con base en las transacciones,
     * edad, inflación, salario y periodos tipo K.
     *
     * @param yield Datos de entrada para el cálculo
     * @return Resultado del cálculo de rendimientos
     */
    YieldResult calculateYield(YieldInput yield);
}
