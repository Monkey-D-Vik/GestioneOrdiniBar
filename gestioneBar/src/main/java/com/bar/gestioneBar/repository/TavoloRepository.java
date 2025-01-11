package com.bar.gestioneBar.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.bar.gestioneBar.entity.Tavolo;

public interface TavoloRepository extends JpaRepository<Tavolo, Long> {
    
}
