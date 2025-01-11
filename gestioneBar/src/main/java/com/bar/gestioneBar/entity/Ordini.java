package com.bar.gestioneBar.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "ordini")
public class Ordini {
    @Id
    @Column(name = "ordine_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordiniId;
    @ManyToOne
    @JoinColumn(name = "tavolo_id")
    private Tavolo tavoloId;
    @CreationTimestamp // annotazione per il time stamp
    @Column(name = "data_creazione", nullable = false)
    private LocalDateTime dataCreazione;
    @Enumerated(EnumType.STRING)  // Mappo l'enum come stringa nel DB
    @Column(name = "stato", nullable = false)
    private StatoOrdine stato = StatoOrdine.IN_PREPARAZIONE;  // Default
    @Column(name = "totale", nullable = false)
    private BigDecimal totale = BigDecimal.ZERO;
    public Long getOrdiniId() {
        return ordiniId;
    }
    public void setOrdiniId(Long ordiniId) {
        this.ordiniId = ordiniId;
    }
    public Tavolo getTavoloId() {
        return tavoloId;
    }
    public void setTavoloId(Tavolo tavoloId) {
        this.tavoloId = tavoloId;
    }
    public LocalDateTime getDataCreazione() {
        return dataCreazione;
    }
    public void setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione = dataCreazione;
    }
    public StatoOrdine getStato() {
        return stato;
    }
    public void setStato(StatoOrdine stato) {
        this.stato = stato;
    }
    public BigDecimal getTotale() {
        return totale;
    }
    public void setTotale(BigDecimal totale) {
        this.totale = totale;
    }
    public Ordini() {
    }
    public Ordini(Tavolo tavoloId, LocalDateTime dataCreazione, StatoOrdine stato, BigDecimal totale) {
        this.tavoloId = tavoloId;
        this.dataCreazione = dataCreazione;
        this.stato = stato;
        this.totale = totale;
    }
    public Ordini(Long ordiniId, Tavolo tavoloId, LocalDateTime dataCreazione, StatoOrdine stato, BigDecimal totale) {
        this.ordiniId = ordiniId;
        this.tavoloId = tavoloId;
        this.dataCreazione = dataCreazione;
        this.stato = stato;
        this.totale = totale;
    }
    
}   
