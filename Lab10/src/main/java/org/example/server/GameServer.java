package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameServer {
    public static final int PORT = 8100;
    private boolean running = true;

    private ServerSocket serverSocket;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    private List<Player> players = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();

    public GameServer() {
        loadQuestions();
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Serverul a pornit si asculta pe portul " + PORT);

            // serv ruleaza si accepta clienti
            while (running) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Un client nou s a conectat!");

                    ClientThread clientThread = new ClientThread(socket, this);
                    threadPool.submit(clientThread);
                } catch (IOException e) {
                    if (running) {
                        System.err.println("Eroare la acceptare client! " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare server! " + e.getMessage());
        }
    }

    private void loadQuestions() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("questions.txt")) {
            if (is == null) {
                System.err.println("Eroare! Nu am gasit fisierul 'questions.txt'!");
                return;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Ignorăm liniile goale

                // Despărțim linia folosind "->"
                String[] parts = line.split("->");
                if (parts.length == 2) {
                    // parts[0] este intrebarea, parts[1] este raspunsul (le dam trim sa scapam de spatii)
                    questions.add(new Question(parts[0].trim(), parts[1].trim()));
                }
            }
            System.out.println("S-au incarcat " + questions.size() + " intrebari.");
        } catch (Exception e) {
            System.err.println("Eroare la citirea intrebarilor: " + e.getMessage());
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public synchronized void addPlayer(Player player) {
        players.add(player);
    }

    public synchronized String getTop3Ranking() {
        if (players.isEmpty()) {
            return "Nu sunt jucatori inregistrati.";
        }

        // Facem o clona a listei pentru a o sorta in siguranta
        List<Player> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort(
                Comparator.comparing(Player::getScore).reversed()
                        .thenComparing(Player::getTotalResponseTime)
        );

        StringBuilder sb = new StringBuilder();
        sb.append("TOP 3 JUCATORI \n");

        // Luam maxim 3 (sau cati sunt disponibili daca sunt mai putin de 3 conectati)
        int limit = Math.min(3, sortedPlayers.size());
        for (int i = 0; i < limit; i++) {
            Player p = sortedPlayers.get(i);
            sb.append(i + 1).append(". ").append(p.getName())
                    .append(" - Scor: ").append(p.getScore())
                    .append(" puncte (Timp: ").append(p.getTotalResponseTime()).append(" ms)\n");
        }
        sb.append(" ");
        return sb.toString();
    }

    //metoda de broadcast
    public void broadcastToAll(String message) {
        for (Player p : players) {
            p.sendMessage(message);
        }
    }

    // oprire serv
    public void stopServer() {
        running = false;
        System.out.println("se opreste serverul...");
        try {
            if (serverSocket != null) serverSocket.close();
            threadPool.shutdown();
            if (!threadPool.awaitTermination(30, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (IOException | InterruptedException e) {
            threadPool.shutdownNow();
        }
        System.out.println("Server oprit.");
    }

    public static void main(String[] args) {
        new GameServer();
    }
}
