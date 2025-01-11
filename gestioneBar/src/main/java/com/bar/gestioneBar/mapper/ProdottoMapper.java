package com.bar.gestioneBar.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.bar.gestioneBar.dto.ProdottoDto;
import com.bar.gestioneBar.entity.Prodotto;

@Mapper (componentModel = "spring")
public interface ProdottoMapper {
    ProdottoDto toDto(Prodotto prodotto);
    Prodotto toEntity(ProdottoDto prodottoDto);
    List<ProdottoDto> toDtoList(List<Prodotto> prodottoList);
    List<Prodotto> toEntityList(List<ProdottoDto>prodottoDtoList);
}
