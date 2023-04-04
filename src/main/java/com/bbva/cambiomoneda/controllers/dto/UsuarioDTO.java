package com.bbva.cambiomoneda.controllers.dto;

import lombok.*;

@Data
@Builder
public class UsuarioDTO {
    private int id;
    private String nombre;
    private String correo;
    private String clave;
    private RolDTO rol;
    private String dni;
}
