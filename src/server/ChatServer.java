package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static Set<ClientHandler> clients = new HashSet<>();
    private static ServerSocket serverSocket;
    private static boolean running = true;
    private static Scanner consoleScanner;

    public static void main(String[] args) {
        consoleScanner = new Scanner(System.in);
        System.out.println("Servidor iniciado...");
        
        // Thread para ler comandos do console
        new Thread(() -> {
            while (running) {
                String command = consoleScanner.nextLine();
                if (command.equalsIgnoreCase("/quit")) {
                    shutdownServer();
                } else if (command.startsWith("/broadcast ")) {
                    broadcastToAll("[SERVER] " + command.substring(11));
                } else if (command.startsWith("/msg ")) {
                    // Implementar mensagem para cliente específico
                    String[] parts = command.split(" ", 3);
                    if (parts.length == 3) {
                        sendToClient(parts[1], "[SERVER] " + parts[2]);
                    }
                }
            }
        }).start();

        try {
            serverSocket = new ServerSocket(PORT);
            while (running) {
                ClientHandler newClient = new ClientHandler(serverSocket.accept());
                synchronized (clients) {
                    clients.add(newClient);
                }
                newClient.start();
            }
        } catch (IOException e) {
            if (running) {
                e.printStackTrace();
            }
        } finally {
            closeResources();
        }
    }

    private static void shutdownServer() {
        running = false;
        broadcastToAll("[SERVER] O servidor está sendo desligado...");
        
        try {
            synchronized (clients) {
                for (ClientHandler client : clients) {
                    client.close();
                }
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        consoleScanner.close();
        System.out.println("Servidor desligado.");
        System.exit(0);
    }

    public static void broadcastToAll(String message) {
        System.out.println("Broadcasting: " + message);
        synchronized (clients) {
            for (ClientHandler client : clients) {
                client.sendMessage(message);
            }
        }
    }

    public static void sendToClient(String clientId, String message) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client.getClientId().equals(clientId)) {
                    client.sendMessage(message);
                    return;
                }
            }
        }
        System.out.println("Cliente " + clientId + " não encontrado.");
    }

    public static void removeClient(ClientHandler client) {
        synchronized (clients) {
            clients.remove(client);
        }
    }

    private static void closeResources() {
        try {
            if (consoleScanner != null) {
                consoleScanner.close();
            }
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String clientId;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            this.clientId = "client-" + UUID.randomUUID().toString().substring(0, 8);
        }

        public String getClientId() {
            return clientId;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Enviar ID para o cliente
                out.println("/yourid " + clientId);
                System.out.println("Novo cliente conectado: " + clientId);

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("[" + clientId + "]: " + message);
                    broadcastToAll("[" + clientId + "]: " + message);
                }
            } catch (IOException e) {
                System.out.println("Cliente " + clientId + " desconectado.");
            } finally {
                removeClient(this);
                close();
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        public void close() {
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}