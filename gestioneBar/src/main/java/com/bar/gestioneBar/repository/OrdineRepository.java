package com.bar.gestioneBar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bar.gestioneBar.entity.Ordini;
import com.bar.gestioneBar.entity.StatoOrdine;

public interface OrdineRepository extends JpaRepository<Ordini, Long> {
    List<Ordini> findByStato(StatoOrdine stato);
}
