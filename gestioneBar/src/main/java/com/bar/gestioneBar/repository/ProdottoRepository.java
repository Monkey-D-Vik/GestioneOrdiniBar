package com.bar.gestioneBar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bar.gestioneBar.entity.Prodotto;

public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {
}
