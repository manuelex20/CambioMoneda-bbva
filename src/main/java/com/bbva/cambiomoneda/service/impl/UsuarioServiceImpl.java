package com.bbva.cambiomoneda.service.impl;

import com.bbva.cambiomoneda.controllers.dto.UsuarioDTO;
import com.bbva.cambiomoneda.mappers.UsuarioMapper;
import com.bbva.cambiomoneda.repository.UsuarioRepository;
import com.bbva.cambiomoneda.repository.entity.Usuario;
import com.bbva.cambiomoneda.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public UsuarioDTO guardar(UsuarioDTO usuario) {
        Usuario entidadUsuario = usuarioRepository.save(usuarioMapper.toEntity(usuario));
        return usuarioMapper.toDTO(entidadUsuario);
    }

    @Override
    public UsuarioDTO findByCorreo(String correo) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);
        if (usuario.isPresent()) {
            return usuarioMapper.toDTO(usuario.get());
        } else
            return null;
    }

    @Override
    public UsuarioDTO findByDNI(String dni) {
        Optional<Usuario> usuario = usuarioRepository.findBydni(dni);
        if (usuario.isPresent()) {
            return usuarioMapper.toDTO(usuario.get());
        } else
            return null;
    }
}
