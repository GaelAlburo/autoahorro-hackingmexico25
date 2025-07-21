package com.gaelalburo.hackingmx25.autoahorroapi.model.parse;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Datos de entrada representando un gasto
 */
@Schema(description = "Un gasto de entrada con fecha y cantidad")
@Data
@NoArgsConstructor
public class ExpenseInput {
    /**
     * Fecha del gasto en formato yyyy-MM-dd HH:mm
     */
    @Schema(example = "2023-10-12 20:15")
    @NotNull(message = "Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    /**
     * Cantidad gastada
     */
    @Schema(example = "250")
    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be non-negative")
    private Double amount;
}
