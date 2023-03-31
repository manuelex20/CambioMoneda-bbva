package com.bbva.cambiomoneda.service.impl;

import com.bbva.cambiomoneda.controllers.dto.UsuarioDTO;
import com.bbva.cambiomoneda.mappers.UsuarioMapper;
import com.bbva.cambiomoneda.repository.UsuarioRepository;
import com.bbva.cambiomoneda.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public List<UsuarioDTO> listar() {
        List<UsuarioDTO> usuarioDTOList = usuarioMapper.toDTO(usuarioRepository.findAll());
        return usuarioDTOList;
    }
}
