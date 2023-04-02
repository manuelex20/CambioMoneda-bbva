package com.bbva.cambiomoneda.controllers.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SolicitudCambioDTO {
    private String monedaLocal;
    private String monedaDestino;
    private boolean isCompra;
    private double monto;
}
