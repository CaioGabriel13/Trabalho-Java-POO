package com.example.projetofinaljavav2.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Anuncio implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String titulo;
    private String descricao;
    private LocalDate dataPublicacao;
    private int idPsicologo; // Referência ao ID do psicólogo que publicou o anúncio

    public Anuncio(int id, String titulo, String descricao, LocalDate dataPublicacao, int idPsicologo) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataPublicacao = dataPublicacao;
        this.idPsicologo = idPsicologo;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public int getIdPsicologo() {
        return idPsicologo;
    }

    public void setIdPsicologo(int idPsicologo) {
        this.idPsicologo = idPsicologo;
    }

    @Override
    public String toString() {
        return "Anuncio{" +
               "id=" + id +
               ", titulo=\'" + titulo + '\'' +
               ", descricao=\'" + descricao + '\'' +
               ", dataPublicacao=" + dataPublicacao +
               ", idPsicologo=" + idPsicologo +
               '}';
    }
}

