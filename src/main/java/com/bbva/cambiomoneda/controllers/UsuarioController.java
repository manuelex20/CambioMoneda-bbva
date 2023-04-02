package com.bbva.cambiomoneda.controllers;

import com.bbva.cambiomoneda.controllers.dto.UsuarioDTO;
import com.bbva.cambiomoneda.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UsuarioDTO>> listar() {
        try {
            return new ResponseEntity<>(usuarioService.listar(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/registrar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDTO> registrar(@RequestBody UsuarioDTO usuario, BindingResult binding) {
        if (binding.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        } else {
            try {
                UsuarioDTO usuarioDTOguardado = usuarioService.guardar(usuario);
                return new ResponseEntity<>(usuarioDTOguardado, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
            }

        }
    }
}
