package com.bar.gestioneBar.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.bar.gestioneBar.dto.DettaglioOrdineDto;
import com.bar.gestioneBar.entity.DettaglioOrdine;

@Mapper (componentModel = "spring")
public interface DettaglioOdineMapper {
    DettaglioOrdineDto toDto(DettaglioOrdine dettaglioOrdine);
    DettaglioOrdine toEntity(DettaglioOrdineDto dettaglioOrdineDto);
    List<DettaglioOrdineDto> toDtoList(List<DettaglioOrdine> dettaglioOrdineList);
    List<DettaglioOrdine> toEntityList(List<DettaglioOrdineDto> dettaglioOrdineDtoList);
}
