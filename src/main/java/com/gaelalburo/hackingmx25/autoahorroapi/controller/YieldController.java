package com.gaelalburo.hackingmx25.autoahorroapi.controller;

import com.gaelalburo.hackingmx25.autoahorroapi.model.yield.YieldInput;
import com.gaelalburo.hackingmx25.autoahorroapi.model.yield.YieldResult;
import com.gaelalburo.hackingmx25.autoahorroapi.service.yield.YieldCalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 4. Cálculo de Rendimiento
 * Calcula el rendimiento de las inversiones a partir de las transacciones, sueldo, edad, inflación,
 * periodos y tasas de rendimiento. Devuelve métricas financieras y un historial mensual de estrategia.
 * Se ofrecen dos rutas:
 * - /blackrock/challenge/v1/transactions:ppr - para Plan Personal de Retiro
 * - /blackrock/challenge/v1/transactions:ishares - para Fondo iShares IVV
 */
@Slf4j
@RestController
@RequestMapping("/blackrock/challenge/v1")
@Tag(name = "Cálculo de Rendimientos", description = "Endpoints para calcular rendimientos ajustados a inflación.")
public class YieldController {

    private final YieldCalculatorService pprService;
    private final YieldCalculatorService isharesService;

    @Autowired
    public YieldController(
            @Qualifier("pprYieldCalculator") YieldCalculatorService pprService,
            @Qualifier("isharesYieldCalculator") YieldCalculatorService isharesService) {
        this.pprService = pprService;
        this.isharesService = isharesService;
    }

    /**
     * Calcula el rendimiento ajustado a inflación para el Plan Personal de Retiro (PPR).
     * Tasa anual: 7.11%. Se aplica un incentivo fiscal del 10% del ingreso anual.
     *
     * @param input Entrada con edad, salario, inflación, transacciones y periodos K
     * @return Métricas del rendimiento proyectado
     */
    @Operation(summary = "Calcular rendimiento PPR", description = "Devuelve el rendimiento proyectado ajustado a inflación para el Plan Personal de Retiro.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Cálculo exitoso",
                    content = @Content(schema = @Schema(implementation = YieldResult.class))),
            @ApiResponse(responseCode = "400",
                    description = "Entrada inválida",
                    content = @Content)
    })
    @PostMapping("/transactions:ppr")
    public ResponseEntity<YieldResult> calculatePpr(@Valid @RequestBody YieldInput input) {
        log.info("Calculando rendimiento PPR para edad={}, sueldo={}, inflación={}",
                input.getAge(), input.getWage(), input.getInflation());
        return ResponseEntity.ok(pprService.calculateYield(input));
    }

    /**
     * Calcula el rendimiento ajustado a inflación para el fondo iShares IVV.
     * Tasa anual: 14.49%. No tiene restricciones ni incentivos.
     *
     * @param input Entrada con edad, salario, inflación, transacciones y periodos K
     * @return Métricas del rendimiento proyectado
     */
    @Operation(summary = "Calcular rendimiento iShares", description = "Devuelve el rendimiento proyectado ajustado a inflación para el fondo iShares IVV.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cálculo exitoso", content = @Content(schema = @Schema(implementation = YieldResult.class))),
            @ApiResponse(responseCode = "400", description = "Entrada inválida", content = @Content)
    })
    @PostMapping("/transactions:ishares")
    public ResponseEntity<YieldResult> calculateIshares(@Valid @RequestBody YieldInput input) {
        log.info("Calculando rendimiento iShares IVV para edad={}, sueldo={}, inflación={}",
                input.getAge(), input.getWage(), input.getInflation());
        return ResponseEntity.ok(isharesService.calculateYield(input));
    }
}
