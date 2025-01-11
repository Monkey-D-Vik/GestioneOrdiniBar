package com.bar.gestioneBar.service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bar.gestioneBar.dto.OrdiniDto;
import com.bar.gestioneBar.entity.Ordini;
import com.bar.gestioneBar.entity.StatoOrdine;
import com.bar.gestioneBar.entity.Tavolo;
import com.bar.gestioneBar.mapper.OrdineMapper;
import com.bar.gestioneBar.mapper.TavoloMapper;
import com.bar.gestioneBar.repository.OrdineRepository;

@Service
public class OrdineService {
    @Autowired
    private OrdineRepository ordiniRepository;
    @Autowired
    private OrdineMapper ordiniMapper;
    @Autowired
    private TavoloMapper tavoloMapper;
    //CREA ORDINE 
    public OrdiniDto creaOrdine(OrdiniDto ordiniDto) {
    // Verifica che il tavolo_id non sia nullo
    if (ordiniDto.getTavoloId() == null) {
        throw new IllegalArgumentException("Il tavolo non può essere nullo.");
    }

    // Mappa TavoloDto in Tavolo
    Tavolo tavolo = tavoloMapper.toEntity(ordiniDto.getTavoloId());

    // Crea l'entità Ordini a partire dal DTO
    Ordini ordine = ordiniMapper.toEntity(ordiniDto);

    // Associa il tavolo all'ordine
    ordine.setTavoloId(tavolo);

    // Imposta valori di default per stato e totale
    ordine.setStato(StatoOrdine.IN_PREPARAZIONE); // Stato di default
    ordine.setTotale(BigDecimal.ZERO); // Totale iniziale

    // Salva l'ordine nel repository
    ordine = ordiniRepository.save(ordine);

    // Restituisci il DTO dell'ordine
    return ordiniMapper.toDto(ordine);
}

    // aggiorna
    public OrdiniDto aggiornaOrdine(Long id, OrdiniDto ordiniDto) {
        Optional<Ordini> optionalOrdine = ordiniRepository.findById(id);
        if (optionalOrdine.isPresent()) {
            Ordini ordine = optionalOrdine.get();
            ordine.setStato(ordiniDto.getStato());
            ordine.setTotale(ordiniDto.getTotale());
            ordine = ordiniRepository.save(ordine);
            return ordiniMapper.toDto(ordine);
        } else {
            throw new RuntimeException("Ordine non trovato");
        }
    }
    // elimina ordine
    public void eliminaOrdine(Long id) {
        ordiniRepository.deleteById(id);
    }
    // trova per id
    public OrdiniDto trovaOrdinePerId(Long id) {
        Ordini ordine = ordiniRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato"));
        return ordiniMapper.toDto(ordine);
    }
    // Recupera tutti gli ordini aperti
    public List<OrdiniDto> trovaOrdiniAperti() {
        List<Ordini> ordiniAperti = ordiniRepository.findByStato(StatoOrdine.IN_PREPARAZIONE);
        return ordiniAperti.stream().map(ordiniMapper::toDto).collect(Collectors.toList());
    }

    // Recupera tutti gli ordini chiusi
    // il .name restituisce come stringa l'enum
    public List<OrdiniDto> trovaOrdiniChiusi() {
        List<Ordini> ordiniChiusi = ordiniRepository.findByStato(StatoOrdine.PAGATO);
        return ordiniChiusi.stream().map(ordiniMapper::toDto).collect(Collectors.toList());
    }

    // Segna un ordine come pagato
    public OrdiniDto chiudiOrdine(Long id) {
        Ordini ordine = ordiniRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato"));
        ordine.setStato(StatoOrdine.PAGATO);
        ordine = ordiniRepository.save(ordine);
        return ordiniMapper.toDto(ordine);
    }

    // Calcola il totale di un ordine
    public BigDecimal calcolaTotale(Long id) {
        Ordini ordine = ordiniRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato"));
        return ordine.getTotale();
    }

    
}
