package com.gaelalburo.hackingmx25.autoahorroapi.controller;

import com.gaelalburo.hackingmx25.autoahorroapi.model.filter.TransactionPeriodInput;
import com.gaelalburo.hackingmx25.autoahorroapi.model.filter.TransactionPeriodResult;
import com.gaelalburo.hackingmx25.autoahorroapi.service.filter.TransactionPeriodValidatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 3. Validador de Restricciones Temporales
 * Valida transacciones según los periodos temporales Q, P y K.
 * Esta validación incluye reglas adicionales como remanentes fijos, incrementos extra y evaluaciones por periodo.
 * Endpoint: /blackrock/challenge/v1/transactions:filter
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/blackrock/challenge/v1")
@Tag(name = "Validador de Restricciones Temporales", description = "Valida transacciones según periodos definidos Q, P y K.")
public class FilterController {
    private final TransactionPeriodValidatorService validatorService;

    @Autowired
    public FilterController(TransactionPeriodValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    /**
     * Valida transacciones en función de periodos temporales definidos (Q, P y K).
     *
     * @param input Entrada que incluye las listas de periodos Q, P, K, el sueldo y las transacciones.
     * @return Resultado con listas de transacciones válidas e inválidas.
     */
    @Operation(
            summary = "Validar transacciones por periodos Q, P y K",
            description = "Evalúa una lista de transacciones aplicando reglas adicionales de inversión según periodos temporales definidos. "
                    + "Devuelve transacciones válidas e inválidas con mensajes explicativos en caso de error.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Validación exitosa",
                            content = @Content(schema = @Schema(implementation = TransactionPeriodResult.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Solicitud malformada o con datos inválidos",
                            content = @Content(schema = @Schema())
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No autorizado - token faltante o inválido",
                            content = @Content(schema = @Schema())
                    )
            }
    )
    @PostMapping("/transactions:filter")
    public ResponseEntity<TransactionPeriodResult> validateByPeriods(
            @Valid @RequestBody TransactionPeriodInput input) {
        log.info("Validating transactions by periods for {} transactions", input.getTransactions().size());
        TransactionPeriodResult result = validatorService.validateByPeriods(input);
        return ResponseEntity.ok(result);
    }
}
