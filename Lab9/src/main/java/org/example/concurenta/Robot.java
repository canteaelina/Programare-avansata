package org.example.concurenta;


import org.example.cell.Cell;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Robot implements Runnable {
    private final SharedMaze maze;
    public final String name;
    private int r, c;
    private final Random rand = new Random();

    //control manual + explorare sistematica
    public volatile int speed = 600;
    public volatile boolean isPaused = false;
    private final boolean[][] visited;

    public Robot(SharedMaze maze, String name, int startR, int startC, int rows, int cols) {
        this.maze = maze;
        this.name = name;
        this.r = startR;
        this.c = startC;
        maze.moveAgent(name, -1, -1, r, c, false);

        //init memoria robotului
        this.visited = new boolean[rows][cols];
        this.visited[r][c] = true;

        maze.moveAgent(name, -1, -1, r, c, false);
    }

    @Override
    public void run() {
        while (!maze.isGameOver()) {
            try {
                if (isPaused) {
                    Thread.sleep(200); // asteapta fara sa faca mutari
                    continue;
                }
                Thread.sleep(600); // Roboții se mișcă o idee mai lent
                List<Cell> moves = maze.getValidMoves(r, c);
                if (!moves.isEmpty()) {
                    Cell nextMove;
                    //nextMove = moves.get(rand.nextInt(moves.size()));  mutari aleatoare

                    // cauta doar celulele in care NU a mai fost
                    List<Cell> unvisitedMoves = moves.stream()
                            .filter(m -> !visited[m.row][m.col])
                            .collect(Collectors.toList());

                    if (!unvisitedMoves.isEmpty()) {
                        // daca are vecini nevizitati alege unul la intamplare din ei
                        nextMove = unvisitedMoves.get(rand.nextInt(unvisitedMoves.size()));
                    } else {
                        // daca toti vecinii sunt vizitati se intoarce la intamplare
                        nextMove = moves.get(rand.nextInt(moves.size()));
                    }

                    if (maze.moveAgent(name, r, c, nextMove.row, nextMove.col, false)) {
                        r = nextMove.row;
                        c = nextMove.col;
                        visited[r][c] = true; // marcam celula noua ca viz
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}