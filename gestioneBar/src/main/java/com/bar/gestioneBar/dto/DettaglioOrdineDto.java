package com.bar.gestioneBar.dto;

public class DettaglioOrdineDto {
    private Long dettaglioId;
    private OrdiniDto ordineId;
    private ProdottoDto prodottoId;
    private int quantita;
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
    public OrdiniDto getOrdineId() {
        return ordineId;
    }
    public void setOrdineId(OrdiniDto ordineId) {
        this.ordineId = ordineId;
    }
    public ProdottoDto getProdottoId() {
        return prodottoId;
    }
    public void setProdottoId(ProdottoDto prodottoId) {
        this.prodottoId = prodottoId;
    }
    public int getQuantita() {
        return quantita;
    }
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
    public DettaglioOrdineDto() {
    }
    public DettaglioOrdineDto(Long dettaglioId, OrdiniDto ordineId, ProdottoDto prodottoId, int quantita, String note) {
        this.dettaglioId = dettaglioId;
        this.ordineId = ordineId;
        this.prodottoId = prodottoId;
        this.quantita = quantita;
        this.note = note;
    }
    public DettaglioOrdineDto(OrdiniDto ordineId, ProdottoDto prodottoId, int quantita, String note) {
        this.ordineId = ordineId;
        this.prodottoId = prodottoId;
        this.quantita = quantita;
        this.note = note;
    }
    

}
