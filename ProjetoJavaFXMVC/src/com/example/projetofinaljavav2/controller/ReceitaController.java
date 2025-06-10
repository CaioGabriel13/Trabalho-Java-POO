package com.example.projetofinaljavav2.controller;

import com.example.projetofinaljavav2.dao.ReceitaDAO;
import com.example.projetofinaljavav2.model.Receita;
import com.example.projetofinaljavav2.view.ReceitaView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class ReceitaController {
    private ReceitaView view;
    private ReceitaDAO dao;
    private ObservableList<Receita> receitaList;

    public ReceitaController(ReceitaView view) {
        this.view = view;
        this.dao = new ReceitaDAO();
        this.receitaList = FXCollections.observableArrayList();

        // Carregar dados iniciais
        loadReceitas();

        // Configurar listeners dos botões
        this.view.getAddButton().setOnAction(e -> addReceita());
        this.view.getUpdateButton().setOnAction(e -> updateReceita());
        this.view.getDeleteButton().setOnAction(e -> deleteReceita());
        this.view.getClearButton().setOnAction(e -> view.clearFields());
    }

    private void loadReceitas() {
        receitaList.clear();
        receitaList.addAll(dao.readAll());
        view.getReceitaTable().setItems(receitaList);
    }

    private void addReceita() {
        try {
            // Gerar um ID simples
            int newId = receitaList.isEmpty() ? 1 : receitaList.stream().mapToInt(Receita::getId).max().getAsInt() + 1;
            String nomePaciente = view.getNomePacienteField().getText();
            String nomeMedicamento = view.getNomeMedicamentoField().getText();
            String dosagem = view.getDosagemField().getText();
            String instrucoes = view.getInstrucoesArea().getText();
            LocalDate dataEmissao = view.getDataEmissaoPicker().getValue();

            if (nomePaciente.isEmpty() || nomeMedicamento.isEmpty() || dosagem.isEmpty() || instrucoes.isEmpty() || dataEmissao == null) {
                view.showAlert("Erro", "Todos os campos devem ser preenchidos.");
                return;
            }

            Receita receita = new Receita(newId, nomePaciente, nomeMedicamento, dosagem, instrucoes, dataEmissao);
            dao.create(receita);
            loadReceitas();
            view.clearFields();
            view.showAlert("Sucesso", "Receita adicionada com sucesso!");
        } catch (Exception e) {
            view.showAlert("Erro", "Erro ao adicionar receita: " + e.getMessage());
        }
    }

    private void updateReceita() {
        try {
            int id = Integer.parseInt(view.getIdField().getText());
            String nomePaciente = view.getNomePacienteField().getText();
            String nomeMedicamento = view.getNomeMedicamentoField().getText();
            String dosagem = view.getDosagemField().getText();
            String instrucoes = view.getInstrucoesArea().getText();
            LocalDate dataEmissao = view.getDataEmissaoPicker().getValue();

            if (nomePaciente.isEmpty() || nomeMedicamento.isEmpty() || dosagem.isEmpty() || instrucoes.isEmpty() || dataEmissao == null) {
                view.showAlert("Erro", "Todos os campos devem ser preenchidos.");
                return;
            }

            Receita receita = new Receita(id, nomePaciente, nomeMedicamento, dosagem, instrucoes, dataEmissao);
            dao.update(receita);
            loadReceitas();
            view.clearFields();
            view.showAlert("Sucesso", "Receita atualizada com sucesso!");
        } catch (NumberFormatException e) {
            view.showAlert("Erro", "ID inválido. Selecione uma receita da tabela.");
        } catch (Exception e) {
            view.showAlert("Erro", "Erro ao atualizar receita: " + e.getMessage());
        }
    }

    private void deleteReceita() {
        try {
            int id = Integer.parseInt(view.getIdField().getText());
            dao.delete(id);
            loadReceitas();
            view.clearFields();
            view.showAlert("Sucesso", "Receita excluída com sucesso!");
        } catch (NumberFormatException e) {
            view.showAlert("Erro", "ID inválido. Selecione uma receita da tabela para excluir.");
        } catch (Exception e) {
            view.showAlert("Erro", "Erro ao excluir receita: " + e.getMessage());
        }
    }
}

