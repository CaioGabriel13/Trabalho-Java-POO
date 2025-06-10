package com.example.projetofinaljavav2.dao;

import com.example.projetofinaljavav2.model.Guia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GuiaDAO implements GenericDAO<Guia> {
    private static final String FILE_PATH = "/home/ubuntu/ProjetoJavaFXMVC/data/guias.dat";

    @Override
    public void create(Guia guia) {
        List<Guia> guias = readAll();
        guias.add(guia);
        saveAll(guias);
    }

    @Override
    public Guia readById(int id) {
        return readAll().stream()
                .filter(g -> g.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Guia> readAll() {
        List<Guia> guias = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return guias;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            guias = (List<Guia>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler guias do arquivo: " + e.getMessage());
        }
        return guias;
    }

    @Override
    public void update(Guia updatedGuia) {
        List<Guia> guias = readAll();
        Optional<Guia> existingGuia = guias.stream()
                .filter(g -> g.getId() == updatedGuia.getId())
                .findFirst();

        existingGuia.ifPresent(g -> {
            int index = guias.indexOf(g);
            guias.set(index, updatedGuia);
            saveAll(guias);
        });
    }

    @Override
    public void delete(int id) {
        List<Guia> guias = readAll();
        guias.removeIf(g -> g.getId() == id);
        saveAll(guias);
    }

    private void saveAll(List<Guia> guias) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(guias);
        } catch (IOException e) {
            System.err.println("Erro ao salvar guias no arquivo: " + e.getMessage());
        }
    }
}

