package org.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    public static final int PORT = 8100;
    private boolean running = true;

    public GameServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serverul a pornit și ascultă pe portul " + PORT);

            // serv ruleaza si accepta clienti
            while (running) {
                Socket socket = serverSocket.accept();
                System.out.println("Un nou client s-a conectat!");

                new ClientThread(socket, this).start();
            }
        } catch (IOException e) {
            System.err.println("Eroare la nivel de server: " + e.getMessage());
        }
    }

    // oprire serv
    public void stopServer() {
        this.running = false;
        System.out.println("Comanda 'stop' primită. Serverul se va opri.");
        System.exit(0);
    }

    public static void main(String[] args) {
        new GameServer();
    }
}