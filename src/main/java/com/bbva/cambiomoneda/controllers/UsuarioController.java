package com.bbva.cambiomoneda.controllers;

import com.bbva.cambiomoneda.controllers.dto.AuthenticationResponseDTO;
import com.bbva.cambiomoneda.controllers.dto.LoginDTO;
import com.bbva.cambiomoneda.controllers.dto.UsuarioDTO;
import com.bbva.cambiomoneda.mappers.UsuarioMapper;
import com.bbva.cambiomoneda.service.JwtService;
import com.bbva.cambiomoneda.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UsuarioDTO>> listar() {
        try {
            return new ResponseEntity<>(usuarioService.listar(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/registrar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseDTO> registrar(@RequestBody UsuarioDTO usuario, BindingResult binding) {
        if (binding.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        } else {
            try {
                usuario.setClave(passwordEncoder.encode(usuario.getClave()));

                UsuarioDTO usuarioDTOguardado = usuarioService.guardar(usuario);

                String jwtToken = jwtService.generateToken(usuarioMapper.toEntity(usuarioDTOguardado));
                AuthenticationResponseDTO response = AuthenticationResponseDTO.builder().token(jwtToken).build();
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
            }

        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody LoginDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getCorreo(), request.getClave()
        ));
        var user = usuarioService.findByCorreo(request.getCorreo());
        if (user != null) {
            String jwtToken = jwtService.generateToken(usuarioMapper.toEntity(user));
            AuthenticationResponseDTO response = AuthenticationResponseDTO
                    .builder()
                    .token(jwtToken)
                    .build();
            return new ResponseEntity<AuthenticationResponseDTO>(response, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
