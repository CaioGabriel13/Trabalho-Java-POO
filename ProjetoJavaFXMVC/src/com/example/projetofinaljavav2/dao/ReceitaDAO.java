package com.example.projetofinaljavav2.dao;

import com.example.projetofinaljavav2.model.Receita;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReceitaDAO implements GenericDAO<Receita> {
    private static final String FILE_PATH = "/home/ubuntu/ProjetoJavaFXMVC/data/receitas.dat";

    @Override
    public void create(Receita receita) {
        List<Receita> receitas = readAll();
        receitas.add(receita);
        saveAll(receitas);
    }

    @Override
    public Receita readById(int id) {
        return readAll().stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Receita> readAll() {
        List<Receita> receitas = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return receitas;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            receitas = (List<Receita>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler receitas do arquivo: " + e.getMessage());
        }
        return receitas;
    }

    @Override
    public void update(Receita updatedReceita) {
        List<Receita> receitas = readAll();
        Optional<Receita> existingReceita = receitas.stream()
                .filter(r -> r.getId() == updatedReceita.getId())
                .findFirst();

        existingReceita.ifPresent(r -> {
            int index = receitas.indexOf(r);
            receitas.set(index, updatedReceita);
            saveAll(receitas);
        });
    }

    @Override
    public void delete(int id) {
        List<Receita> receitas = readAll();
        receitas.removeIf(r -> r.getId() == id);
        saveAll(receitas);
    }

    private void saveAll(List<Receita> receitas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(receitas);
        } catch (IOException e) {
            System.err.println("Erro ao salvar receitas no arquivo: " + e.getMessage());
        }
    }
}

