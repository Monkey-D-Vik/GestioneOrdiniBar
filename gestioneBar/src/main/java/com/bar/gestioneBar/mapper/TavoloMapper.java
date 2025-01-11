package com.bar.gestioneBar.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.bar.gestioneBar.dto.TavoloDto;
import com.bar.gestioneBar.entity.Tavolo;
// spring gestisce il mapper automaticamente
@Mapper (componentModel = "spring")
public interface TavoloMapper {
    // creo i metodi di conversione e le liste
    TavoloDto toDto(Tavolo tavolo);
    Tavolo toEntity(TavoloDto tavoloDto);
    List<TavoloDto> toDtoList(List<Tavolo> tavoloList);
    List<Tavolo> toEntityList(List<TavoloDto> tavoloDtoList);
}
