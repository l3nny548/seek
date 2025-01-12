package com.seek.cliente.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class ErrorDto {
    private String code;
    private String message;
    private String description;
}
