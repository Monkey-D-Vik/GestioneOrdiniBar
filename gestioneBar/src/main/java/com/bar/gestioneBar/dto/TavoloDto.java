package com.bar.gestioneBar.dto;

public class TavoloDto {
    private Long tavolo_id;
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
    public TavoloDto() {
    }
    public TavoloDto(Long tavolo_id, int numero) {
        this.tavolo_id = tavolo_id;
        this.numero = numero;
    }
    public TavoloDto(int numero) {
        this.numero = numero;
    }

}
