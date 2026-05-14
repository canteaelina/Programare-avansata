package org.example.concurenta;

import org.example.cell.Cell;
import java.util.ArrayList;
import java.util.List;

public class SharedMaze {
    private final Cell[][] grid;
    private final int rows, cols;
    private boolean gameOver = false;
    private final String[][] occupants;

    public SharedMaze(Cell[][] grid, int rows, int cols) {
        this.grid = grid;
        this.rows = rows;
        this.cols = cols;
        this.occupants = new String[rows][cols];
    }

    public synchronized boolean isGameOver() {
        return gameOver;
    }

    public synchronized void forceStopGame() {
        this.gameOver = true;
    }

    public synchronized boolean moveAgent(String agentName, int oldR, int oldC, int newR, int newC, boolean isBunny) {
        if (gameOver) return false;

        if (occupants[newR][newC] != null && occupants[newR][newC].startsWith("R") && !isBunny) {
            return false;
        }

        if (oldR != -1 && oldC != -1) occupants[oldR][oldC] = null;

        if (isBunny && occupants[newR][newC] != null && occupants[newR][newC].startsWith("R")) {
            System.out.println("\n GAME OVER! Iepurasul s a intalnit cu robotul " + occupants[newR][newC] + " la (" + newR + "," + newC + ")!");
            gameOver = true;
            return true;
        }

        if (!isBunny && "B".equals(occupants[newR][newC])) {
            System.out.println("\n GAME OVER! Robotul " + agentName + " a prins iepurasul la (" + newR + "," + newC + ")!");
            gameOver = true;
            return true;
        }

        occupants[newR][newC] = isBunny ? "B" : agentName;

        if (isBunny && newR == rows - 1 && newC == cols - 1) {
            System.out.println("\n VICTORIE! Iepurasul a evadat pe la (" + newR + "," + newC + ")!");
            gameOver = true;
        }
        /*
        // afis fiecare mutare
        printState();
        */
        return true;
    }

    public List<Cell> getValidMoves(int r, int c) {
        List<Cell> validMoves = new ArrayList<>();
        Cell current = grid[r][c];

        if (!current.walls[0] && r > 0) validMoves.add(grid[r - 1][c]);
        if (!current.walls[1] && c < cols - 1) validMoves.add(grid[r][c + 1]);
        if (!current.walls[2] && r < rows - 1) validMoves.add(grid[r + 1][c]);
        if (!current.walls[3] && c > 0) validMoves.add(grid[r][c - 1]);

        return validMoves;
    }
/*
    private void printState() {
        if (gameOver) return;
        System.out.println("\n--- Stare curentă ---");
        for (int i = 0; i < rows; i++) {
            // peretii de sus
            for (int j = 0; j < cols; j++) {
                System.out.print("+");
                System.out.print(grid[i][j].walls[0] ? "---" : "   ");
            }
            System.out.println("+");

            // peretii din stanga
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j].walls[3] ? "|" : " ");

                String content = "   ";
                if ("B".equals(occupants[i][j])) content = " B ";
                else if (occupants[i][j] != null) content = String.format("%2s ", occupants[i][j]);
                else if (i == rows - 1 && j == cols - 1) content = " E "; // Exit

                System.out.print(content);
            }
            // perete dreapta ult coloana
            System.out.println(grid[i][cols - 1].walls[1] ? "|" : " ");
        }

        //peretii de jos ult linie
        for (int j = 0; j < cols; j++) {
            System.out.print("+");
            System.out.print(grid[rows - 1][j].walls[2] ? "---" : "   ");
        }
        System.out.println("+");
    }
}
 */

    public synchronized void printState() {
        if (gameOver) return;
        System.out.println("\nStare curenta");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("+");
                System.out.print(grid[i][j].walls[0] ? "---" : "   ");
            }
            System.out.println("+");

            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j].walls[3] ? "|" : " ");
                String content = "   ";
                if ("B".equals(occupants[i][j])) content = " B ";
                else if (occupants[i][j] != null) content = String.format("%2s ", occupants[i][j]);
                else if (i == rows - 1 && j == cols - 1) content = " E ";

                System.out.print(content);
            }
            System.out.println(grid[i][cols - 1].walls[1] ? "|" : " ");
        }

        for (int j = 0; j < cols; j++) {
            System.out.print("+");
            System.out.print(grid[rows - 1][j].walls[2] ? "---" : "   ");
        }
        System.out.println("+");
    }
}