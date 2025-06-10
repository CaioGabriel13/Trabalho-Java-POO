package com.example.projetofinaljavav2.model;

import java.io.Serializable;

public class Psicologo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nome;
    private String crp;
    private String especialidade;
    private String telefone;
    private String email;

    public Psicologo(int id, String nome, String crp, String especialidade, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.crp = crp;
        this.especialidade = especialidade;
        this.telefone = telefone;
        this.email = email;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrp() {
        return crp;
    }

    public void setCrp(String crp) {
        this.crp = crp;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Psicologo{" +
               "id=" + id +
               ", nome=\'" + nome + '\'' +
               ", crp=\'" + crp + '\'' +
               ", especialidade=\'" + especialidade + '\'' +
               ", telefone=\'" + telefone + '\'' +
               ", email=\'" + email + '\'' +
               '}';
    }
}

