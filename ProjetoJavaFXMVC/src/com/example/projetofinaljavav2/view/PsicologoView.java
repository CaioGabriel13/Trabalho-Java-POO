package com.example.projetofinaljavav2.view;

import com.example.projetofinaljavav2.model.Psicologo;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PsicologoView extends Stage {

    private TextField idField;
    private TextField nomeField;
    private TextField crpField;
    private TextField especialidadeField;
    private TextField telefoneField;
    private TextField emailField;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private Button clearButton;
    private TableView<Psicologo> psicologoTable;

    public PsicologoView() {
        setTitle("Gerenciar Psicólogos");

        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(10));
        formPane.setHgap(10);
        formPane.setVgap(10);

        idField = new TextField();
        idField.setPromptText("ID (para busca/edição/exclusão)");
        idField.setDisable(true); // ID será gerado automaticamente ou preenchido na seleção

        nomeField = new TextField();
        nomeField.setPromptText("Nome");

        crpField = new TextField();
        crpField.setPromptText("CRP");

        especialidadeField = new TextField();
        especialidadeField.setPromptText("Especialidade");

        telefoneField = new TextField();
        telefoneField.setPromptText("Telefone");

        emailField = new TextField();
        emailField.setPromptText("Email");

        formPane.addRow(0, new Label("ID:"), idField);
        formPane.addRow(1, new Label("Nome:"), nomeField);
        formPane.addRow(2, new Label("CRP:"), crpField);
        formPane.addRow(3, new Label("Especialidade:"), especialidadeField);
        formPane.addRow(4, new Label("Telefone:"), telefoneField);
        formPane.addRow(5, new Label("Email:"), emailField);

        addButton = new Button("Adicionar");
        updateButton = new Button("Atualizar");
        deleteButton = new Button("Excluir");
        clearButton = new Button("Limpar Campos");

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, clearButton);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        psicologoTable = new TableView<>();
        TableColumn<Psicologo, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Psicologo, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Psicologo, String> crpColumn = new TableColumn<>("CRP");
        crpColumn.setCellValueFactory(new PropertyValueFactory<>("crp"));

        TableColumn<Psicologo, String> especialidadeColumn = new TableColumn<>("Especialidade");
        especialidadeColumn.setCellValueFactory(new PropertyValueFactory<>("especialidade"));

        TableColumn<Psicologo, String> telefoneColumn = new TableColumn<>("Telefone");
        telefoneColumn.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        TableColumn<Psicologo, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        psicologoTable.getColumns().addAll(idColumn, nomeColumn, crpColumn, especialidadeColumn, telefoneColumn, emailColumn);

        VBox root = new VBox(10, formPane, buttonBox, psicologoTable);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 800, 600);
        setScene(scene);

        // Listener para preencher os campos quando uma linha da tabela é selecionada
        psicologoTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idField.setText(String.valueOf(newSelection.getId()));
                nomeField.setText(newSelection.getNome());
                crpField.setText(newSelection.getCrp());
                especialidadeField.setText(newSelection.getEspecialidade());
                telefoneField.setText(newSelection.getTelefone());
                emailField.setText(newSelection.getEmail());
            }
        });
    }

    // Getters para os campos e botões (para o Controller)
    public TextField getIdField() {
        return idField;
    }

    public TextField getNomeField() {
        return nomeField;
    }

    public TextField getCrpField() {
        return crpField;
    }

    public TextField getEspecialidadeField() {
        return especialidadeField;
    }

    public TextField getTelefoneField() {
        return telefoneField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public TableView<Psicologo> getPsicologoTable() {
        return psicologoTable;
    }

    public void clearFields() {
        idField.clear();
        nomeField.clear();
        crpField.clear();
        especialidadeField.clear();
        telefoneField.clear();
        emailField.clear();
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

