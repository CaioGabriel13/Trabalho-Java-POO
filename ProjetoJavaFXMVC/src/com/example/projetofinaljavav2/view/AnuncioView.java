package com.example.projetofinaljavav2.view;

import com.example.projetofinaljavav2.model.Anuncio;
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

public class AnuncioView extends Stage {

    private TextField idField;
    private TextField tituloField;
    private TextArea descricaoArea;
    private DatePicker dataPublicacaoPicker;
    private TextField idPsicologoField;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private Button clearButton;
    private TableView<Anuncio> anuncioTable;

    public AnuncioView() {
        setTitle("Gerenciar Anúncios");

        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(10));
        formPane.setHgap(10);
        formPane.setVgap(10);

        idField = new TextField();
        idField.setPromptText("ID (para busca/edição/exclusão)");
        idField.setDisable(true); // ID será gerado automaticamente ou preenchido na seleção

        tituloField = new TextField();
        tituloField.setPromptText("Título");

        descricaoArea = new TextArea();
        descricaoArea.setPromptText("Descrição");
        descricaoArea.setWrapText(true);
        descricaoArea.setPrefRowCount(4);

        dataPublicacaoPicker = new DatePicker();
        dataPublicacaoPicker.setPromptText("Data de Publicação");

        idPsicologoField = new TextField();
        idPsicologoField.setPromptText("ID do Psicólogo");

        formPane.addRow(0, new Label("ID:"), idField);
        formPane.addRow(1, new Label("Título:"), tituloField);
        formPane.addRow(2, new Label("Descrição:"), descricaoArea);
        formPane.addRow(3, new Label("Data Publicação:"), dataPublicacaoPicker);
        formPane.addRow(4, new Label("ID Psicólogo:"), idPsicologoField);

        addButton = new Button("Adicionar");
        updateButton = new Button("Atualizar");
        deleteButton = new Button("Excluir");
        clearButton = new Button("Limpar Campos");

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, clearButton);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        anuncioTable = new TableView<>();
        TableColumn<Anuncio, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Anuncio, String> tituloColumn = new TableColumn<>("Título");
        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Anuncio, String> descricaoColumn = new TableColumn<>("Descrição");
        descricaoColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<Anuncio, LocalDate> dataPublicacaoColumn = new TableColumn<>("Data Publicação");
        dataPublicacaoColumn.setCellValueFactory(new PropertyValueFactory<>("dataPublicacao"));
        dataPublicacaoColumn.setCellFactory(column -> new TableCell<Anuncio, LocalDate>() {
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

        TableColumn<Anuncio, Integer> idPsicologoColumn = new TableColumn<>("ID Psicólogo");
        idPsicologoColumn.setCellValueFactory(new PropertyValueFactory<>("idPsicologo"));

        anuncioTable.getColumns().addAll(idColumn, tituloColumn, descricaoColumn, dataPublicacaoColumn, idPsicologoColumn);

        VBox root = new VBox(10, formPane, buttonBox, anuncioTable);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 800, 600);
        setScene(scene);

        // Listener para preencher os campos quando uma linha da tabela é selecionada
        anuncioTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idField.setText(String.valueOf(newSelection.getId()));
                tituloField.setText(newSelection.getTitulo());
                descricaoArea.setText(newSelection.getDescricao());
                dataPublicacaoPicker.setValue(newSelection.getDataPublicacao());
                idPsicologoField.setText(String.valueOf(newSelection.getIdPsicologo()));
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

    public TextArea getDescricaoArea() {
        return descricaoArea;
    }

    public DatePicker getDataPublicacaoPicker() {
        return dataPublicacaoPicker;
    }

    public TextField getIdPsicologoField() {
        return idPsicologoField;
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

    public TableView<Anuncio> getAnuncioTable() {
        return anuncioTable;
    }

    public void clearFields() {
        idField.clear();
        tituloField.clear();
        descricaoArea.clear();
        dataPublicacaoPicker.setValue(null);
        idPsicologoField.clear();
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

