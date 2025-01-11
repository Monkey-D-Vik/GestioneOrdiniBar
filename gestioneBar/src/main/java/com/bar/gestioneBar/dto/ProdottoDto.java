package com.bar.gestioneBar.dto;

import java.math.BigDecimal;

public class ProdottoDto {
    private Long prodottoId;
    private String nome;
    private String categoria;
    private BigDecimal prezzo;
    public Long getProdottoId() {
        return prodottoId;
    }
    public void setProdottoId(Long prodottoId) {
        this.prodottoId = prodottoId;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public BigDecimal getPrezzo() {
        return prezzo;
    }
    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }
    public ProdottoDto() {
    }
    public ProdottoDto(Long prodottoId, String nome, String categoria, BigDecimal prezzo) {
        this.prodottoId = prodottoId;
        this.nome = nome;
        this.categoria = categoria;
        this.prezzo = prezzo;
    }
    public ProdottoDto(String nome, String categoria, BigDecimal prezzo) {
        this.nome = nome;
        this.categoria = categoria;
        this.prezzo = prezzo;
    }
    
}
