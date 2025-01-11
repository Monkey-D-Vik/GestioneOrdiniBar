package com.bar.gestioneBar.controller;

import com.bar.gestioneBar.dto.DettaglioOrdineDto;
import com.bar.gestioneBar.service.DettaglioOrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dettagliOrdine")
public class DettaglioOrdineController {

    @Autowired
    private DettaglioOrdineService dettaglioOrdineService;

    // Crea un nuovo dettaglio ordine
    @PostMapping
    public DettaglioOrdineDto creaDettaglioOrdine(@RequestBody DettaglioOrdineDto dettaglioOrdineDto) {
        return dettaglioOrdineService.creaDettaglioOrdine(dettaglioOrdineDto);
    }

    // Aggiorna un dettaglio ordine
    @PutMapping("/{id}")
    public DettaglioOrdineDto aggiornaDettaglioOrdine(@PathVariable Long id, @RequestBody DettaglioOrdineDto dettaglioOrdineDto) {
        return dettaglioOrdineService.aggiornaDettaglioOrdine(id, dettaglioOrdineDto);
    }

    // Elimina un dettaglio ordine
    @DeleteMapping("/{id}")
    public void eliminaDettaglioOrdine(@PathVariable Long id) {
        dettaglioOrdineService.eliminaDettaglioOrdine(id);
    }

    // Trova un dettaglio ordine per ID
    @GetMapping("/{id}")
    public DettaglioOrdineDto trovaDettaglioOrdinePerId(@PathVariable Long id) {
        return dettaglioOrdineService.trovaDettaglioOrdinePerId(id);
    }

    // Trova tutti i dettagli per un ordine
    @GetMapping("/ordine/{ordineId}")
    public List<DettaglioOrdineDto> trovaDettagliPerOrdineId(@PathVariable Long ordineId) {
        return dettaglioOrdineService.trovaDettagliPerOrdineId(ordineId);
    }
}
