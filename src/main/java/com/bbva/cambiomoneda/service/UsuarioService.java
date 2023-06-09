package com.bbva.cambiomoneda.service;

import com.bbva.cambiomoneda.controllers.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> listar();

    UsuarioDTO guardar(UsuarioDTO usuario);

    UsuarioDTO findByCorreo(String correo);

    UsuarioDTO findByDNI(String dni);
}
