package com.example.projetofinaljavav2.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ChatView extends Stage {

    private TextArea chatArea;
    private TextField messageField;
    private Button sendButton;

    public ChatView() {
        setTitle("Chat com GPT");

        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        chatArea.setPromptText("Inicie a conversa...");

        messageField = new TextField();
        messageField.setPromptText("Digite sua mensagem...");
        messageField.setPrefWidth(400);

        sendButton = new Button("Enviar");

        HBox inputArea = new HBox(10, messageField, sendButton);
        inputArea.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(chatArea);
        root.setBottom(inputArea);

        Scene scene = new Scene(root, 600, 400);
        setScene(scene);
    }

    public TextArea getChatArea() {
        return chatArea;
    }

    public TextField getMessageField() {
        return messageField;
    }

    public Button getSendButton() {
        return sendButton;
    }

    public void appendMessage(String sender, String message) {
        chatArea.appendText(sender + ": " + message + "\n");
    }

    public void clearMessageField() {
        messageField.clear();
    }
}

