package com.bbva.cambiomoneda.controllers.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MonedaDTO {
    private int id;
    private String nombre;
    private double compra;
    private double venta;
}
