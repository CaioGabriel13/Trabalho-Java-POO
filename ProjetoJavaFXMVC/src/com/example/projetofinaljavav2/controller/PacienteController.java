package com.example.projetofinaljavav2.controller;

import com.example.projetofinaljavav2.dao.PacienteDAO;
import com.example.projetofinaljavav2.model.Paciente;
import com.example.projetofinaljavav2.view.PacienteView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class PacienteController {
    private PacienteView view;
    private PacienteDAO dao;
    private ObservableList<Paciente> pacienteList;

    public PacienteController(PacienteView view) {
        this.view = view;
        this.dao = new PacienteDAO();
        this.pacienteList = FXCollections.observableArrayList();

        // Carregar dados iniciais
        loadPacientes();

        // Configurar listeners dos botões
        this.view.getAddButton().setOnAction(e -> addPaciente());
        this.view.getUpdateButton().setOnAction(e -> updatePaciente());
        this.view.getDeleteButton().setOnAction(e -> deletePaciente());
        this.view.getClearButton().setOnAction(e -> view.clearFields());
    }

    private void loadPacientes() {
        pacienteList.clear();
        pacienteList.addAll(dao.readAll());
        view.getPacienteTable().setItems(pacienteList);
    }

    private void addPaciente() {
        try {
            // Gerar um ID simples (pode ser melhorado para IDs únicos e sequenciais)
            int newId = pacienteList.isEmpty() ? 1 : pacienteList.stream().mapToInt(Paciente::getId).max().getAsInt() + 1;
            String nome = view.getNomeField().getText();
            String cpf = view.getCpfField().getText();
            LocalDate dataNascimento = view.getDataNascimentoPicker().getValue();
            String telefone = view.getTelefoneField().getText();
            String email = view.getEmailField().getText();
            String endereco = view.getEnderecoField().getText();

            if (nome.isEmpty() || cpf.isEmpty() || dataNascimento == null || telefone.isEmpty() || email.isEmpty() || endereco.isEmpty()) {
                view.showAlert("Erro", "Todos os campos devem ser preenchidos.");
                return;
            }

            Paciente paciente = new Paciente(newId, nome, cpf, dataNascimento, telefone, email, endereco);
            dao.create(paciente);
            loadPacientes();
            view.clearFields();
            view.showAlert("Sucesso", "Paciente adicionado com sucesso!");
        } catch (Exception e) {
            view.showAlert("Erro", "Erro ao adicionar paciente: " + e.getMessage());
        }
    }

    private void updatePaciente() {
        try {
            int id = Integer.parseInt(view.getIdField().getText());
            String nome = view.getNomeField().getText();
            String cpf = view.getCpfField().getText();
            LocalDate dataNascimento = view.getDataNascimentoPicker().getValue();
            String telefone = view.getTelefoneField().getText();
            String email = view.getEmailField().getText();
            String endereco = view.getEnderecoField().getText();

            if (nome.isEmpty() || cpf.isEmpty() || dataNascimento == null || telefone.isEmpty() || email.isEmpty() || endereco.isEmpty()) {
                view.showAlert("Erro", "Todos os campos devem ser preenchidos.");
                return;
            }

            Paciente paciente = new Paciente(id, nome, cpf, dataNascimento, telefone, email, endereco);
            dao.update(paciente);
            loadPacientes();
            view.clearFields();
            view.showAlert("Sucesso", "Paciente atualizado com sucesso!");
        } catch (NumberFormatException e) {
            view.showAlert("Erro", "ID inválido. Selecione um paciente da tabela.");
        } catch (Exception e) {
            view.showAlert("Erro", "Erro ao atualizar paciente: " + e.getMessage());
        }
    }

    private void deletePaciente() {
        try {
            int id = Integer.parseInt(view.getIdField().getText());
            dao.delete(id);
            loadPacientes();
            view.clearFields();
            view.showAlert("Sucesso", "Paciente excluído com sucesso!");
        } catch (NumberFormatException e) {
            view.showAlert("Erro", "ID inválido. Selecione um paciente da tabela para excluir.");
        } catch (Exception e) {
            view.showAlert("Erro", "Erro ao excluir paciente: " + e.getMessage());
        }
    }
}

