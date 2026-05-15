package org.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // Localhost
        int port = 8100;

        try (
                // conectare la server
                Socket socket = new Socket(serverAddress, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Conectarea s a efectuat cu succes! Introduceti o comanda:");

            // thread pt ascultarea mesajelor de la sv in timp real
            Thread listenerThread = new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println("\n[Server]: " + response);
                        if ("Server stopped".equals(response)) {
                            System.exit(0);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Conexiunea cu serverul s-a încheiat.");
                }
            });
            listenerThread.start();

            while (true) {
                String command = scanner.nextLine();

                if ("exit".equalsIgnoreCase(command.trim())) {
                    out.println("exit");
                    System.out.println("Se inchide clientul...");
                    break;
                }

                out.println(command);
                /*
                String response = in.readLine();

                if (response == null) {
                    System.out.println("Conexiunea cu serverul a fost intrerupta.");
                    break;
                }

                System.out.println("Raspuns server: " + response);

                if ("Server stopped".equals(response)) {
                    break;
                }*/
            }

        } catch (IOException e) {
            System.err.println("Nu se poate conecta la server: " + e.getMessage());
        }
    }
}
