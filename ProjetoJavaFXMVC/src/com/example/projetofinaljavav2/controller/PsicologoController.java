package com.example.projetofinaljavav2.controller;

import com.example.projetofinaljavav2.dao.PsicologoDAO;
import com.example.projetofinaljavav2.model.Psicologo;
import com.example.projetofinaljavav2.view.PsicologoView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PsicologoController {
    private PsicologoView view;
    private PsicologoDAO dao;
    private ObservableList<Psicologo> psicologoList;

    public PsicologoController(PsicologoView view) {
        this.view = view;
        this.dao = new PsicologoDAO();
        this.psicologoList = FXCollections.observableArrayList();

        // Carregar dados iniciais
        loadPsicologos();

        // Configurar listeners dos botões
        this.view.getAddButton().setOnAction(e -> addPsicologo());
        this.view.getUpdateButton().setOnAction(e -> updatePsicologo());
        this.view.getDeleteButton().setOnAction(e -> deletePsicologo());
        this.view.getClearButton().setOnAction(e -> view.clearFields());
    }

    private void loadPsicologos() {
        psicologoList.clear();
        psicologoList.addAll(dao.readAll());
        view.getPsicologoTable().setItems(psicologoList);
    }

    private void addPsicologo() {
        try {
            // Gerar um ID simples
            int newId = psicologoList.isEmpty() ? 1 : psicologoList.stream().mapToInt(Psicologo::getId).max().getAsInt() + 1;
            String nome = view.getNomeField().getText();
            String crp = view.getCrpField().getText();
            String especialidade = view.getEspecialidadeField().getText();
            String telefone = view.getTelefoneField().getText();
            String email = view.getEmailField().getText();

            if (nome.isEmpty() || crp.isEmpty() || especialidade.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
                view.showAlert("Erro", "Todos os campos devem ser preenchidos.");
                return;
            }

            Psicologo psicologo = new Psicologo(newId, nome, crp, especialidade, telefone, email);
            dao.create(psicologo);
            loadPsicologos();
            view.clearFields();
            view.showAlert("Sucesso", "Psicólogo adicionado com sucesso!");
        } catch (Exception e) {
            view.showAlert("Erro", "Erro ao adicionar psicólogo: " + e.getMessage());
        }
    }

    private void updatePsicologo() {
        try {
            int id = Integer.parseInt(view.getIdField().getText());
            String nome = view.getNomeField().getText();
            String crp = view.getCrpField().getText();
            String especialidade = view.getEspecialidadeField().getText();
            String telefone = view.getTelefoneField().getText();
            String email = view.getEmailField().getText();

            if (nome.isEmpty() || crp.isEmpty() || especialidade.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
                view.showAlert("Erro", "Todos os campos devem ser preenchidos.");
                return;
            }

            Psicologo psicologo = new Psicologo(id, nome, crp, especialidade, telefone, email);
            dao.update(psicologo);
            loadPsicologos();
            view.clearFields();
            view.showAlert("Sucesso", "Psicólogo atualizado com sucesso!");
        } catch (NumberFormatException e) {
            view.showAlert("Erro", "ID inválido. Selecione um psicólogo da tabela.");
        } catch (Exception e) {
            view.showAlert("Erro", "Erro ao atualizar psicólogo: " + e.getMessage());
        }
    }

    private void deletePsicologo() {
        try {
            int id = Integer.parseInt(view.getIdField().getText());
            dao.delete(id);
            loadPsicologos();
            view.clearFields();
            view.showAlert("Sucesso", "Psicólogo excluído com sucesso!");
        } catch (NumberFormatException e) {
            view.showAlert("Erro", "ID inválido. Selecione um psicólogo da tabela para excluir.");
        } catch (Exception e) {
            view.showAlert("Erro", "Erro ao excluir psicólogo: " + e.getMessage());
        }
    }
}

