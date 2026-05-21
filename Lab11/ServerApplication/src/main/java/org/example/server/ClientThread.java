package org.example.server;

import org.example.server.entity.PlayerEntity;
import org.example.server.repository.GameRepository;
import org.example.server.repository.PlayerRepository;
import org.example.server.repository.QuestionRepository;
import org.example.server.repository.ResultRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private GameServer server;

    private BufferedReader in;
    private PrintWriter out;

    public ClientThread(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Conectare reusita! Te rog sa introduci numele tau:");
            String playerName = in.readLine();

            // daca nu introduce nimic, ii dam un nume default
            if (playerName == null || playerName.trim().isEmpty()) {
                playerName = "Player " + socket.getPort();
            }

            Player player = new Player(playerName.trim(), out);
            server.addPlayer(player);

            out.println("Bine ai venit, " + player.getName() + "! Scrie 'start' pentru a incepe intrebarile, 'top' pt clasament, sau 'stop' pentru a opri serverul.");

            String request;
            while ((request = in.readLine()) != null) {

                if ("start".equalsIgnoreCase(request)) {
                    out.println("Pregătește-te! Jocul începe acum.");

                    for (Question q : server.getQuestions()) {
                        askQuestion(player, q);
                    }

                    try {
                        QuestionRepository questionRepo = new QuestionRepository();
                        PlayerRepository playerRepo = new PlayerRepository();
                        GameRepository gameRepo = new GameRepository();
                        ResultRepository resultRepo = new ResultRepository();

                        // 1. Verificăm dacă jucătorul există deja în BD după nume (folosind query-ul tău JPQL)
                        java.util.List<PlayerEntity> existing = playerRepo.findPlayersByName(player.getName());
                        PlayerEntity playerEntity;
                        if (!existing.isEmpty()) {
                            playerEntity = existing.get(0); // Îl refolosim pe cel existent
                        } else {
                            playerEntity = new PlayerEntity(player.getName());
                            playerRepo.create(playerEntity); // Îl salvăm ca nou
                        }

                        // 2. Extragem întrebările din BD și creăm sesiunea de joc (Asta va popula game_questions!)
                        java.util.List<org.example.server.entity.QuestionEntity> dbQuestions = questionRepo.findAll();
                        org.example.server.entity.GameEntity gameEntity = gameRepo.createGame(dbQuestions);

                        // 3. Salvăm rezultatul final în tabela 'results'
                        org.example.server.entity.ResultEntity result = new org.example.server.entity.ResultEntity(
                                gameEntity,
                                playerEntity,
                                player.getScore(),
                                player.getTotalResponseTime()
                        );
                        resultRepo.saveResult(result);
                        out.println("[Sistem] Rezultatele tale au fost salvate permanent în baza de date Oracle!");
                    } catch (Exception e) {
                        System.err.println("Eroare la salvarea în BD: " + e.getMessage());
                    }

                    out.println("Jocul s-a incheiat pentru tine! Scrie 'exit' dacă vrei sa iesi.");
                } else if ("exit".equalsIgnoreCase(request)) {
                    out.println("La revedere, " + player.getName() + "!");
                    break;
            } else if ("top".equalsIgnoreCase(request) || "clasament".equalsIgnoreCase(request)) {

                // AICI APELEZI METODA NOUĂ
                String clasament = server.getTop3Ranking();
                out.println(clasament); // Îi trimitem textul clientului care a cerut

            }else if ("stop".equalsIgnoreCase(request.trim())) {
                    out.println("Server stopped");
                    server.stopServer(); // Oprim serverul
                    break;
                } else {
                    out.println("Comanda invalida" + request);
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare in comunicarea cu clientul: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Eroare la inchiderea socket-ului: " + e.getMessage());
            }
        }
    }

    public void askQuestion(Player player, Question question) throws IOException {
        long TIME_LIMIT_MS = 10000; // 10 secunde (Blitz)

        player.sendMessage("[INTREBARE] " + question.getText() + " (Ai " + (TIME_LIMIT_MS/1000) + " secunde!)");

        long startTime = System.currentTimeMillis();

        // Setăm un timeout pe socket pentru a forța limita de timp
        // Notă: socket-ul trebuie expus/transmis în acest context
        socket.setSoTimeout((int) TIME_LIMIT_MS);

        try {
            String answer = in.readLine(); // Așteaptă răspunsul
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;

            if (question.isCorrect(answer)) {
                player.addScore(1);
                player.addTime(timeTaken); // Adăugăm timpul pentru departajare
                player.sendMessage("Corect! Timp de raspuns: " + timeTaken + " ms.");
            } else {
                player.sendMessage("Gresit! Raspunsul corect era: " + question.getCorrectAnswer());
            }
        } catch (java.net.SocketTimeoutException e) {
            player.sendMessage("Timpul a expirat pentru aceasta intrebare!");
        } finally {
            // Resetăm timeout-ul socket-ului la infinit pentru restul comunicației (ex: așteptare comenzi)
            socket.setSoTimeout(0);
        }
    }
}


