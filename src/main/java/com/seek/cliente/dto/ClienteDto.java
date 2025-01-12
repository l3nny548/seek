package com.seek.cliente.dto;

import lombok.Data;
import lombok.Builder;

import java.time.LocalDate;

import com.seek.cliente.interfaces.PastDate;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Builder
@Data
public class ClienteDto {
    @Nullable
    private Long id;

    @NotBlank(message = "Nombre es requerido")
    private String nombre;

    @NotBlank(message = "Apellido es requerido")
    private String apellido;

    @NotNull(message = "Edad es requerido")
    @Min(value = 10, message = "La edad minima es 10 años")
    @Max(value = 99, message = "La edad máxima es 99 años")
    private Integer edad;
    
    @NotNull(message = "Fecha Nacimiento es requerido")
    @PastDate(message = "La fecha de nacimiento debe estar en el pasado")
    private LocalDate fechaNacimiento;

    @NotNull(message = "Edad es requerido")
    private double esperanzaDeVida;
}