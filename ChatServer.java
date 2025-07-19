import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;


public class ChatServer {
    private static final int PORT = 12345;
        // Thread-safe set to keep track of all connected client handlers
    private static Set<ClientHandler> clientHandlers = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) {
        System.out.println("Chat server started on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // Continuously accept incoming client connections
            while (true) {
                Socket clientSocket = serverSocket.accept();// Wait for a client
                ClientHandler handler = new ClientHandler(clientSocket);// Create a handler for the client
                clientHandlers.add(handler);// Add client to the set
                new Thread(handler).start();// Handle client in a new thread
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    // Broadcast message to all clients
    public static void broadcast(String message, ClientHandler excludeUser) {
        for (ClientHandler client : clientHandlers) {
            if (client != excludeUser) {
                client.sendMessage(message);// Send message to other clients
            }
        }
    }

    // Remove client on disconnect
    public static void removeClient(ClientHandler client) {
        clientHandlers.remove(client);
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader reader;
        private PrintWriter writer;
        private String userName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                System.err.println("Error creating streams: " + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                writer.println("Enter your name: ");
                userName = reader.readLine();
                System.out.println(userName + " connected.");
                broadcast(userName + " joined the chat.", this);// Notify others

                String message;
                // Continuously read messages from this client
                while ((message = reader.readLine()) != null) {
                    if (message.equalsIgnoreCase("/exit")) {
                        break;
                    }
                    System.out.println(userName + ": " + message);
                    broadcast(userName + ": " + message, this);
                }
            } catch (IOException e) {
                System.err.println("Connection error with " + userName);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    
                }
                System.out.println(userName + " disconnected.");
                broadcast(userName + " left the chat.", this);
                removeClient(this);
            }
        }

        public void sendMessage(String message) {
            writer.println(message);
        }
    }
}
