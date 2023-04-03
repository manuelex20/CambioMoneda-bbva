package com.bbva.cambiomoneda.service.impl;

import com.bbva.cambiomoneda.controllers.dto.AuditoriaDTO;
import com.bbva.cambiomoneda.mappers.AuditoriaMapper;
import com.bbva.cambiomoneda.repository.AuditoriaRepository;
import com.bbva.cambiomoneda.repository.entity.Auditoria;
import com.bbva.cambiomoneda.service.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaServiceImpl implements AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @Autowired
    private AuditoriaMapper auditoriaMapper;

    @Override
    public AuditoriaDTO guardar(AuditoriaDTO auditoriaDTO) {
        Auditoria auditoria = auditoriaRepository.save(auditoriaMapper.toEntity(auditoriaDTO));
        return null;
    }
}
