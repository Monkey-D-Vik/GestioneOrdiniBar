package com.bar.gestioneBar.controller;

import com.bar.gestioneBar.dto.OrdiniDto;
import com.bar.gestioneBar.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/ordini")
public class OrdineController {

    @Autowired
    private OrdineService ordineService;

    // Crea un nuovo ordine
    @PostMapping
    public OrdiniDto creaOrdine(@RequestBody OrdiniDto ordiniDto) {
        return ordineService.creaOrdine(ordiniDto);
    }

    // Aggiorna un ordine
    @PutMapping("/{id}")
    public OrdiniDto aggiornaOrdine(@PathVariable Long id, @RequestBody OrdiniDto ordiniDto) {
        return ordineService.aggiornaOrdine(id, ordiniDto);
    }

    // Elimina un ordine
    @DeleteMapping("/{id}")
    public void eliminaOrdine(@PathVariable Long id) {
        ordineService.eliminaOrdine(id);
    }

    // Trova un ordine per ID
    @GetMapping("/{id}")
    public OrdiniDto trovaOrdinePerId(@PathVariable Long id) {
        return ordineService.trovaOrdinePerId(id);
    }

    // Trova tutti gli ordini aperti
    @GetMapping("/aperti")
    public List<OrdiniDto> trovaOrdiniAperti() {
        return ordineService.trovaOrdiniAperti();
    }

    // Trova tutti gli ordini chiusi
    @GetMapping("/chiusi")
    public List<OrdiniDto> trovaOrdiniChiusi() {
        return ordineService.trovaOrdiniChiusi();
    }

    // Chiudi un ordine (segna come pagato)
    @PutMapping("/chiudi/{id}")
    public OrdiniDto chiudiOrdine(@PathVariable Long id) {
        return ordineService.chiudiOrdine(id);
    }

    // Calcola il totale di un ordine
    @GetMapping("/{id}/totale")
    public BigDecimal calcolaTotale(@PathVariable Long id) {
        return ordineService.calcolaTotale(id);
    }
}
