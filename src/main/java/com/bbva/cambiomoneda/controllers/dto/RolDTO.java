package com.bbva.cambiomoneda.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RolDTO {
    private int id;
    private int nombre;
}
