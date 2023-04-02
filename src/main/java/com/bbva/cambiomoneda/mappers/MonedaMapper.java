package com.bbva.cambiomoneda.mappers;

import com.bbva.cambiomoneda.controllers.dto.MonedaDTO;
import com.bbva.cambiomoneda.repository.entity.Moneda;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MonedaMapper {

    Moneda toEntity(MonedaDTO dto);

    MonedaDTO toDTO(Moneda m);

    default List<Moneda> toEntity(List<MonedaDTO> listaDTO) {
        return listaDTO.stream().map(dto -> toEntity(dto)).toList();
    }

    default List<MonedaDTO> toDTO(List<Moneda> listaMoneda) {
        return listaMoneda.stream().map(m -> toDTO(m)).toList();
    }
}
