package com.bar.gestioneBar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bar.gestioneBar.dto.ProdottoDto;
import com.bar.gestioneBar.entity.Prodotto;
import com.bar.gestioneBar.mapper.ProdottoMapper;
import com.bar.gestioneBar.repository.ProdottoRepository;

@Service
public class ProdottoService {
    @Autowired
    private ProdottoRepository prodottoRepository;
    @Autowired
    private ProdottoMapper prodottoMapper;
    //get tutta la lista
    public List<ProdottoDto> getAllProdotti(){
        List<Prodotto> prodotti = prodottoRepository.findAll();
        return prodottoMapper.toDtoList(prodotti);
    }
    //aggiungi un prodotto
    // transactional permette il rollback se qualcosa va storto
    @Transactional
    public ProdottoDto aggiungiProdotto(ProdottoDto prodottoDto) {
        Prodotto prodotto = prodottoMapper.toEntity(prodottoDto);
        prodotto = prodottoRepository.save(prodotto);
        return prodottoMapper.toDto(prodotto);
    }
    //elimina un prodotto
    public void eliminaProdotto(Long prodottoId) {
        prodottoRepository.deleteById(prodottoId);
    }
    //modifica campi prodotto
    @Transactional
    public ProdottoDto modificaProdotto(Long prodottoId, ProdottoDto prodottoDto) {
        Prodotto prodotto = prodottoRepository.findById(prodottoId)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));

        // Modifica i dettagli del prodotto
        prodotto.setNome(prodottoDto.getNome());
        prodotto.setCategoria(prodottoDto.getCategoria());
        prodotto.setPrezzo(prodottoDto.getPrezzo());

        prodotto = prodottoRepository.save(prodotto);
        return prodottoMapper.toDto(prodotto);
    } 
}
