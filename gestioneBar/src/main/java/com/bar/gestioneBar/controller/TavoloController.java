package com.bar.gestioneBar.controller;

import com.bar.gestioneBar.dto.TavoloDto;
import com.bar.gestioneBar.service.TavoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tavoli")
public class TavoloController {

    @Autowired
    private TavoloService tavoloService;

    // Aggiungi un tavolo
    @PostMapping
    public TavoloDto aggiungiTavolo(@RequestBody TavoloDto tavoloDto) {
        return tavoloService.aggiungiTavolo(tavoloDto);
    }
}
