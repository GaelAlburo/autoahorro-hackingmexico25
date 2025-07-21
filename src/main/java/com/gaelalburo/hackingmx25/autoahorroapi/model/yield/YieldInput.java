package com.gaelalburo.hackingmx25.autoahorroapi.model.yield;

import com.gaelalburo.hackingmx25.autoahorroapi.model.filter.KPeriod;
import com.gaelalburo.hackingmx25.autoahorroapi.model.filter.PPeriod;
import com.gaelalburo.hackingmx25.autoahorroapi.model.filter.QPeriod;
import com.gaelalburo.hackingmx25.autoahorroapi.model.parse.EnrichedExpense;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * Representa la entrada para el cálculo de rendimientos financieros.
 * Contiene datos del usuario, tasas, periodos especiales y transacciones.
 */
@Data
@Schema(description = "Entrada para el cálculo de rendimientos financieros")
public class YieldInput {
    @Schema(description = "Edad actual del usuario", example = "40", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La edad es obligatoria")
    @Min(value = 1, message = "La edad debe ser mayor que 0")
    private Integer age;

    @Schema(description = "Salario mensual del usuario en pesos mexicanos", example = "50000", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El salario es obligatorio")
    @Min(value = 1, message = "El salario debe ser mayor que 0")
    private Integer wage;

    @Schema(description = "Tasa de inflación anual en porcentaje", example = "4.83", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La inflación es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La inflación debe ser mayor que 0")
    private Double inflation;

    @Schema(description = "Lista de periodos tipo Q", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<QPeriod> q;

    @Schema(description = "Lista de periodos tipo P", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<PPeriod> p;

    @Schema(description = "Lista de periodos tipo K", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Debe existir al menos un periodo tipo K")
    private List<KPeriod> k;

    @Schema(description = "Lista de transacciones enriquecidas a evaluar", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Debe proporcionar al menos una transacción")
    private List<EnrichedExpense> transactions;
}
