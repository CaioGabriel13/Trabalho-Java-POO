package com.example.projetofinaljavav2.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Paciente implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String email;
    private String endereco;

    public Paciente(int id, String nome, String cpf, LocalDate dataNascimento, String telefone, String email, String endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Paciente{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", cpf='" + cpf + '\'' +
               ", dataNascimento=" + dataNascimento +
               ", telefone='" + telefone + '\'' +
               ", email='" + email + '\'' +
               ", endereco='" + endereco + '\'' +
               '}';
    }
}

