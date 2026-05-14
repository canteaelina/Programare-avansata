package org.example.concurenta;

import org.example.cell.Cell;
import java.util.List;
import java.util.Random;

public class Bunny implements Runnable {
    private final SharedMaze maze;
    private int r, c;
    private final Random rand = new Random();

    public volatile int speed = 400;
    public volatile boolean isPaused = false;

    public Bunny(SharedMaze maze, int startR, int startC) {
        this.maze = maze;
        this.r = startR;
        this.c = startC;
        maze.moveAgent("B", -1, -1, r, c, true);
    }

    @Override
    public void run() {
        while (!maze.isGameOver()) {
            try {
                if (isPaused) {
                    Thread.sleep(200);
                    continue;
                }
                Thread.sleep(400); // viteza iepurasului
                List<Cell> moves = maze.getValidMoves(r, c);
                if (!moves.isEmpty()) {
                    Cell nextMove = moves.get(rand.nextInt(moves.size()));
                    if (maze.moveAgent("B", r, c, nextMove.row, nextMove.col, true)) {
                        r = nextMove.row;
                        c = nextMove.col;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}