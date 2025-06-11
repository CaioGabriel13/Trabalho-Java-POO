package com.example.projetofinaljavav2.view;

import com.example.projetofinaljavav2.model.Psicologo;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PsicologoView extends Stage {

    private TableView<Psicologo> psicologoTable;
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

    public PsicologoView() {
        setTitle("Gerenciador de Psicólogos");

        // --- Tabela de psicólogos (ID, Nome, Especialidade) ---
        psicologoTable = new TableView<>();
        psicologoTable.setPrefWidth(350);
        psicologoTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Psicologo, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Psicologo, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Psicologo, String> espCol = new TableColumn<>("Especialidade");
        espCol.setCellValueFactory(new PropertyValueFactory<>("especialidade"));

        psicologoTable.getColumns().addAll(idCol, nomeCol, espCol);

        // Evento duplo-clique para exibir detalhes completos
        psicologoTable.setRowFactory(tv -> {
            TableRow<Psicologo> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !row.isEmpty()) {
                    Psicologo p = row.getItem();
                    Alert det = new Alert(Alert.AlertType.INFORMATION);
                    det.setTitle("Detalhes do Psicólogo");
                    det.setHeaderText(p.getNome());
                    String content = String.format(
                            "ID: %d\nNome: %s\nCRP: %s\nEspecialidade: %s\nTelefone: %s\nEmail: %s",
                            p.getId(), p.getNome(), p.getCrp(), p.getEspecialidade(), p.getTelefone(), p.getEmail()
                    );
                    det.setContentText(content);
                    det.showAndWait();
                }
            });
            return row;
        });

        // --- Formulário de cadastro/edição ---
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));
        form.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(4), Insets.EMPTY)));

        idField = new TextField(); idField.setPromptText("ID"); idField.setDisable(true); idField.setStyle(fieldStyle());
        nomeField = new TextField(); nomeField.setPromptText("Nome"); nomeField.setStyle(fieldStyle());
        crpField = new TextField(); crpField.setPromptText("CRP"); crpField.setStyle(fieldStyle());
        especialidadeField = new TextField(); especialidadeField.setPromptText("Especialidade"); especialidadeField.setStyle(fieldStyle());
        telefoneField = new TextField(); telefoneField.setPromptText("Telefone"); telefoneField.setStyle(fieldStyle());
        emailField = new TextField(); emailField.setPromptText("Email"); emailField.setStyle(fieldStyle());

        form.addRow(0, new Label("ID:"), idField);
        form.addRow(1, new Label("Nome:"), nomeField);
        form.addRow(2, new Label("CRP:"), crpField);
        form.addRow(3, new Label("Especialidade:"), especialidadeField);
        form.addRow(4, new Label("Telefone:"), telefoneField);
        form.addRow(5, new Label("Email:"), emailField);

        // --- Botões de ação ---
        addButton = new Button("Adicionar");
        updateButton = new Button("Atualizar");
        deleteButton = new Button("Excluir");
        clearButton = new Button("Limpar");
        styleButton(addButton, "#00695C"); // verde-escuro para psicólogos
        styleButton(updateButton, "#00695C");
        styleButton(deleteButton, "#D32F2F");
        styleButton(clearButton, "#777777");

        HBox actions = new HBox(10, addButton, updateButton, deleteButton, clearButton);
        actions.setPadding(new Insets(10));
        actions.setBackground(new Background(new BackgroundFill(Color.web("#ECEFF1"), new CornerRadii(4), Insets.EMPTY)));

        // --- Layout principal ---
        BorderPane root = new BorderPane();
        root.setLeft(psicologoTable);
        root.setCenter(form);
        root.setBottom(actions);
        root.setPadding(new Insets(12));
        root.setBackground(new Background(new BackgroundFill(Color.web("#F0F4C3"), CornerRadii.EMPTY, Insets.EMPTY)));
        // cor de fundo suave para psicólogos

        Scene scene = new Scene(root, 950, 600);
        setScene(scene);
    }

    private String fieldStyle() {
        return "-fx-background-color: white; -fx-border-color: #DDD; -fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 4;";
    }

    private void styleButton(Button btn, String color) {
        btn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        btn.setTextFill(Color.WHITE);
        btn.setBackground(new Background(new BackgroundFill(Color.web(color), new CornerRadii(4), Insets.EMPTY)));
        btn.setPadding(new Insets(6, 12, 6, 12));
    }

    // Getters para controller
    public TableView<Psicologo> getPsicologoTable() { return psicologoTable; }
    public TextField getIdField() { return idField; }
    public TextField getNomeField() { return nomeField; }
    public TextField getCrpField() { return crpField; }
    public TextField getEspecialidadeField() { return especialidadeField; }
    public TextField getTelefoneField() { return telefoneField; }
    public TextField getEmailField() { return emailField; }
    public Button getAddButton() { return addButton; }
    public Button getUpdateButton() { return updateButton; }
    public Button getDeleteButton() { return deleteButton; }
    public Button getClearButton() { return clearButton; }

    public void clearFields() {
        idField.clear(); nomeField.clear(); crpField.clear(); especialidadeField.clear(); telefoneField.clear(); emailField.clear();
        psicologoTable.getSelectionModel().clearSelection();
    }

    public void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
