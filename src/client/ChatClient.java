package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;

public class ChatClient extends Application {
    private PrintWriter out;
    private TextArea messageArea = new TextArea();
    private String clientId;
    private TextField inputField = new TextField();
    
    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) {
        primaryStage.setTitle("Chat - Apple Style");
        
        // Layout principal
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f7;");
        
        // Barra superior (título)
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e1e1e3; -fx-border-width: 0 0 1 0;");
        
        Label titleLabel = new Label("Chat");
        titleLabel.setFont(Font.font("SF Pro", 20));
        titleLabel.setTextFill(Color.web("#1d1d1f"));
        topBar.getChildren().add(titleLabel);
        
        // Área de mensagens
        messageArea.setEditable(false);
        messageArea.setWrapText(true);
        messageArea.setFont(Font.font("SF Pro", 14));
        messageArea.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e1e1e3; -fx-border-width: 1; -fx-border-radius: 5;");
        
        ScrollPane scrollPane = new ScrollPane(messageArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: #ffffff; -fx-border-color: transparent;");
        
        // Área de entrada
        HBox inputBox = new HBox(10);
        inputBox.setPadding(new Insets(15));
        inputBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e1e1e3; -fx-border-width: 1 0 0 0;");
        
        inputField.setPromptText("Digite sua mensagem...");
        inputField.setFont(Font.font("SF Pro", 14));
        inputField.setStyle("-fx-background-color: #f5f5f7; -fx-border-color: #e1e1e3; -fx-border-width: 1; -fx-border-radius: 18; -fx-padding: 8 15;");
        HBox.setHgrow(inputField, Priority.ALWAYS);
        
        Button sendButton = new Button("Enviar");
        sendButton.setFont(Font.font("SF Pro", 14));
        sendButton.setStyle("-fx-background-color: #0071e3; -fx-text-fill: white; -fx-background-radius: 18; -fx-padding: 8 20;");
        sendButton.setOnMouseEntered(e -> sendButton.setStyle("-fx-background-color: #0077ed; -fx-text-fill: white; -fx-background-radius: 18; -fx-padding: 8 20;"));
        sendButton.setOnMouseExited(e -> sendButton.setStyle("-fx-background-color: #0071e3; -fx-text-fill: white; -fx-background-radius: 18; -fx-padding: 8 20;"));
        
        sendButton.setOnAction(e -> sendMessage());
        inputField.setOnAction(e -> sendMessage());
        
        inputBox.getChildren().addAll(inputField, sendButton);
        
        // Montando a cena
        root.setTop(topBar);
        root.setCenter(scrollPane);
        root.setBottom(inputBox);
        
        Scene scene = new Scene(root, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(350);
        primaryStage.setMinHeight(500);
        primaryStage.show();
        
        // Conectar ao servidor
        new Thread(this::connectToServer).start();
        
        // Fechar conexão ao fechar a janela
        primaryStage.setOnCloseRequest(e -> {
            if (out != null) {
                out.println("/quit");
            }
        });
    }
    
    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            out.println(message);
            inputField.clear();
        }
    }
    
    private void connectToServer() {
        try (Socket socket = new Socket("localhost", 12345)) {
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("/yourid ")) {
                    clientId = message.substring(8);
                    Platform.runLater(() -> messageArea.appendText("Seu ID: " + clientId + "\n"));
                } else {
                    String finalMessage = message;
                    Platform.runLater(() -> {
                        messageArea.appendText(finalMessage + "\n");
                        // Scroll para baixo
                        messageArea.setScrollTop(Double.MAX_VALUE);
                    });
                }
            }
        } catch (IOException e) {
            Platform.runLater(() -> messageArea.appendText("Erro de conexão com o servidor.\n"));
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}