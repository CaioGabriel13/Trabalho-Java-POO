package com.example.projetofinaljavav2.controller;

import com.example.projetofinaljavav2.view.ChatView;

public class ChatController {
    private ChatView view;

    public ChatController(ChatView view) {
        this.view = view;
        this.view.getSendButton().setOnAction(e -> sendMessage());
        this.view.getMessageField().setOnAction(e -> sendMessage()); // Enviar mensagem ao pressionar Enter
    }

    private void sendMessage() {
        String userMessage = view.getMessageField().getText();
        if (userMessage != null && !userMessage.trim().isEmpty()) {
            view.appendMessage("Você", userMessage);
            view.clearMessageField();
            // Simulação de resposta do GPT
            String gptResponse = getGptResponse(userMessage);
            view.appendMessage("GPT", gptResponse);
        }
    }

    private String getGptResponse(String userMessage) {
        // Lógica de simulação de resposta do GPT
        if (userMessage.toLowerCase().contains("olá") || userMessage.toLowerCase().contains("oi")) {
            return "Olá! Como posso ajudar você hoje?";
        } else if (userMessage.toLowerCase().contains("tudo bem")) {
            return "Estou bem, obrigado por perguntar! E você?";
        } else if (userMessage.toLowerCase().contains("pacientes")) {
            return "Posso te ajudar a gerenciar informações de pacientes. O que você gostaria de fazer?";
        } else if (userMessage.toLowerCase().contains("psicólogos")) {
            return "Informações sobre psicólogos podem ser gerenciadas. Qual sua dúvida?";
        } else if (userMessage.toLowerCase().contains("anúncios")) {
            return "Anúncios de psicólogos podem ser criados e visualizados. Em que posso auxiliar?";
        } else if (userMessage.toLowerCase().contains("guias")) {
            return "Os guias para psicólogos contêm informações úteis. Você quer criar um novo guia ou consultar um existente?";
        } else if (userMessage.toLowerCase().contains("receitas")) {
            return "Posso te ajudar com o registro de receitas. O que você precisa?";
        } else if (userMessage.toLowerCase().contains("obrigado") || userMessage.toLowerCase().contains("valeu")) {
            return "De nada! Se precisar de mais alguma coisa, é só chamar.";
        } else {
            return "Desculpe, não entendi. Poderia reformular sua pergunta?";
        }
    }
}

