package com.example.projetofinaljavav2.controller;

import com.example.projetofinaljavav2.dao.PacienteDAO;
import com.example.projetofinaljavav2.model.Paciente;
import com.example.projetofinaljavav2.view.PacienteView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class PacienteController {
    private final PacienteView view;
    private final PacienteDAO dao;
    private final ObservableList<Paciente> pacienteList;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
    );

    public PacienteController(PacienteView view) {
        this.view = view;
        this.dao = new PacienteDAO();
        this.pacienteList = FXCollections.observableArrayList();

        configureTableSelection();
        configureListeners();
        loadPacientes();
    }

    private void configureTableSelection() {
        view.getPacienteTable().getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        view.getIdField().setText(String.valueOf(newSel.getId()));
                        view.getNomeField().setText(newSel.getNome());
                        view.getCpfField().setText(newSel.getCpf());
                        view.getDataNascimentoPicker().setValue(newSel.getDataNascimento());
                        view.getTelefoneField().setText(newSel.getTelefone());
                        view.getEmailField().setText(newSel.getEmail());
                        view.getEnderecoArea().setText(newSel.getEndereco());
                    }
                });
    }

    private void configureListeners() {
        view.getAddButton().setOnAction(e -> addPaciente());
        view.getUpdateButton().setOnAction(e -> updatePaciente());
        view.getDeleteButton().setOnAction(e -> deletePaciente());
        view.getClearButton().setOnAction(e -> {
            view.clearFields();
            view.getPacienteTable().getSelectionModel().clearSelection();
        });
    }

    private void loadPacientes() {
        List<Paciente> pacientes = dao.readAll();
        pacienteList.setAll(pacientes);
        view.getPacienteTable().setItems(pacienteList);
    }

    private void addPaciente() {
        try {
            String nome = view.getNomeField().getText().trim();
            String cpf = view.getCpfField().getText().trim();
            LocalDate dataNasc = view.getDataNascimentoPicker().getValue();
            String tel = view.getTelefoneField().getText().trim();
            String email = view.getEmailField().getText().trim();
            String end = view.getEnderecoArea().getText().trim();

            if (nome.isEmpty() || cpf.isEmpty() || dataNasc == null || tel.isEmpty() || email.isEmpty() || end.isEmpty()) {
                view.showAlert("Erro", "Todos os campos devem ser preenchidos.");
                return;
            }
            if (!isValidCPF(cpf)) {
                view.showAlert("Erro", "CPF inválido. Deve conter 11 dígitos.");
                return;
            }
            if (!isValidPhone(tel)) {
                view.showAlert("Erro", "Telefone inválido. Deve conter 10 ou 11 dígitos.");
                return;
            }
            if (!isValidEmail(email)) {
                view.showAlert("Erro", "Email inválido. Insira um endereço de email válido.");
                return;
            }
            if (dataNasc.isAfter(LocalDate.now())) {
                view.showAlert("Erro", "Data de nascimento não pode ser no futuro.");
                return;
            }

            List<Paciente> current = dao.readAll();
            int newId = current.isEmpty() ? 1 : current.stream()
                    .mapToInt(Paciente::getId).max().getAsInt() + 1;

            Paciente p = new Paciente(newId, nome, cpf, dataNasc, tel, email, end);
            dao.create(p);
            loadPacientes();
            view.clearFields();
            view.showAlert("Sucesso", "Paciente adicionado com sucesso!");
        } catch (Exception ex) {
            view.showAlert("Erro", "Falha ao adicionar paciente: " + ex.getMessage());
        }
    }

    private void updatePaciente() {
        try {
            int id = Integer.parseInt(view.getIdField().getText());
            String nome = view.getNomeField().getText().trim();
            String cpf = view.getCpfField().getText().trim();
            LocalDate dataNasc = view.getDataNascimentoPicker().getValue();
            String tel = view.getTelefoneField().getText().trim();
            String email = view.getEmailField().getText().trim();
            String end = view.getEnderecoArea().getText().trim();

            if (nome.isEmpty() || cpf.isEmpty() || dataNasc == null || tel.isEmpty() || email.isEmpty() || end.isEmpty()) {
                view.showAlert("Erro", "Todos os campos devem ser preenchidos.");
                return;
            }
            if (!isValidCPF(cpf)) {
                view.showAlert("Erro", "CPF inválido. Deve conter 11 dígitos.");
                return;
            }
            if (!isValidPhone(tel)) {
                view.showAlert("Erro", "Telefone inválido. Deve conter 10 ou 11 dígitos.");
                return;
            }
            if (!isValidEmail(email)) {
                view.showAlert("Erro", "Email inválido. Insira um endereço de email válido.");
                return;
            }
            if (dataNasc.isAfter(LocalDate.now())) {
                view.showAlert("Erro", "Data de nascimento não pode ser no futuro.");
                return;
            }

            Paciente p = new Paciente(id, nome, cpf, dataNasc, tel, email, end);
            dao.update(p);
            loadPacientes();
            view.clearFields();
            view.showAlert("Sucesso", "Paciente atualizado com sucesso!");
        } catch (NumberFormatException ex) {
            view.showAlert("Erro", "Selecione um paciente válido.");
        } catch (Exception ex) {
            view.showAlert("Erro", "Falha ao atualizar paciente: " + ex.getMessage());
        }
    }

    private void deletePaciente() {
        try {
            int id = Integer.parseInt(view.getIdField().getText());
            dao.delete(id);
            loadPacientes();
            view.clearFields();
            view.showAlert("Sucesso", "Paciente excluído com sucesso!");
        } catch (NumberFormatException ex) {
            view.showAlert("Erro", "Selecione um paciente válido.");
        } catch (Exception ex) {
            view.showAlert("Erro", "Falha ao excluir paciente: " + ex.getMessage());
        }
    }

    private boolean isValidCPF(String cpf) {
        String digits = cpf.replaceAll("\\D", "");
        return digits.matches("\\d{11}");
    }

    private boolean isValidPhone(String phone) {
        String digits = phone.replaceAll("\\D", "");
        return digits.matches("\\d{10,11}");
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
