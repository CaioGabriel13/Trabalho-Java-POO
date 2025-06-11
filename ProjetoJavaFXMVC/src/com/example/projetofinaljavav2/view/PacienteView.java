package com.example.projetofinaljavav2.view;

import com.example.projetofinaljavav2.model.Paciente;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PacienteView extends Stage {

    private TableView<Paciente> pacienteTable;
    private TextField idField;
    private TextField nomeField;
    private TextField cpfField;
    private DatePicker dataNascimentoPicker;
    private TextField telefoneField;
    private TextField emailField;
    private TextArea enderecoArea;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private Button clearButton;

    public PacienteView() {
        setTitle("Gerenciador de Pacientes");

        // --- Tabela de pacientes (apenas ID e Nome) ---
        pacienteTable = new TableView<>();
        pacienteTable.setPrefWidth(300);
        pacienteTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Paciente, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Paciente, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        pacienteTable.getColumns().addAll(idColumn, nomeColumn);

        // Evento de duplo-clique para ver detalhes
        pacienteTable.setRowFactory(tv -> {
            TableRow<Paciente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Paciente p = row.getItem();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Detalhes do Paciente");
                    alert.setHeaderText(p.getNome());
                    String content = String.format(
                            "ID: %d\n" +
                                    "CPF: %s\n" +
                                    "Nascimento: %s\n" +
                                    "Telefone: %s\n" +
                                    "Email: %s\n" +
                                    "Endereço: %s",
                            p.getId(),
                            p.getCpf(),
                            p.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            p.getTelefone(),
                            p.getEmail(),
                            p.getEndereco()
                    );
                    alert.setContentText(content);
                    alert.showAndWait();
                }
            });
            return row;
        });

        // --- Formulário de detalhes ---
        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(10));
        formPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(4), Insets.EMPTY)));

        idField = new TextField();
        idField.setPromptText("ID");
        idField.setDisable(true);
        idField.setStyle(fieldStyle());

        nomeField = new TextField();
        nomeField.setPromptText("Nome");
        nomeField.setStyle(fieldStyle());

        cpfField = new TextField();
        cpfField.setPromptText("CPF");
        cpfField.setStyle(fieldStyle());

        dataNascimentoPicker = new DatePicker();
        dataNascimentoPicker.setPromptText("Nascimento");
        dataNascimentoPicker.setStyle(fieldStyle());

        telefoneField = new TextField();
        telefoneField.setPromptText("Telefone");
        telefoneField.setStyle(fieldStyle());

        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle(fieldStyle());

        enderecoArea = new TextArea();
        enderecoArea.setPromptText("Endereço");
        enderecoArea.setWrapText(true);
        enderecoArea.setPrefRowCount(3);
        enderecoArea.setStyle(fieldStyle());

        formPane.addRow(0, new Label("ID:"), idField);
        formPane.addRow(1, new Label("Nome:"), nomeField);
        formPane.addRow(2, new Label("CPF:"), cpfField);
        formPane.addRow(3, new Label("Nascimento:"), dataNascimentoPicker);
        formPane.addRow(4, new Label("Telefone:"), telefoneField);
        formPane.addRow(5, new Label("Email:"), emailField);
        formPane.addRow(6, new Label("Endereço:"), enderecoArea);

        // --- Botões de ação ---
        addButton = new Button("Adicionar");
        updateButton = new Button("Atualizar");
        deleteButton = new Button("Excluir");
        clearButton = new Button("Limpar");
        styleButton(addButton, "#007ACC");
        styleButton(updateButton, "#007ACC");
        styleButton(deleteButton, "#E53935");
        styleButton(clearButton, "#777777");

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, clearButton);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setBackground(new Background(new BackgroundFill(Color.web("#EFEFEF"), new CornerRadii(4), Insets.EMPTY)));

        // --- Layout principal ---
        BorderPane root = new BorderPane();
        root.setLeft(pacienteTable);
        root.setCenter(formPane);
        root.setBottom(buttonBox);
        root.setPadding(new Insets(12));
        root.setBackground(new Background(new BackgroundFill(Color.web("#F5F5F5"), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(root, 900, 600);
        setScene(scene);
    }

    // Estilo para campos
    private String fieldStyle() {
        return "-fx-background-color: white; " +
                "-fx-border-color: #DDD; " +
                "-fx-border-radius: 4; " +
                "-fx-background-radius: 4; " +
                "-fx-padding: 4;";
    }

    // Estilo para botões
    private void styleButton(Button btn, String color) {
        btn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        btn.setTextFill(Color.WHITE);
        btn.setBackground(new Background(new BackgroundFill(Color.web(color), new CornerRadii(4), Insets.EMPTY)));
        btn.setPadding(new Insets(6, 12, 6, 12));
    }

    // Getters para o controller
    public TableView<Paciente> getPacienteTable() { return pacienteTable; }
    public TextField getIdField() { return idField; }
    public TextField getNomeField() { return nomeField; }
    public TextField getCpfField() { return cpfField; }
    public DatePicker getDataNascimentoPicker() { return dataNascimentoPicker; }
    public TextField getTelefoneField() { return telefoneField; }
    public TextField getEmailField() { return emailField; }
    public TextArea getEnderecoArea() { return enderecoArea; }
    public Button getAddButton() { return addButton; }
    public Button getUpdateButton() { return updateButton; }
    public Button getDeleteButton() { return deleteButton; }
    public Button getClearButton() { return clearButton; }

    // Métodos auxiliares
    public void clearFields() {
        idField.clear(); nomeField.clear(); cpfField.clear(); dataNascimentoPicker.setValue(null);
        telefoneField.clear(); emailField.clear(); enderecoArea.clear();
        pacienteTable.getSelectionModel().clearSelection();
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
