package com.example.projetofinaljavav2.view;

import com.example.projetofinaljavav2.model.Guia;
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

public class GuiaView extends Stage {

    private TextField idField;
    private TextField tituloField;
    private TextArea conteudoArea;
    private DatePicker dataCriacaoPicker;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private Button clearButton;
    private TableView<Guia> guiaTable;

    public GuiaView() {
        setTitle("Gerenciar Guias para Psicólogos");

        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(10));
        formPane.setHgap(10);
        formPane.setVgap(10);

        idField = new TextField();
        idField.setPromptText("ID (para busca/edição/exclusão)");
        idField.setDisable(true); // ID será gerado automaticamente ou preenchido na seleção

        tituloField = new TextField();
        tituloField.setPromptText("Título");

        conteudoArea = new TextArea();
        conteudoArea.setPromptText("Conteúdo");
        conteudoArea.setWrapText(true);
        conteudoArea.setPrefRowCount(8);

        dataCriacaoPicker = new DatePicker();
        dataCriacaoPicker.setPromptText("Data de Criação");

        formPane.addRow(0, new Label("ID:"), idField);
        formPane.addRow(1, new Label("Título:"), tituloField);
        formPane.addRow(2, new Label("Conteúdo:"), conteudoArea);
        formPane.addRow(3, new Label("Data Criação:"), dataCriacaoPicker);

        addButton = new Button("Adicionar");
        updateButton = new Button("Atualizar");
        deleteButton = new Button("Excluir");
        clearButton = new Button("Limpar Campos");

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, clearButton);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        guiaTable = new TableView<>();
        TableColumn<Guia, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Guia, String> tituloColumn = new TableColumn<>("Título");
        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Guia, String> conteudoColumn = new TableColumn<>("Conteúdo");
        conteudoColumn.setCellValueFactory(new PropertyValueFactory<>("conteudo"));

        TableColumn<Guia, LocalDate> dataCriacaoColumn = new TableColumn<>("Data Criação");
        dataCriacaoColumn.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));
        dataCriacaoColumn.setCellFactory(column -> new TableCell<Guia, LocalDate>() {
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

        guiaTable.getColumns().addAll(idColumn, tituloColumn, conteudoColumn, dataCriacaoColumn);

        VBox root = new VBox(10, formPane, buttonBox, guiaTable);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 800, 600);
        setScene(scene);

        // Listener para preencher os campos quando uma linha da tabela é selecionada
        guiaTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idField.setText(String.valueOf(newSelection.getId()));
                tituloField.setText(newSelection.getTitulo());
                conteudoArea.setText(newSelection.getConteudo());
                dataCriacaoPicker.setValue(newSelection.getDataCriacao());
            }
        });
    }

    // Getters para os campos e botões (para o Controller)
    public TextField getIdField() {
        return idField;
    }

    public TextField getTituloField() {
        return tituloField;
    }

    public TextArea getConteudoArea() {
        return conteudoArea;
    }

    public DatePicker getDataCriacaoPicker() {
        return dataCriacaoPicker;
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

    public TableView<Guia> getGuiaTable() {
        return guiaTable;
    }

    public void clearFields() {
        idField.clear();
        tituloField.clear();
        conteudoArea.clear();
        dataCriacaoPicker.setValue(null);
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

