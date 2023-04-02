package com.bbva.cambiomoneda.service;

import com.bbva.cambiomoneda.controllers.dto.MonedaDTO;
import com.bbva.cambiomoneda.repository.entity.Moneda;

import java.util.List;

public interface MonedaService {
    List<MonedaDTO> listar();

    MonedaDTO obtenerPorNombre(String monedalocal);

    MonedaDTO guardar(MonedaDTO monedaDTO);
}
