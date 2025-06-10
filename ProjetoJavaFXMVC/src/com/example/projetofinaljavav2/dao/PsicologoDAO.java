package com.example.projetofinaljavav2.dao;

import com.example.projetofinaljavav2.model.Psicologo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PsicologoDAO implements GenericDAO<Psicologo> {
    private static final String FILE_PATH = "/home/ubuntu/ProjetoJavaFXMVC/data/psicologos.dat";

    @Override
    public void create(Psicologo psicologo) {
        List<Psicologo> psicologos = readAll();
        psicologos.add(psicologo);
        saveAll(psicologos);
    }

    @Override
    public Psicologo readById(int id) {
        return readAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Psicologo> readAll() {
        List<Psicologo> psicologos = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return psicologos;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            psicologos = (List<Psicologo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler psicólogos do arquivo: " + e.getMessage());
        }
        return psicologos;
    }

    @Override
    public void update(Psicologo updatedPsicologo) {
        List<Psicologo> psicologos = readAll();
        Optional<Psicologo> existingPsicologo = psicologos.stream()
                .filter(p -> p.getId() == updatedPsicologo.getId())
                .findFirst();

        existingPsicologo.ifPresent(p -> {
            int index = psicologos.indexOf(p);
            psicologos.set(index, updatedPsicologo);
            saveAll(psicologos);
        });
    }

    @Override
    public void delete(int id) {
        List<Psicologo> psicologos = readAll();
        psicologos.removeIf(p -> p.getId() == id);
        saveAll(psicologos);
    }

    private void saveAll(List<Psicologo> psicologos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(psicologos);
        } catch (IOException e) {
            System.err.println("Erro ao salvar psicólogos no arquivo: " + e.getMessage());
        }
    }
}

