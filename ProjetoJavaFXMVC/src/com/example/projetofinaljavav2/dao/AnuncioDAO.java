package com.example.projetofinaljavav2.dao;

import com.example.projetofinaljavav2.model.Anuncio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnuncioDAO implements GenericDAO<Anuncio> {
    private static final String FILE_PATH = "ProjetoJavaFXMVC/data/anuncios.dat";

    @Override
    public void create(Anuncio anuncio) {
        List<Anuncio> anuncios = readAll();
        anuncios.add(anuncio);
        saveAll(anuncios);
    }

    @Override
    public Anuncio readById(int id) {
        return readAll().stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Anuncio> readAll() {
        List<Anuncio> anuncios = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return anuncios;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            anuncios = (List<Anuncio>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler anúncios do arquivo: " + e.getMessage());
        }
        return anuncios;
    }

    @Override
    public void update(Anuncio updatedAnuncio) {
        List<Anuncio> anuncios = readAll();
        Optional<Anuncio> existingAnuncio = anuncios.stream()
                .filter(a -> a.getId() == updatedAnuncio.getId())
                .findFirst();

        existingAnuncio.ifPresent(a -> {
            int index = anuncios.indexOf(a);
            anuncios.set(index, updatedAnuncio);
            saveAll(anuncios);
        });
    }

    @Override
    public void delete(int id) {
        List<Anuncio> anuncios = readAll();
        anuncios.removeIf(a -> a.getId() == id);
        saveAll(anuncios);
    }

    private void saveAll(List<Anuncio> anuncios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(anuncios);
        } catch (IOException e) {
            System.err.println("Erro ao salvar anúncios no arquivo: " + e.getMessage());
        }
    }
}

