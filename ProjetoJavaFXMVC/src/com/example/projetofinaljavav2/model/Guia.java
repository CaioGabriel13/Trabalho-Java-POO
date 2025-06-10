package com.example.projetofinaljavav2.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Guia implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String titulo;
    private String conteudo;
    private LocalDate dataCriacao;

    public Guia(int id, String titulo, String conteudo, LocalDate dataCriacao) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataCriacao = dataCriacao;
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

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "Guia{" +
               "id=" + id +
               ", titulo=\'" + titulo + '\'' +
               ", conteudo=\'" + conteudo + '\'' +
               ", dataCriacao=" + dataCriacao +
               '}';
    }
}

