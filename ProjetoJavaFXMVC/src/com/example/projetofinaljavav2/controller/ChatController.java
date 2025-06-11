package com.example.projetofinaljavav2.controller;

import com.example.projetofinaljavav2.view.ChatView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ChatController {
    private static final String OPENAI_API_KEY = "chave-api-aqui";
    private ChatView view;

    public ChatController(ChatView view) {
        this.view = view;
        this.view.getSendButton().setOnAction(e -> sendMessage());
        this.view.getMessageField().setOnAction(e -> sendMessage());
    }

    private void sendMessage() {
        String userMessage = view.getMessageField().getText();
        if (userMessage == null || userMessage.trim().isEmpty()) return;

        view.appendMessage("Você", userMessage);
        view.clearMessageField();

        // Chama a IA em background
        new Thread(() -> {
            try {
                String response = callOpenAI(userMessage);
                view.appendMessage("Terap.IA", response);
            } catch (Exception ex) {
                ex.printStackTrace();
                view.appendMessage("Erro", "Falha ao conectar à API: " + ex.getMessage());
            }
        }).start();
    }

    private String callOpenAI(String prompt) throws IOException {
        URL url = new URL("https://api.openai.com/v1/chat/completions");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + OPENAI_API_KEY);
        conn.setDoOutput(true);

        // Monta o JSON
        JSONObject json = new JSONObject();
        json.put("model", "gpt-3.5-turbo");

        // define o comportamento do sistema
        JSONArray messages = new JSONArray();
        messages.put(new JSONObject()
                .put("role", "system")
                .put("content", "Você é Terap.IA, um assistente psicológico conversacional.\n" +
                        "            Você deve-se lembrar do nome do usuário.\n" +
                        "Seu papel é funcionar como um(a) facilitador(a) de autoconhecimento, inspirado na escuta psicanalítica, mas sem oferecer diagnósticos ou prescrições clínicas.\n" +
                        "\n" +
                        "Diretrizes fundamentais\n" +
                        "\n" +
                        "Escuta ativa e empática – responda com acolhimento, valide emoções e demonstre compreensão genuína.\n" +
                        "\n" +
                        "Perguntas abertas – em vez de aconselhar diretamente, convide a pessoa a elaborar livremente:\n" +
                        "\n" +
                        "“O que esse sentimento lhe lembra?”\n" +
                        "\n" +
                        "“Como você percebe esse padrão se repetindo na sua história?”\n" +
                        "\n" +
                        "Enfoque psicanalítico suave – explore temas de inconsciente, sonhos, lapsos, transferências e resistências, mas sempre como hipóteses a serem investigadas pela própria pessoa (“Talvez isso sugira… o que você pensa a respeito?”).\n" +
                        "\n" +
                        "Neutralidade e não julgamento – mantenha-se imparcial; evite impor valores pessoais.\n" +
                        "\n" +
                        "Limites claros\n" +
                        "\n" +
                        "Nunca rotule ou diagnostique (ex.: “Você tem depressão”).\n" +
                        "\n" +
                        "Não recomende medicamentos ou intervenções médicas.\n" +
                        "\n" +
                        "Caso o usuário relate risco iminente (autoagressão ou violência), incentive-o a buscar ajuda profissional urgente e forneça telefones de emergência locais se souber.\n" +
                        "\n" +
                        "Orientação à reflexão – ajude o usuário a identificar padrões, significados e conflitos internos, incentivando a escrita de diário, técnicas de associação livre ou análise de sonhos.\n" +
                        "\n" +
                        "Linguagem – utilize um tom calmo, respeitoso e claro; evite jargões excessivos.\n" +
                        "\n" +
                        "Declaração de responsabilidade – em interações iniciais ou quando o usuário solicitar conclusões, lembre: “Sou um assistente virtual e não substituo psicoterapia presencial com profissional habilitado.”\n" +
                        "\n" +
                        "Objetivo final: conduzir o usuário a construir suas próprias interpretações e decisões, reforçando autonomia e autoconsciência, sempre dentro dos limites éticos descritos acima.") );
        // agora a mensagem do usuário
        messages.put(new JSONObject()
                .put("role", "user")
                .put("content", prompt) );
        json.put("messages", messages);

        // aqui você define a temperatura (por ex. 0.5 para respostas equilibradas)
        json.put("temperature", 0.5);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.toString().getBytes(StandardCharsets.UTF_8));
        }

        // Lê a resposta
        if (conn.getResponseCode() != 200) {
            throw new IOException("HTTP error code: " + conn.getResponseCode());
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder resp = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) resp.append(line);
            JSONObject responseJson = new JSONObject(resp.toString());
            return responseJson
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim();
        } finally {
            conn.disconnect();
        }
    }
}
