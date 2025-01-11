package com.bar.gestioneBar.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.bar.gestioneBar.dto.OrdiniDto;
import com.bar.gestioneBar.entity.Ordini;

@Mapper(componentModel = "spring")
public interface OrdineMapper {
    OrdiniDto toDto(Ordini ordine);
    Ordini toEntity(OrdiniDto ordineDto);
    List<OrdiniDto> toDtoList(List<Ordini> ordiniList);
    List<Ordini> toEntityList(List<OrdiniDto> ordiniDtoList);
}
