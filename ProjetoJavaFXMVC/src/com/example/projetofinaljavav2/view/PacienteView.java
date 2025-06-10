package com.example.projetofinaljavav2.view;

import com.example.projetofinaljavav2.model.Paciente;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PacienteView extends Stage {

    private TextField idField;
    private TextField nomeField;
    private TextField cpfField;
    private DatePicker dataNascimentoPicker;
    private TextField telefoneField;
    private TextField emailField;
    private TextField enderecoField;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private Button clearButton;
    private TableView<Paciente> pacienteTable;

    public PacienteView() {
        setTitle("Gerenciar Pacientes");

        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(10));
        formPane.setHgap(10);
        formPane.setVgap(10);

        idField = new TextField();
        idField.setPromptText("ID (para busca/edição/exclusão)");
        idField.setDisable(true); // ID será gerado automaticamente ou preenchido na seleção

        nomeField = new TextField();
        nomeField.setPromptText("Nome");

        cpfField = new TextField();
        cpfField.setPromptText("CPF");

        dataNascimentoPicker = new DatePicker();
        dataNascimentoPicker.setPromptText("Data de Nascimento");

        telefoneField = new TextField();
        telefoneField.setPromptText("Telefone");

        emailField = new TextField();
        emailField.setPromptText("Email");

        enderecoField = new TextField();
        enderecoField.setPromptText("Endereço");

        formPane.addRow(0, new Label("ID:"), idField);
        formPane.addRow(1, new Label("Nome:"), nomeField);
        formPane.addRow(2, new Label("CPF:"), cpfField);
        formPane.addRow(3, new Label("Data Nasc.:"), dataNascimentoPicker);
        formPane.addRow(4, new Label("Telefone:"), telefoneField);
        formPane.addRow(5, new Label("Email:"), emailField);
        formPane.addRow(6, new Label("Endereço:"), enderecoField);

        addButton = new Button("Adicionar");
        updateButton = new Button("Atualizar");
        deleteButton = new Button("Excluir");
        clearButton = new Button("Limpar Campos");

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, clearButton);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        pacienteTable = new TableView<>();
        TableColumn<Paciente, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Paciente, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Paciente, String> cpfColumn = new TableColumn<>("CPF");
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        TableColumn<Paciente, LocalDate> dataNascimentoColumn = new TableColumn<>("Data Nasc.");
        dataNascimentoColumn.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        dataNascimentoColumn.setCellFactory(column -> new TableCell<Paciente, LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });

        TableColumn<Paciente, String> telefoneColumn = new TableColumn<>("Telefone");
        telefoneColumn.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        TableColumn<Paciente, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Paciente, String> enderecoColumn = new TableColumn<>("Endereço");
        enderecoColumn.setCellValueFactory(new PropertyValueFactory<>("endereco"));

        pacienteTable.getColumns().addAll(idColumn, nomeColumn, cpfColumn, dataNascimentoColumn, telefoneColumn, emailColumn, enderecoColumn);

        VBox root = new VBox(10, formPane, buttonBox, pacienteTable);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 800, 600);
        setScene(scene);

        // Listener para preencher os campos quando uma linha da tabela é selecionada
        pacienteTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idField.setText(String.valueOf(newSelection.getId()));
                nomeField.setText(newSelection.getNome());
                cpfField.setText(newSelection.getCpf());
                dataNascimentoPicker.setValue(newSelection.getDataNascimento());
                telefoneField.setText(newSelection.getTelefone());
                emailField.setText(newSelection.getEmail());
                enderecoField.setText(newSelection.getEndereco());
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

    public TextField getCpfField() {
        return cpfField;
    }

    public DatePicker getDataNascimentoPicker() {
        return dataNascimentoPicker;
    }

    public TextField getTelefoneField() {
        return telefoneField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getEnderecoField() {
        return enderecoField;
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

    public TableView<Paciente> getPacienteTable() {
        return pacienteTable;
    }

    public void clearFields() {
        idField.clear();
        nomeField.clear();
        cpfField.clear();
        dataNascimentoPicker.setValue(null);
        telefoneField.clear();
        emailField.clear();
        enderecoField.clear();
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

