package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private GameServer server;

    public ClientThread(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String request;
            while ((request = in.readLine()) != null) {

                if ("stop".equalsIgnoreCase(request.trim())) {
                    out.println("Server stopped");
                    server.stopServer(); // Oprim serverul
                    break;
                } else {
                    out.println("Server received the request " + request);
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare în comunicarea cu clientul: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Eroare la închiderea socket-ului: " + e.getMessage());
            }
        }
    }
}