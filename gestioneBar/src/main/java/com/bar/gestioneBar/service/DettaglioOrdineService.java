package com.bar.gestioneBar.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bar.gestioneBar.dto.DettaglioOrdineDto;
import com.bar.gestioneBar.entity.DettaglioOrdine;
import com.bar.gestioneBar.entity.Ordini;
import com.bar.gestioneBar.mapper.DettaglioOdineMapper;
import com.bar.gestioneBar.repository.DettaglioOrdineRepository;
import com.bar.gestioneBar.repository.OrdineRepository;
import com.bar.gestioneBar.repository.ProdottoRepository;
import com.bar.gestioneBar.entity.Prodotto;

@Service
public class DettaglioOrdineService {

    @Autowired
    private DettaglioOrdineRepository dettaglioOrdineRepository;

    @Autowired
    private DettaglioOdineMapper dettaglioOrdineMapper;

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private ProdottoRepository prodottoRepository;

    // Crea un nuovo dettaglio ordine
    @Transactional
    public DettaglioOrdineDto creaDettaglioOrdine(DettaglioOrdineDto dettaglioOrdineDto) {
        // Validazione input
        if (dettaglioOrdineDto.getOrdineId() == null || dettaglioOrdineDto.getOrdineId().getOrdiniId() == null) {
            throw new IllegalArgumentException("ordineId non può essere nullo");
        }
        if (dettaglioOrdineDto.getProdottoId() == null || dettaglioOrdineDto.getProdottoId().getProdottoId() == null) {
            throw new IllegalArgumentException("prodottoId non può essere nullo");
        }
        if (dettaglioOrdineDto.getQuantita() <= 0) {
            throw new IllegalArgumentException("La quantità deve essere maggiore di zero");
        }

        // Verifica che il prodotto esista e abbia un prezzo
        Prodotto prodotto = prodottoRepository.findById(dettaglioOrdineDto.getProdottoId().getProdottoId())
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
        if (prodotto.getPrezzo() == null) {
            throw new IllegalArgumentException("Il prodotto deve avere un prezzo valido");
        }

        DettaglioOrdine dettaglioOrdine = dettaglioOrdineMapper.toEntity(dettaglioOrdineDto);
        dettaglioOrdine = dettaglioOrdineRepository.save(dettaglioOrdine);

        // Aggiorna il totale dell'ordine
        aggiornaOrdine(dettaglioOrdine.getOrdineId().getOrdiniId());

        return dettaglioOrdineMapper.toDto(dettaglioOrdine);
    }

    // Metodo privato per aggiornare il totale dell'ordine
    private void aggiornaOrdine(Long ordineId) {
        BigDecimal nuovoTotale = calcolaTotale(ordineId);
        Ordini ordine = ordineRepository.findById(ordineId)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato"));
        ordine.setTotale(nuovoTotale);
        ordineRepository.save(ordine);
    }

    // Aggiorna un dettaglio ordine
    @Transactional
    public DettaglioOrdineDto aggiornaDettaglioOrdine(Long id, DettaglioOrdineDto dettaglioOrdineDto) {
        Optional<DettaglioOrdine> optionalDettaglio = dettaglioOrdineRepository.findById(id);
        if (optionalDettaglio.isPresent()) {
            DettaglioOrdine dettaglioOrdine = optionalDettaglio.get();
            dettaglioOrdine.setQuantita(dettaglioOrdineDto.getQuantita());
            dettaglioOrdine = dettaglioOrdineRepository.save(dettaglioOrdine);
            
            // Aggiorna il totale dell'ordine
            aggiornaOrdine(dettaglioOrdine.getOrdineId().getOrdiniId());
            
            return dettaglioOrdineMapper.toDto(dettaglioOrdine);
        } else {
            throw new RuntimeException("Dettaglio ordine non trovato");
        }
    }

    // Elimina un dettaglio ordine
    @Transactional
    public void eliminaDettaglioOrdine(Long id) {
        DettaglioOrdine dettaglio = dettaglioOrdineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dettaglio ordine non trovato"));
        Long ordineId = dettaglio.getOrdineId().getOrdiniId();
        
        dettaglioOrdineRepository.deleteById(id);
        
        // Aggiorna il totale dell'ordine dopo l'eliminazione
        aggiornaOrdine(ordineId);
    }

    // Recupera un dettaglio ordine per ID
    public DettaglioOrdineDto trovaDettaglioOrdinePerId(Long id) {
        DettaglioOrdine dettaglioOrdine = dettaglioOrdineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dettaglio ordine non trovato"));
        return dettaglioOrdineMapper.toDto(dettaglioOrdine);
    }

    // Recupera tutti i dettagli di un ordine
    public List<DettaglioOrdineDto> trovaDettagliPerOrdineId(Long ordineId) {
        List<DettaglioOrdine> dettagli = dettaglioOrdineRepository.findByOrdineId_OrdiniId(ordineId);
        return dettagli.stream()
                      .map(dettaglioOrdineMapper::toDto)
                      .collect(Collectors.toList());
    }

    // Calcola il totale di un ordine basato sui dettagli
    public BigDecimal calcolaTotale(Long ordineId) {
        List<DettaglioOrdine> dettagli = dettaglioOrdineRepository.findByOrdineId_OrdiniId(ordineId);
        return dettagli.stream()
                .map(dettaglio -> {
                    Prodotto prodotto = prodottoRepository.findById(dettaglio.getProdottoId().getProdottoId())
                            .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
                    return prodotto.getPrezzo().multiply(BigDecimal.valueOf(dettaglio.getQuantita()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}