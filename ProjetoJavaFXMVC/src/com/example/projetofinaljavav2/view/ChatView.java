package com.example.projetofinaljavav2.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.scene.paint.Color;

public class ChatView extends Stage {

    private TextArea chatArea;
    private TextField messageField;
    private Button sendButton;

        public ChatView() {
            // Área de chat
            chatArea = new TextArea();
            chatArea.setEditable(false);
            chatArea.setWrapText(true);
            chatArea.setFont(Font.font("Segoe UI", 14));
            chatArea.setBackground(new Background(new BackgroundFill(
                    Color.WHITE, new CornerRadii(4), Insets.EMPTY
            )));
            chatArea.setPadding(new Insets(10));

            // Campo de mensagem
            messageField = new TextField();
            messageField.setPromptText("Digite sua mensagem...");
            messageField.setFont(Font.font("Segoe UI", 13));
            messageField.setBackground(new Background(new BackgroundFill(
                    Color.WHITE, new CornerRadii(4), Insets.EMPTY
            )));
            messageField.setBorder(new Border(new BorderStroke(
                    Color.web("#DDD"), BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT
            )));
            messageField.setPadding(new Insets(4));
            messageField.setPrefWidth(400);

            // Botão enviar
            sendButton = new Button("Enviar");
            sendButton.setFont(Font.font("Segoe UI", javafx.scene.text.FontWeight.BOLD, 14));
            sendButton.setTextFill(Color.WHITE);
            sendButton.setBackground(new Background(new BackgroundFill(
                    Color.web("#007ACC"), new CornerRadii(4), Insets.EMPTY
            )));
            sendButton.setPadding(new Insets(6, 14, 6, 14));
            sendButton.setDefaultButton(true);

            // Layout do input
            HBox inputBox = new HBox(10, messageField, sendButton);
            inputBox.setPadding(new Insets(10));
            inputBox.setBackground(new Background(new BackgroundFill(
                    Color.web("#EFEFEF"), CornerRadii.EMPTY, Insets.EMPTY
            )));

            // Layout principal
            BorderPane root = new BorderPane();
            root.setCenter(chatArea);
            root.setBottom(inputBox);
            root.setPadding(new Insets(12));
            root.setBackground(new Background(new BackgroundFill(
                    Color.web("#F5F5F5"), CornerRadii.EMPTY, Insets.EMPTY
            )));

            // Cena
            Scene scene = new Scene(root, 600, 400);
            setScene(scene);
            setTitle("Chat com GPT");
        }

    public TextField getMessageField() {
        return messageField;
    }

    public Button getSendButton() {
        return sendButton;
    }

    public void appendMessage(String sender, String message) {
        chatArea.appendText("[" + sender + "] " + message + "\n");
    }

    public void clearMessageField() {
        messageField.clear();
    }
}
