package com.bar.gestioneBar.controller;

import com.bar.gestioneBar.dto.ProdottoDto;
import com.bar.gestioneBar.service.ProdottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prodotti")
public class ProdottoController {

    @Autowired
    private ProdottoService prodottoService;

    // Ottieni tutti i prodotti
    @GetMapping
    public List<ProdottoDto> getAllProdotti() {
        return prodottoService.getAllProdotti();
    }

    // Aggiungi un prodotto
    @PostMapping
    public ProdottoDto aggiungiProdotto(@RequestBody ProdottoDto prodottoDto) {
        return prodottoService.aggiungiProdotto(prodottoDto);
    }

    // Elimina un prodotto
    @DeleteMapping("/{id}")
    public void eliminaProdotto(@PathVariable Long id) {
        prodottoService.eliminaProdotto(id);
    }

    // Modifica un prodotto
    @PutMapping("/{id}")
    public ProdottoDto modificaProdotto(@PathVariable Long id, @RequestBody ProdottoDto prodottoDto) {
        return prodottoService.modificaProdotto(id, prodottoDto);
    }
}
