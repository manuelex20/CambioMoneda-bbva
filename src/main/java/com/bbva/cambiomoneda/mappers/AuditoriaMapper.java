package com.bbva.cambiomoneda.mappers;

import com.bbva.cambiomoneda.controllers.dto.AuditoriaDTO;
import com.bbva.cambiomoneda.repository.entity.Auditoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditoriaMapper {

    @Mapping(source = "fecha_hora", target = "fecha_hora")
    @Mapping(source = "nombre_usuario", target = "nombre_usuario")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "detalle", target = "detalle")
    Auditoria toEntity(AuditoriaDTO auditoriaDTO);

    @Mapping(source = "fecha_hora", target = "fecha_hora")
    @Mapping(source = "nombre_usuario", target = "nombre_usuario")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "detalle", target = "detalle")
    AuditoriaDTO toDTO(Auditoria auditoria);
}
