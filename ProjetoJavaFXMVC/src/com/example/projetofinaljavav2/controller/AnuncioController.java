package com.example.projetofinaljavav2.controller;

import com.example.projetofinaljavav2.dao.AnuncioDAO;
import com.example.projetofinaljavav2.model.Anuncio;
import com.example.projetofinaljavav2.view.AnuncioView;

import java.util.List;

public class AnuncioController {
    private final AnuncioView view;
    private final AnuncioDAO dao;

    public AnuncioController(AnuncioView view) {
        this.view = view;
        this.dao = new AnuncioDAO();

        configureListeners();
        loadAnuncios();
    }

    private void configureListeners() {
        view.getAddButton().setOnAction(e -> addAnuncio());
        view.getUpdateButton().setOnAction(e -> updateAnuncio());
        view.getDeleteButton().setOnAction(e -> deleteAnuncio());
        view.getClearButton().setOnAction(e -> {
            view.clearFields();
            loadAnuncios();
        });
    }

    private void loadAnuncios() {
        List<Anuncio> anuncios = dao.readAll();
        view.setAnuncios(anuncios);
    }

    private void addAnuncio() {
        try {
            List<Anuncio> current = dao.readAll();
            int newId = current.isEmpty() ? 1 : current.stream()
                    .mapToInt(Anuncio::getId).max().getAsInt() + 1;

            String titulo = view.getTituloField().getText();
            String descricao = view.getDescricaoArea().getText();
            var data = view.getDataPublicacaoPicker().getValue();
            int idPsicologo = Integer.parseInt(view.getIdPsicologoField().getText());

            if (titulo.isEmpty() || descricao.isEmpty() || data == null) {
                view.showAlert("Erro", "Título, Descrição e Data devem ser preenchidos.");
                return;
            }

            Anuncio anuncio = new Anuncio(newId, titulo, descricao, data, idPsicologo);
            dao.create(anuncio);
            loadAnuncios();
            view.clearFields();
            view.showAlert("Sucesso", "Anúncio adicionado com sucesso!");
        } catch (NumberFormatException ex) {
            view.showAlert("Erro", "ID do Psicólogo inválido.");
        } catch (Exception ex) {
            view.showAlert("Erro", "Erro ao adicionar anúncio: " + ex.getMessage());
        }
    }

    private void updateAnuncio() {
        try {
            int id = Integer.parseInt(view.getIdField().getText());
            String titulo = view.getTituloField().getText();
            String descricao = view.getDescricaoArea().getText();
            var data = view.getDataPublicacaoPicker().getValue();
            int idPsicologo = Integer.parseInt(view.getIdPsicologoField().getText());

            if (titulo.isEmpty() || descricao.isEmpty() || data == null) {
                view.showAlert("Erro", "Título, Descrição e Data devem ser preenchidos.");
                return;
            }

            Anuncio anuncio = new Anuncio(id, titulo, descricao, data, idPsicologo);
            dao.update(anuncio);
            loadAnuncios();
            view.clearFields();
            view.showAlert("Sucesso", "Anúncio atualizado com sucesso!");
        } catch (NumberFormatException ex) {
            view.showAlert("Erro", "Selecione um anúncio válido para atualizar.");
        } catch (Exception ex) {
            view.showAlert("Erro", "Erro ao atualizar anúncio: " + ex.getMessage());
        }
    }

    private void deleteAnuncio() {
        try {
            int id = Integer.parseInt(view.getIdField().getText());
            dao.delete(id);
            loadAnuncios();
            view.clearFields();
            view.showAlert("Sucesso", "Anúncio excluído com sucesso!");
        } catch (NumberFormatException ex) {
            view.showAlert("Erro", "Selecione um anúncio válido para excluir.");
        } catch (Exception ex) {
            view.showAlert("Erro", "Erro ao excluir anúncio: " + ex.getMessage());
        }
    }
}
