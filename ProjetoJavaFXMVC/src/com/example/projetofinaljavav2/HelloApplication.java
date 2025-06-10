package com.example.projetofinaljavav2;

import com.example.projetofinaljavav2.controller.*;
import com.example.projetofinaljavav2.view.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        MenuBar menuBar = new MenuBar();

        // Menu Pacientes
        Menu pacientesMenu = new Menu("Pacientes");
        MenuItem gerenciarPacientesItem = new MenuItem("Gerenciar Pacientes");
        gerenciarPacientesItem.setOnAction(e -> {
            PacienteView pacienteView = new PacienteView();
            new PacienteController(pacienteView);
            pacienteView.show();
        });
        pacientesMenu.getItems().add(gerenciarPacientesItem);

        // Menu Psicólogos
        Menu psicologosMenu = new Menu("Psicólogos");
        MenuItem gerenciarPsicologosItem = new MenuItem("Gerenciar Psicólogos");
        gerenciarPsicologosItem.setOnAction(e -> {
            PsicologoView psicologoView = new PsicologoView();
            new PsicologoController(psicologoView);
            psicologoView.show();
        });
        psicologosMenu.getItems().add(gerenciarPsicologosItem);

        // Menu Anúncios
        Menu anunciosMenu = new Menu("Anúncios");
        MenuItem gerenciarAnunciosItem = new MenuItem("Gerenciar Anúncios");
        gerenciarAnunciosItem.setOnAction(e -> {
            AnuncioView anuncioView = new AnuncioView();
            new AnuncioController(anuncioView);
            anuncioView.show();
        });
        anunciosMenu.getItems().add(gerenciarAnunciosItem);

        // Menu Guias
        Menu guiasMenu = new Menu("Guias");
        MenuItem gerenciarGuiasItem = new MenuItem("Gerenciar Guias");
        gerenciarGuiasItem.setOnAction(e -> {
            GuiaView guiaView = new GuiaView();
            new GuiaController(guiaView);
            guiaView.show();
        });
        guiasMenu.getItems().add(gerenciarGuiasItem);

        // Menu Receitas
        Menu receitasMenu = new Menu("Receitas");
        MenuItem gerenciarReceitasItem = new MenuItem("Gerenciar Receitas");
        gerenciarReceitasItem.setOnAction(e -> {
            ReceitaView receitaView = new ReceitaView();
            new ReceitaController(receitaView);
            receitaView.show();
        });
        receitasMenu.getItems().add(gerenciarReceitasItem);

        // Menu Chat
        Menu chatMenu = new Menu("Chat");
        MenuItem abrirChatItem = new MenuItem("Abrir Chat");
        abrirChatItem.setOnAction(e -> {
            ChatView chatView = new ChatView();
            new ChatController(chatView);
            chatView.show();
        });
        chatMenu.getItems().add(abrirChatItem);

        menuBar.getMenus().addAll(pacientesMenu, psicologosMenu, anunciosMenu, guiasMenu, receitasMenu, chatMenu);

        root.setTop(menuBar);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Sistema de Gerenciamento");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

