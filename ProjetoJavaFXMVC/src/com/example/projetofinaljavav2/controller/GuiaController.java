package com.example.projetofinaljavav2.controller;

import com.example.projetofinaljavav2.dao.GuiaDAO;
import com.example.projetofinaljavav2.model.Guia;
import com.example.projetofinaljavav2.view.GuiaView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class GuiaController {
    private GuiaView view;
    private GuiaDAO dao;
    private ObservableList<Guia> guiaList;

    public GuiaController(GuiaView view) {
        this.view = view;
        this.dao = new GuiaDAO();
        this.guiaList = FXCollections.observableArrayList();

        // Carregar dados iniciais
        loadGuias();

        // Configurar listeners dos botões
        this.view.getAddButton().setOnAction(e -> addGuia());
        this.view.getUpdateButton().setOnAction(e -> updateGuia());
        this.view.getDeleteButton().setOnAction(e -> deleteGuia());
        this.view.getClearButton().setOnAction(e -> view.clearFields());
    }

    private void loadGuias() {
        guiaList.clear();
        guiaList.addAll(dao.readAll());
        view.getGuiaTable().setItems(guiaList);
    }

    private void addGuia() {
        try {
            // Gerar um ID simples
            int newId = guiaList.isEmpty() ? 1 : guiaList.stream().mapToInt(Guia::getId).max().getAsInt() + 1;
            String titulo = view.getTituloField().getText();
            String conteudo = view.getConteudoArea().getText();
            LocalDate dataCriacao = view.getDataCriacaoPicker().getValue();

            if (titulo.isEmpty() || conteudo.isEmpty() || dataCriacao == null) {
                view.showAlert("Erro", "Todos os campos devem ser preenchidos.");
                return;
            }

            Guia guia = new Guia(newId, titulo, conteudo, dataCriacao);
            dao.create(guia);
            loadGuias();
            view.clearFields();
            view.showAlert("Sucesso", "Guia adicionado com sucesso!");
        } catch (Exception e) {
            view.showAlert("Erro", "Erro ao adicionar guia: " + e.getMessage());
        }
    }

    private void updateGuia() {
        try {
            int id = Integer.parseInt(view.getIdField().getText());
            String titulo = view.getTituloField().getText();
            String conteudo = view.getConteudoArea().getText();
            LocalDate dataCriacao = view.getDataCriacaoPicker().getValue();

            if (titulo.isEmpty() || conteudo.isEmpty() || dataCriacao == null) {
                view.showAlert("Erro", "Todos os campos devem ser preenchidos.");
                return;
            }

            Guia guia = new Guia(id, titulo, conteudo, dataCriacao);
            dao.update(guia);
            loadGuias();
            view.clearFields();
            view.showAlert("Sucesso", "Guia atualizado com sucesso!");
        } catch (NumberFormatException e) {
            view.showAlert("Erro", "ID inválido. Selecione um guia da tabela.");
        } catch (Exception e) {
            view.showAlert("Erro", "Erro ao atualizar guia: " + e.getMessage());
        }
    }

    private void deleteGuia() {
        try {
            int id = Integer.parseInt(view.getIdField().getText());
            dao.delete(id);
            loadGuias();
            view.clearFields();
            view.showAlert("Sucesso", "Guia excluído com sucesso!");
        } catch (NumberFormatException e) {
            view.showAlert("Erro", "ID inválido. Selecione um guia da tabela para excluir.");
        } catch (Exception e) {
            view.showAlert("Erro", "Erro ao excluir guia: " + e.getMessage());
        }
    }
}

