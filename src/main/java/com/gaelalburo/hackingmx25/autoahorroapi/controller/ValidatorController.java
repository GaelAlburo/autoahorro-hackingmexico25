package com.gaelalburo.hackingmx25.autoahorroapi.controller;

import com.gaelalburo.hackingmx25.autoahorroapi.model.validator.TransactionValidationInput;
import com.gaelalburo.hackingmx25.autoahorroapi.model.validator.TransactionValidationResult;
import com.gaelalburo.hackingmx25.autoahorroapi.service.validator.TransactionValidatorService;
import io.swagger.v3.oas.annotations.Operation;
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
 * 2. Validador de Transacciones
 * Valida una lista de transacciones en función del sueldo y el monto máximo a invertir.
 * Endpoint: /blackrock/challenge/v1/transactions:validator
 */
@Tag(name = "Validador de Transacciones", description = "Funcionalidad para validar transacciones según sueldo y monto máximo a invertir")
@Slf4j
@RestController
@RequestMapping("/blackrock/challenge/v1/")
@Validated
public class ValidatorController {

    private final TransactionValidatorService transactionValidatorService;

    @Autowired
    public ValidatorController(TransactionValidatorService transactionValidatorService) {
        this.transactionValidatorService = transactionValidatorService;
    }

    /**
     * Valida una lista de transacciones contra 3 reglas:
     *      - No deben estar duplicadas (fecha + monto)
     *      - La suma del remanente mensual no debe superar el 10% del sueldo
     *      - Deben ser consistentes en cuanto a amount, ceiling y remanent
     * @param input Objeto con sueldo y lista de transacciones a validar
     * @return Resultado que separa transacciones válidas e inválidas (estas con mensaje explicativo)
     */
    @Operation(
            summary = "Valida una lista de transacciones",
            description = """
            Esta operación recibe un sueldo mensual y una lista de transacciones enriquecidas,
            y devuelve dos listas: transacciones válidas e inválidas (con mensaje de error).
            
            Las validaciones son:
            - No duplicadas (mismo date + amount)
            - Límite mensual de inversión: remanent mensual ≤ 10% del sueldo
            - Consistencia interna de los valores: amount < ceiling, ceiling debe ser múltiplo de 100 arriba 
              de amount, y remanent debe ser exactamente ceiling - amount
            """
    )
    @PostMapping("/transactions:validator")
    public ResponseEntity<TransactionValidationResult> validate(@Valid @RequestBody TransactionValidationInput input){
        TransactionValidationResult result =
                transactionValidatorService.validatedTransactions(input.getWage(), input.getTransactions());
        return ResponseEntity.ok(result);
    }
}
