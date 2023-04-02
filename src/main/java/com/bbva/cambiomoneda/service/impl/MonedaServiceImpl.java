package com.bbva.cambiomoneda.service.impl;

import com.bbva.cambiomoneda.controllers.dto.MonedaDTO;
import com.bbva.cambiomoneda.mappers.MonedaMapper;
import com.bbva.cambiomoneda.repository.MonedaRepository;
import com.bbva.cambiomoneda.repository.entity.Moneda;
import com.bbva.cambiomoneda.service.MonedaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonedaServiceImpl implements MonedaService {

    @Autowired
    private MonedaRepository monedaRepository;

    @Autowired
    private MonedaMapper monedaMapper;

    @Override
    public List<MonedaDTO> listar() {
        return monedaMapper.toDTO(monedaRepository.findAll());
    }

    @Override
    public MonedaDTO obtenerPorNombre(String monedalocal) {
        Moneda moneda = monedaRepository.buscarPorNombre(monedalocal);
        if (moneda == null) {
            return null;
        }
        return monedaMapper.toDTO(moneda);
    }

    @Override
    public MonedaDTO guardar(MonedaDTO monedaDTO) {
        return monedaMapper.toDTO(monedaRepository.save(monedaMapper.toEntity(monedaDTO)));
    }
}
