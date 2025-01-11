package com.bar.gestioneBar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "dettagli_ordine")
public class DettaglioOrdine {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long dettaglioId;
   @ManyToOne
   @JoinColumn(name = "ordine_id", nullable = false)
   private Ordini ordineId;
   @ManyToOne
   @JoinColumn(name = "prodotto_id", nullable = false)
   private Prodotto prodottoId;
   @Column(name= "quantita", nullable = false)
   private int quantita;
   @Column(name= "note")
   
   private String note;
public String getNote() {
    return note;
}
public void setNote(String note) {
    this.note = note;
}
public Long getDettaglioId() {
    return dettaglioId;
}
public void setDettaglioId(Long dettaglioId) {
    this.dettaglioId = dettaglioId;
}
public Ordini getOrdineId() {
    return ordineId;
}
public void setOrdineId(Ordini ordineId) {
    this.ordineId = ordineId;
}
public Prodotto getProdottoId() {
    return prodottoId;
}
public void setProdottoId(Prodotto prodottoId) {
    this.prodottoId = prodottoId;
}
public int getQuantita() {
    return quantita;
}
public void setQuantita(int quantita) {
    this.quantita = quantita;
}
public DettaglioOrdine() {
}
public DettaglioOrdine(Ordini ordineId, Prodotto prodottoId, int quantita, String note) {
    
    this.ordineId = ordineId;
    this.prodottoId = prodottoId;
    this.quantita = quantita;
    this.note = note;
}
public DettaglioOrdine(Long dettaglioId, Ordini ordineId, Prodotto prodottoId, int quantita, String note) {
    this.dettaglioId = dettaglioId;
    this.ordineId = ordineId;
    this.prodottoId = prodottoId;
    this.quantita = quantita;
    this.note = note;
}

}
