import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_IP = "127.0.0.1"; // change if needed
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            // Thread to listen to messages from server
            Thread readThread = new Thread(() -> {
                try {
                    String response;
                    while ((response = reader.readLine()) != null) {
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });
            readThread.start();

            // Main thread sends user input to server
            while (true) {
                String message = scanner.nextLine();
                writer.println(message);
                if (message.equalsIgnoreCase("/exit")) {
                    break;
                }
            }

        } catch (IOException e) {
            // Handle error if unable to connect to server
            System.err.println("Cannot connect to server: " + e.getMessage());
        }
    }
}
//run in the terminal of the folder
//javac ChatServer.java
//javac ChatClient.java
//java ChatServer

//run below in other terminals
//java ChatClient
