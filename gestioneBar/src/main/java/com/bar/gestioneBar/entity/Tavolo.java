package com.bar.gestioneBar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tavoli")
public class Tavolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tavolo_id;
    @Column (nullable = false)
    private int numero;
    
    public Long getTavolo_id() {
        return tavolo_id;
    }
    public void setTavolo_id(Long tavolo_id) {
        this.tavolo_id = tavolo_id;
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public Tavolo(int numero) {
        this.numero = numero;
    }
    public Tavolo() {
    }
    public Tavolo(Long tavolo_id, int numero) {
        this.tavolo_id = tavolo_id;
        this.numero = numero;
    }
}