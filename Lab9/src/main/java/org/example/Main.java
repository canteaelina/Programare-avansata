package org.example.concurenta;

import org.example.cell.Cell;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Cell[][] grid = null;
        int rows = 0;
        int cols = 0;

        // labirintul din lab trecut
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("maze.ser"))) {
            grid = (Cell[][]) ois.readObject();
            rows = ois.readInt();
            cols = ois.readInt();
            System.out.println("Labirint incarcat cu succes din maze.ser! Dimensiune: " + rows + "x" + cols);
        } catch (Exception e) {
            System.err.println("Eroare! Nu am gasit fisierul 'maze.ser'");
            return;
        }

        SharedMaze maze = new SharedMaze(grid, rows, cols);

        // matrice pt a preveni spawn-ul pe aceeasi celula
        boolean[][] initialOccupied = new boolean[rows][cols];
        Random rand = new Random();

        /*
        Bunny bunny = new Bunny(maze, 0, 0);
        initialOccupied[0][0] = true; // Marcam intrarea ca fiind ocupata
        */

        int br, bc;
        do {
            br = rand.nextInt(rows);
            bc = rand.nextInt(cols);
            //ma asigur ca iepurasul nu se spawneaza la exit
        } while (br == rows - 1 && bc == cols - 1);

        Bunny bunny = new Bunny(maze, br, bc);
        initialOccupied[br][bc] = true;

        // setez nr de roboți
        int numberOfRobots = 2;
        Robot[] robots = new Robot[numberOfRobots];
        // Thread[] robotThreads = new Thread[numberOfRobots];

        // pun robotii la poz random distincte
        for (int i = 0; i < numberOfRobots; i++) {
            int rr, cc;
            do {
                rr = rand.nextInt(rows);
                cc = rand.nextInt(cols);
                // repet generarea daca celula e deja ocupata sau daca e fix celula de iesire
            } while (initialOccupied[rr][cc] || (rr == rows - 1 && cc == cols - 1));

            initialOccupied[rr][cc] = true; // Marcăm noua poziție ca ocupată
            robots[i] = new Robot(maze, "R" + (i + 1), rr, cc, rows, cols);
            //robotThreads[i] = new Thread(robots[i]);
        }

        Thread tb = new Thread(bunny);
        tb.start();

        for (Robot r : robots) {
            new Thread(r).start();
        }

        Thread daemon = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            long timeLimitMs = 100000; // 100 secunde limita de joc

            while (!maze.isGameOver()) {
                try {
                    Thread.sleep(1500); // afis starea la fiecare 1.5 secunde
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                if(maze.isGameOver()) break;

                long elapsedTime = System.currentTimeMillis() - startTime;
                System.out.println("\n[DAEMON] Timp scurs: " + (elapsedTime / 1000) + "s / 30s");
                maze.printState();

                if (elapsedTime > timeLimitMs) {
                    System.out.println("\n[DAEMON] Limita de timp a expirat! Oprire forțată.");
                    maze.forceStopGame();
                    System.exit(0); // Forteaza oprirea, inclusiv a Scanner-ului
                }
            }
        });
        daemon.setDaemon(true); // il setam ca Daemon
        daemon.start();

        System.out.println("Comenzile disponibile: pause <nume>, resume <nume>, slow <nume>, fast <nume>");
        System.out.println("<nume> poate fi: B, R1, R2... sau 'all'.");

        Scanner scanner = new Scanner(System.in);
        while (!maze.isGameOver()) {
            // verif daca exista input ca sa nu blocam permanent main ul la final
            try {
                if (System.in.available() > 0) {
                    String input = scanner.nextLine().trim().toLowerCase();
                    String[] parts = input.split(" ");

                    if (parts.length == 2) {
                        String cmd = parts[0];
                        String target = parts[1].toUpperCase();

                        // Iepure
                        if (target.equals("B") || target.equals("ALL")) {
                            if (cmd.equals("pause")) bunny.isPaused = true;
                            if (cmd.equals("resume")) bunny.isPaused = false;
                            if (cmd.equals("slow")) bunny.speed += 300;
                            if (cmd.equals("fast")) bunny.speed = Math.max(100, bunny.speed - 300);
                        }

                        // Roboti
                        for (Robot r : robots) {
                            if (target.equals(r.name) || target.equals("ALL")) {
                                if (cmd.equals("pause")) r.isPaused = true;
                                if (cmd.equals("resume")) r.isPaused = false;
                                if (cmd.equals("slow")) r.speed += 300;
                                if (cmd.equals("fast")) r.speed = Math.max(100, r.speed - 300);
                            }
                        }
                        if(!(target.equals("B") || target.equals("R1") || target.equals("R2") || target.equals("ALL")) ||
                                !(cmd.equals("pause") || cmd.equals("resume") || cmd.equals("slow") || cmd.equals("fast")))
                            System.out.println("Nume sau comanda invalida");
                        else
                            System.out.println("Comanda acceptata: " + input);
                    }
                } else {
                    Thread.sleep(200);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Simularea s a incheiat! Apasa enter pentru a iesi.");
        System.exit(0);
    }
}