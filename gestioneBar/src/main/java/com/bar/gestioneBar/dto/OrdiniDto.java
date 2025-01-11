package com.bar.gestioneBar.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bar.gestioneBar.entity.StatoOrdine;

public class OrdiniDto {
    private Long ordiniId;
    private TavoloDto tavoloId;
    private LocalDateTime dataCreazione;
    private StatoOrdine stato;
    private BigDecimal totale = BigDecimal.ZERO;
    public Long getOrdiniId() {
        return ordiniId;
    }
    public void setOrdiniId(Long ordiniId) {
        this.ordiniId = ordiniId;
    }
    public TavoloDto getTavoloId() {
        return tavoloId;
    }
    public void setTavoloId(TavoloDto tavoloId) {
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
    public OrdiniDto() {
    }
    public OrdiniDto(Long ordiniId, TavoloDto tavoloId, LocalDateTime dataCreazione, StatoOrdine stato,
            BigDecimal totale) {
        this.ordiniId = ordiniId;
        this.tavoloId = tavoloId;
        this.dataCreazione = dataCreazione;
        this.stato = stato;
        this.totale = totale;
    }
    public OrdiniDto(TavoloDto tavoloId, LocalDateTime dataCreazione, StatoOrdine stato, BigDecimal totale) {
        this.tavoloId = tavoloId;
        this.dataCreazione = dataCreazione;
        this.stato = stato;
        this.totale = totale;
    }
    
}