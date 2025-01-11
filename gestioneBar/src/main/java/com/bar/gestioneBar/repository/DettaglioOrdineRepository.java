package com.bar.gestioneBar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bar.gestioneBar.entity.DettaglioOrdine;

public interface DettaglioOrdineRepository extends JpaRepository<DettaglioOrdine, Long> {
    List<DettaglioOrdine> findByOrdineId_OrdiniId(Long ordineId);
}
