package com.example.projetofinaljavav2.view;

import com.example.projetofinaljavav2.model.Receita;
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

public class ReceitaView extends Stage {

    private TextField idField;
    private TextField nomePacienteField;
    private TextField nomeMedicamentoField;
    private TextField dosagemField;
    private TextArea instrucoesArea;
    private DatePicker dataEmissaoPicker;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private Button clearButton;
    private TableView<Receita> receitaTable;

    public ReceitaView() {
        setTitle("Gerenciar Receitas");

        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(10));
        formPane.setHgap(10);
        formPane.setVgap(10);

        idField = new TextField();
        idField.setPromptText("ID (para busca/edição/exclusão)");
        idField.setDisable(true); // ID será gerado automaticamente ou preenchido na seleção

        nomePacienteField = new TextField();
        nomePacienteField.setPromptText("Nome do Paciente");

        nomeMedicamentoField = new TextField();
        nomeMedicamentoField.setPromptText("Nome do Medicamento");

        dosagemField = new TextField();
        dosagemField.setPromptText("Dosagem");

        instrucoesArea = new TextArea();
        instrucoesArea.setPromptText("Instruções");
        instrucoesArea.setWrapText(true);
        instrucoesArea.setPrefRowCount(4);

        dataEmissaoPicker = new DatePicker();
        dataEmissaoPicker.setPromptText("Data de Emissão");

        formPane.addRow(0, new Label("ID:"), idField);
        formPane.addRow(1, new Label("Paciente:"), nomePacienteField);
        formPane.addRow(2, new Label("Medicamento:"), nomeMedicamentoField);
        formPane.addRow(3, new Label("Dosagem:"), dosagemField);
        formPane.addRow(4, new Label("Instruções:"), instrucoesArea);
        formPane.addRow(5, new Label("Data Emissão:"), dataEmissaoPicker);

        addButton = new Button("Adicionar");
        updateButton = new Button("Atualizar");
        deleteButton = new Button("Excluir");
        clearButton = new Button("Limpar Campos");

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, clearButton);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        receitaTable = new TableView<>();
        TableColumn<Receita, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Receita, String> nomePacienteColumn = new TableColumn<>("Nome Paciente");
        nomePacienteColumn.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));

        TableColumn<Receita, String> nomeMedicamentoColumn = new TableColumn<>("Nome Medicamento");
        nomeMedicamentoColumn.setCellValueFactory(new PropertyValueFactory<>("nomeMedicamento"));

        TableColumn<Receita, String> dosagemColumn = new TableColumn<>("Dosagem");
        dosagemColumn.setCellValueFactory(new PropertyValueFactory<>("dosagem"));

        TableColumn<Receita, String> instrucoesColumn = new TableColumn<>("Instruções");
        instrucoesColumn.setCellValueFactory(new PropertyValueFactory<>("instrucoes"));

        TableColumn<Receita, LocalDate> dataEmissaoColumn = new TableColumn<>("Data Emissão");
        dataEmissaoColumn.setCellValueFactory(new PropertyValueFactory<>("dataEmissao"));
        dataEmissaoColumn.setCellFactory(column -> new TableCell<Receita, LocalDate>() {
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

        receitaTable.getColumns().addAll(idColumn, nomePacienteColumn, nomeMedicamentoColumn, dosagemColumn, instrucoesColumn, dataEmissaoColumn);

        VBox root = new VBox(10, formPane, buttonBox, receitaTable);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 800, 600);
        setScene(scene);

        // Listener para preencher os campos quando uma linha da tabela é selecionada
        receitaTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idField.setText(String.valueOf(newSelection.getId()));
                nomePacienteField.setText(newSelection.getNomePaciente());
                nomeMedicamentoField.setText(newSelection.getNomeMedicamento());
                dosagemField.setText(newSelection.getDosagem());
                instrucoesArea.setText(newSelection.getInstrucoes());
                dataEmissaoPicker.setValue(newSelection.getDataEmissao());
            }
        });
    }

    // Getters para os campos e botões (para o Controller)
    public TextField getIdField() {
        return idField;
    }

    public TextField getNomePacienteField() {
        return nomePacienteField;
    }

    public TextField getNomeMedicamentoField() {
        return nomeMedicamentoField;
    }

    public TextField getDosagemField() {
        return dosagemField;
    }

    public TextArea getInstrucoesArea() {
        return instrucoesArea;
    }

    public DatePicker getDataEmissaoPicker() {
        return dataEmissaoPicker;
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

    public TableView<Receita> getReceitaTable() {
        return receitaTable;
    }

    public void clearFields() {
        idField.clear();
        nomePacienteField.clear();
        nomeMedicamentoField.clear();
        dosagemField.clear();
        instrucoesArea.clear();
        dataEmissaoPicker.setValue(null);
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

