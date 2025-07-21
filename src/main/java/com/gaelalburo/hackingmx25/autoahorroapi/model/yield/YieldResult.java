package com.gaelalburo.hackingmx25.autoahorroapi.model.yield;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Representa el resultado del cálculo de rendimientos.
 * Contiene métricas financieras agregadas y resumen por periodos K.
 */
@Data
@AllArgsConstructor
@Schema(description = "Resultado del cálculo de rendimientos financieros")
public class YieldResult {
    @Schema(description = "Suma total de los gastos originales (amount)", example = "1725")
    private double transactionsTotalAmount;

    @Schema(description = "Suma total de los techos (ceiling)", example = "175")
    private double transactionsTotalCeiling;

    @Schema(description = "Suma total de los remanentes invertidos", example = "145")
    private double investedAmount;

    @Schema(description = "Ganancia total ajustada por inflación", example = "3026.29")
    private double profits;

    @Schema(description = "Lista de cantidades invertidas por cada periodo tipo K")
    private List<KPeriodReturn> savingByDates;
}
