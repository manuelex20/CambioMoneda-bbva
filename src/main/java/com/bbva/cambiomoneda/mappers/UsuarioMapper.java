package com.bbva.cambiomoneda.mappers;

import com.bbva.cambiomoneda.controllers.dto.UsuarioDTO;
import com.bbva.cambiomoneda.repository.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "rol.id", source = "rol.id")
    Usuario toEntity(UsuarioDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "rol.id", source = "rol.id")
    UsuarioDTO toDTO(Usuario u);

    default List<Usuario> toEntity(List<UsuarioDTO> listaUsuariosDTO) {
        return listaUsuariosDTO.stream().map(dto -> toEntity(dto)).toList();
    }

    default List<UsuarioDTO> toDTO(List<Usuario> listaUsuarios) {
        return listaUsuarios.stream().map(u -> toDTO(u)).toList();
    }
}
