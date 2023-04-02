package com.bbva.cambiomoneda.controllers.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespuestaCambioDTO {
    private String monedas;
    private String tipoCambio;
    private double monto;
}
