package com.gaelalburo.hackingmx25.autoahorroapi.model.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Representa un periodo tipo P donde se suma un extra al remanente de cada transacción.
 */
@Data
public class PPeriod {
    @Schema(description = "Cantidad extra que se añade al remanente por transacción", example = "25")
    //@Min(value = 0, message = "Extra must be non-negative")
    private Double extra;

    @Schema(description = "Fecha y hora de inicio del periodo P", example = "2023-10-01 08:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start;

    @Schema(description = "Fecha y hora de fin del periodo P", example = "2023-12-31 19:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end;
}
