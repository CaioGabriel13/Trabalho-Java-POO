package com.example.projetofinaljavav2.view;

import com.example.projetofinaljavav2.model.Anuncio;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private FlowPane cardContainer;
    private VBox selectedCard;

    public AnuncioView() {
        setTitle("Gerenciar Anúncios");

        // Formulário de dados
        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(10));
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setBackground(new Background(
                new BackgroundFill(Color.WHITE, new CornerRadii(4), Insets.EMPTY)
        ));

        idField = new TextField();
        idField.setPromptText("ID");
        idField.setDisable(true);
        idField.setStyle(fieldStyle());

        tituloField = new TextField();
        tituloField.setPromptText("Título");
        tituloField.setStyle(fieldStyle());

        descricaoArea = new TextArea();
        descricaoArea.setPromptText("Descrição");
        descricaoArea.setWrapText(true);
        descricaoArea.setPrefRowCount(3);
        descricaoArea.setStyle(fieldStyle());

        dataPublicacaoPicker = new DatePicker();
        dataPublicacaoPicker.setPromptText("Data");
        dataPublicacaoPicker.setStyle(fieldStyle());

        idPsicologoField = new TextField();
        idPsicologoField.setPromptText("ID Psicólogo");
        idPsicologoField.setStyle(fieldStyle());

        formPane.addRow(0, new Label("ID:"), idField);
        formPane.addRow(1, new Label("Título:"), tituloField);
        formPane.addRow(2, new Label("Descrição:"), descricaoArea);
        formPane.addRow(3, new Label("Data:"), dataPublicacaoPicker);
        formPane.addRow(4, new Label("ID Psicólogo:"), idPsicologoField);

        // Botões de ação
        addButton = new Button("Adicionar");
        updateButton = new Button("Atualizar");
        deleteButton = new Button("Excluir");
        clearButton = new Button("Limpar");
        styleButton(addButton, "#007ACC");
        styleButton(updateButton, "#007ACC");
        styleButton(deleteButton, "#E53935"); // vermelho para excluir
        styleButton(clearButton, "#777777");

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, clearButton);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));
        buttonBox.setBackground(new Background(
                new BackgroundFill(Color.web("#EFEFEF"), new CornerRadii(4), Insets.EMPTY)
        ));

        // Container de cartões
        cardContainer = new FlowPane();
        cardContainer.setHgap(15);
        cardContainer.setVgap(15);
        cardContainer.setPadding(new Insets(10));
        cardContainer.setBackground(new Background(
                new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)
        ));

        ScrollPane scrollPane = new ScrollPane(cardContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setBackground(new Background(
                new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)
        ));

        // Layout principal
        VBox root = new VBox(10, formPane, buttonBox, scrollPane);
        root.setPadding(new Insets(12));
        root.setBackground(new Background(
                new BackgroundFill(Color.web("#F5F5F5"), CornerRadii.EMPTY, Insets.EMPTY)
        ));

        Scene scene = new Scene(root, 800, 600);
        setScene(scene);
    }

    /**
     * Exibe os anúncios em formato de card e configura seleção.
     */
    public void setAnuncios(List<Anuncio> anuncios) {
        cardContainer.getChildren().clear();
        selectedCard = null;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Anuncio a : anuncios) {
            VBox card = new VBox(8);
            card.setPadding(new Insets(12));
            card.setBackground(new Background(
                    new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)
            ));
            card.setBorder(new Border(
                    new BorderStroke(Color.web("#DDD"), BorderStrokeStyle.SOLID,
                            new CornerRadii(8), new BorderWidths(1))
            ));

            Label title = new Label(a.getTitulo());
            title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));

            Label desc = new Label(a.getDescricao());
            desc.setWrapText(true);
            desc.setFont(Font.font("Segoe UI", 14));

            Label date = new Label(fmt.format(a.getDataPublicacao()));
            date.setFont(Font.font("Segoe UI", 12));
            date.setTextFill(Color.GRAY);

            card.getChildren().addAll(title, desc, date);

            // Ao clicar, seleciona este card e popula o formulário
            card.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (selectedCard != null) {
                    // reset estilo anterior
                    selectedCard.setBorder(new Border(
                            new BorderStroke(Color.web("#DDD"), BorderStrokeStyle.SOLID,
                                    new CornerRadii(8), new BorderWidths(1))
                    ));
                }
                selectedCard = card;
                // destaque da borda
                card.setBorder(new Border(
                        new BorderStroke(Color.web("#007ACC"), BorderStrokeStyle.SOLID,
                                new CornerRadii(8), new BorderWidths(2))
                ));
                // popula formulário
                populateForm(a);
            });

            cardContainer.getChildren().add(card);
        }
    }

    // Popula os campos do formulário com os dados do anúncio selecionado
    private void populateForm(Anuncio a) {
        idField.setText(String.valueOf(a.getId()));
        tituloField.setText(a.getTitulo());
        descricaoArea.setText(a.getDescricao());
        dataPublicacaoPicker.setValue(a.getDataPublicacao());
        idPsicologoField.setText(String.valueOf(a.getIdPsicologo()));
    }

    // estilos auxiliares
    private String fieldStyle() {
        return "-fx-background-color: white; "
                + "-fx-border-color: #DDD; "
                + "-fx-border-radius: 4; "
                + "-fx-background-radius: 4; "
                + "-fx-padding: 4;";
    }

    private void styleButton(Button btn, String color) {
        btn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        btn.setTextFill(Color.WHITE);
        btn.setBackground(new Background(
                new BackgroundFill(Color.web(color), new CornerRadii(4), Insets.EMPTY)
        ));
        btn.setPadding(new Insets(6, 12, 6, 12));
    }

    // Getters para o controller
    public TextField getIdField() { return idField; }
    public TextField getTituloField() { return tituloField; }
    public TextArea getDescricaoArea() { return descricaoArea; }
    public DatePicker getDataPublicacaoPicker() { return dataPublicacaoPicker; }
    public TextField getIdPsicologoField() { return idPsicologoField; }
    public Button getAddButton() { return addButton; }
    public Button getUpdateButton() { return updateButton; }
    public Button getDeleteButton() { return deleteButton; }
    public Button getClearButton() { return clearButton; }
    public FlowPane getCardContainer() { return cardContainer; }

    public void clearFields() {
    }

    public void showAlert(String erro, String s) {
    }
}
