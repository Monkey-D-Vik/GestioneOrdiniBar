package com.bar.gestioneBar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bar.gestioneBar.dto.TavoloDto;
import com.bar.gestioneBar.entity.Tavolo;
import com.bar.gestioneBar.mapper.TavoloMapper;
import com.bar.gestioneBar.repository.TavoloRepository;

@Service
public class TavoloService {
    @Autowired
    private TavoloRepository tavoloRepository;
    @Autowired
    private TavoloMapper tavoloMapper;

//aggiungere tavolo
@Transactional
public TavoloDto aggiungiTavolo(TavoloDto tavoloDto) {
    Tavolo tavolo = tavoloMapper.toEntity(tavoloDto);
    tavolo = tavoloRepository.save(tavolo);
    return tavoloMapper.toDto(tavolo);
}
}


