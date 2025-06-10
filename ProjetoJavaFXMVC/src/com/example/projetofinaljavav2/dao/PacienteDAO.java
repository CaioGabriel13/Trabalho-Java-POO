package com.example.projetofinaljavav2.dao;

import com.example.projetofinaljavav2.model.Paciente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PacienteDAO implements GenericDAO<Paciente> {
    private static final String FILE_PATH = "/home/ubuntu/ProjetoJavaFXMVC/data/pacientes.dat";

    @Override
    public void create(Paciente paciente) {
        List<Paciente> pacientes = readAll();
        pacientes.add(paciente);
        saveAll(pacientes);
    }

    @Override
    public Paciente readById(int id) {
        return readAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Paciente> readAll() {
        List<Paciente> pacientes = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return pacientes;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            pacientes = (List<Paciente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler pacientes do arquivo: " + e.getMessage());
        }
        return pacientes;
    }

    @Override
    public void update(Paciente updatedPaciente) {
        List<Paciente> pacientes = readAll();
        Optional<Paciente> existingPaciente = pacientes.stream()
                .filter(p -> p.getId() == updatedPaciente.getId())
                .findFirst();

        existingPaciente.ifPresent(p -> {
            int index = pacientes.indexOf(p);
            pacientes.set(index, updatedPaciente);
            saveAll(pacientes);
        });
    }

    @Override
    public void delete(int id) {
        List<Paciente> pacientes = readAll();
        pacientes.removeIf(p -> p.getId() == id);
        saveAll(pacientes);
    }

    private void saveAll(List<Paciente> pacientes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(pacientes);
        } catch (IOException e) {
            System.err.println("Erro ao salvar pacientes no arquivo: " + e.getMessage());
        }
    }
}

