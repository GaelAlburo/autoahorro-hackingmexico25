package com.gaelalburo.hackingmx25.autoahorroapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private Integer status;
    private String error;
    private String message;
    private String path;
    private LocalDateTime timestamp;
}
