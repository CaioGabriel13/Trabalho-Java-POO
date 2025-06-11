package com.example.projetofinaljavav2.controller;

import com.example.projetofinaljavav2.dao.PsicologoDAO;
import com.example.projetofinaljavav2.model.Psicologo;
import com.example.projetofinaljavav2.view.PsicologoView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.regex.Pattern;

public class PsicologoController {
    private final PsicologoView view;
    private final PsicologoDAO dao;
    private final ObservableList<Psicologo> psicologoList;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public PsicologoController(PsicologoView view) {
        this.view = view;
        this.dao = new PsicologoDAO();
        this.psicologoList = FXCollections.observableArrayList();

        configureSelection();
        configureActions();
        loadPsicologos();
    }

    private void configureSelection() {
        view.getPsicologoTable().getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        view.getIdField().setText(String.valueOf(newSel.getId()));
                        view.getNomeField().setText(newSel.getNome());
                        view.getCrpField().setText(newSel.getCrp());
                        view.getEspecialidadeField().setText(newSel.getEspecialidade());
                        view.getTelefoneField().setText(newSel.getTelefone());
                        view.getEmailField().setText(newSel.getEmail());
                    }
                });
    }

    private void configureActions() {
        view.getAddButton().setOnAction(e -> addPsicologo());
        view.getUpdateButton().setOnAction(e -> updatePsicologo());
        view.getDeleteButton().setOnAction(e -> deletePsicologo());
        view.getClearButton().setOnAction(e -> view.clearFields());
    }

    private void loadPsicologos() {
        List<Psicologo> list = dao.readAll();
        psicologoList.setAll(list);
        view.getPsicologoTable().setItems(psicologoList);
    }

    private void addPsicologo() {
        try {
            String nome = view.getNomeField().getText().trim();
            String crp = view.getCrpField().getText().trim();
            String esp = view.getEspecialidadeField().getText().trim();
            String tel = view.getTelefoneField().getText().trim();
            String email = view.getEmailField().getText().trim();

            if (nome.isEmpty()||crp.isEmpty()||esp.isEmpty()||tel.isEmpty()||email.isEmpty()) {
                view.showAlert("Erro","Todos os campos devem ser preenchidos.");
                return;
            }
            if (!isValidCRP(crp)) {
                view.showAlert("Erro","CRP inválido. Insira pelo menos 5 dígitos.");
                return;
            }
            if (!isValidPhone(tel)) {
                view.showAlert("Erro","Telefone inválido. Deve ter 10 ou 11 dígitos.");
                return;
            }
            if (!isValidEmail(email)) {
                view.showAlert("Erro","Email inválido.");
                return;
            }

            int newId = psicologoList.isEmpty() ? 1 : psicologoList.stream()
                    .mapToInt(Psicologo::getId).max().getAsInt()+1;
            Psicologo p = new Psicologo(newId,nome,crp,esp,tel,email);
            dao.create(p);
            loadPsicologos();
            view.clearFields();
            view.showAlert("Sucesso","Psicólogo adicionado com sucesso.");
        } catch(Exception ex) {
            view.showAlert("Erro","Falha ao adicionar: " + ex.getMessage());
        }
    }

    private void updatePsicologo() {
        try {
            int id = Integer.parseInt(view.getIdField().getText());
            String nome = view.getNomeField().getText().trim();
            String crp = view.getCrpField().getText().trim();
            String esp = view.getEspecialidadeField().getText().trim();
            String tel = view.getTelefoneField().getText().trim();
            String email = view.getEmailField().getText().trim();

            if (nome.isEmpty()||crp.isEmpty()||esp.isEmpty()||tel.isEmpty()||email.isEmpty()) {
                view.showAlert("Erro","Todos os campos devem ser preenchidos.");
                return;
            }
            if (!isValidCRP(crp)) {
                view.showAlert("Erro","CRP inválido.");
                return;
            }
            if (!isValidPhone(tel)) {
                view.showAlert("Erro","Telefone inválido.");
                return;
            }
            if (!isValidEmail(email)) {
                view.showAlert("Erro","Email inválido.");
                return;
            }

            Psicologo p = new Psicologo(id,nome,crp,esp,tel,email);
            dao.update(p);
            loadPsicologos();
            view.clearFields();
            view.showAlert("Sucesso","Psicólogo atualizado.");
        } catch(NumberFormatException ex) {
            view.showAlert("Erro","Selecione um psicólogo válido.");
        } catch(Exception ex) {
            view.showAlert("Erro","Falha ao atualizar: " + ex.getMessage());
        }
    }

    private void deletePsicologo() {
        try {
            int id = Integer.parseInt(view.getIdField().getText());
            dao.delete(id);
            loadPsicologos();
            view.clearFields();
            view.showAlert("Sucesso","Psicólogo excluído.");
        } catch(NumberFormatException ex) {
            view.showAlert("Erro","Selecione um psicólogo.");
        } catch(Exception ex) {
            view.showAlert("Erro","Falha ao excluir: " + ex.getMessage());
        }
    }

    private boolean isValidCRP(String crp) {
        String digits = crp.replaceAll("\\D","   ");
        return digits.matches("\\d{5,}");
    }

    private boolean isValidPhone(String phone) {
        String digits = phone.replaceAll("\\D","   ");
        return digits.matches("\\d{10,11}");
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
