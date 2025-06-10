package com.example.projetofinaljavav2.controller;

import com.example.projetofinaljavav2.dao.AnuncioDAO;
import com.example.projetofinaljavav2.model.Anuncio;
import com.example.projetofinaljavav2.view.AnuncioView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class AnuncioController {
    private AnuncioView view;
    private AnuncioDAO dao;
    private ObservableList<Anuncio> anuncioList;

    public AnuncioController(AnuncioView view) {
        this.view = view;
        this.dao = new AnuncioDAO();
        this.anuncioList = FXCollections.observableArrayList();

        // Carregar dados iniciais
        loadAnuncios();

        // Configurar listeners dos botões
        this.view.getAddButton().setOnAction(e -> addAnuncio());
        this.view.getUpdateButton().setOnAction(e -> updateAnuncio());
        this.view.getDeleteButton().setOnAction(e -> deleteAnuncio());
        this.view.getClearButton().setOnAction(e -> view.clearFields());
    }

    private void loadAnuncios() {
        anuncioList.clear();
        anuncioList.addAll(dao.readAll());
        view.getAnuncioTable().setItems(anuncioList);
    }

    private void addAnuncio() {
        try {
            // Gerar um ID simples
            int newId = anuncioList.isEmpty() ? 1 : anuncioList.stream().mapToInt(Anuncio::getId).max().getAsInt() + 1;
            String titulo = view.getTituloField().getText();
            String descricao = view.getDescricaoArea().getText();
            LocalDate dataPublicacao = view.getDataPublicacaoPicker().getValue();
            int idPsicologo = Integer.parseInt(view.getIdPsicologoField().getText());

            if (titulo.isEmpty() || descricao.isEmpty() || dataPublicacao == null) {
                view.showAlert("Erro", "Título, Descrição e Data de Publicação devem ser preenchidos.");
                return;
            }

            Anuncio anuncio = new Anuncio(newId, titulo, descricao, dataPublicacao, idPsicologo);
            dao.create(anuncio);
            loadAnuncios();
            view.clearFields();
            view.showAlert("Sucesso", "Anúncio adicionado com sucesso!");
        } catch (NumberFormatException e) {
            view.showAlert("Erro", "ID do Psicólogo inválido. Digite um número.");
        } catch (Exception e) {
            view.showAlert("Erro", "Erro ao adicionar anúncio: " + e.getMessage());
        }
    }

    private void updateAnuncio() {
        try {
            int id = Integer.parseInt(view.getIdField().getText());
            String titulo = view.getTituloField().getText();
            String descricao = view.getDescricaoArea().getText();
            LocalDate dataPublicacao = view.getDataPublicacaoPicker().getValue();
            int idPsicologo = Integer.parseInt(view.getIdPsicologoField().getText());

            if (titulo.isEmpty() || descricao.isEmpty() || dataPublicacao == null) {
                view.showAlert("Erro", "Título, Descrição e Data de Publicação devem ser preenchidos.");
                return;
            }

            Anuncio anuncio = new Anuncio(id, titulo, descricao, dataPublicacao, idPsicologo);
            dao.update(anuncio);
            loadAnuncios();
            view.clearFields();
            view.showAlert("Sucesso", "Anúncio atualizado com sucesso!");
        } catch (NumberFormatException e) {
            view.showAlert("Erro", "ID ou ID do Psicólogo inválido. Selecione um anúncio da tabela ou digite números válidos.");
        } catch (Exception e) {
            view.showAlert("Erro", "Erro ao atualizar anúncio: " + e.getMessage());
        }
    }

    private void deleteAnuncio() {
        try {
            int id = Integer.parseInt(view.getIdField().getText());
            dao.delete(id);
            loadAnuncios();
            view.clearFields();
            view.showAlert("Sucesso", "Anúncio excluído com sucesso!");
        } catch (NumberFormatException e) {
            view.showAlert("Erro", "ID inválido. Selecione um anúncio da tabela para excluir.");
        } catch (Exception e) {
            view.showAlert("Erro", "Erro ao excluir anúncio: " + e.getMessage());
        }
    }
}

