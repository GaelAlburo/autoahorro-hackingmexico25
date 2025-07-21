package com.gaelalburo.hackingmx25.autoahorroapi.controller;

import com.gaelalburo.hackingmx25.autoahorroapi.model.parse.EnrichedExpense;
import com.gaelalburo.hackingmx25.autoahorroapi.model.parse.ExpenseInput;
import com.gaelalburo.hackingmx25.autoahorroapi.service.parse.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 1. Constructor de Transacciones
 * Recibe una lista de gastos y devuelve una lista de transacciones enriquecidas con los campos ceiling y remanent.
 * Endpoints: /blackrock/challenge/v1/transactions:parse
 */
@Slf4j
@RestController
@RequestMapping("blackrock/challenge/v1/")
@Validated
public class ParseController {

    private final ExpenseService expenseService;

    @Autowired
    public ParseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Operation(
            summary = "Constructor de Transacciones",
            description = "Toma una lista de gastos y devuelve una lista de transacciones enriquecidas con los campos ceiling y remanent",
            responses = {
                @ApiResponse(responseCode = "200", description = "Transacciones enriquecidas exitosamente"),
                @ApiResponse(responseCode = "400", description = "Errores de validación o formato invalido de fecha"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/transactions:parse")
    public ResponseEntity<List<EnrichedExpense>> parse(@Valid @RequestBody List<ExpenseInput> expenses){
        log.info("parse() called with {} expenses", expenses.size());
        List<EnrichedExpense> enriched = expenseService.enrichExpenses(expenses);
        return ResponseEntity.ok(enriched);
    }
}
