package com.bbva.cambiomoneda.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaDTO {
    private int id;
    private LocalDateTime fecha_hora;
    private String nombre_usuario;
    private String detalle;
}
