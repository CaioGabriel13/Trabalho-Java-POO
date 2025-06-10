package com.example.projetofinaljavav2.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Receita implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nomePaciente;
    private String nomeMedicamento;
    private String dosagem;
    private String instrucoes;
    private LocalDate dataEmissao;

    public Receita(int id, String nomePaciente, String nomeMedicamento, String dosagem, String instrucoes, LocalDate dataEmissao) {
        this.id = id;
        this.nomePaciente = nomePaciente;
        this.nomeMedicamento = nomeMedicamento;
        this.dosagem = dosagem;
        this.instrucoes = instrucoes;
        this.dataEmissao = dataEmissao;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }

    public void setNomeMedicamento(String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes(String instrucoes) {
        this.instrucoes = instrucoes;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    @Override
    public String toString() {
        return "Receita{" +
               "id=" + id +
               ", nomePaciente=\'" + nomePaciente + '\'' +
               ", nomeMedicamento=\'" + nomeMedicamento + '\'' +
               ", dosagem=\'" + dosagem + '\'' +
               ", instrucoes=\'" + instrucoes + '\'' +
               ", dataEmissao=" + dataEmissao +
               '\'' +
               '}';
    }
}

