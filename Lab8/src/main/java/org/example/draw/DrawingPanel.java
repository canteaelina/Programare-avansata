package org.example.draw;

import org.example.cell.Cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

public class DrawingPanel extends JPanel {
    private int rows, cols;
    private Cell[][] grid;
    private final int W = 30; // dim celulei

    public DrawingPanel() {
        // click pentru a activa/ dezactiva un perete manual
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (grid == null) return;

                // ajustam coordonatele in functie de padding-ul de 20px
                int mx = e.getX() - 20;
                int my = e.getY() - 20;

                int c = mx / W;
                int r = my / W;

                // verif daca click ul este in interiorul grid ului
                if (r >= 0 && r < rows && c >= 0 && c < cols) {
                    int dx = mx % W;
                    int dy = my % W;

                    int distTop = dy;
                    int distBottom = W - dy;
                    int distLeft = dx;
                    int distRight = W - dx;

                    //cel mai apropiat perete
                    int min = Math.min(Math.min(distTop, distBottom), Math.min(distLeft, distRight));
                    int threshold = 8; // zona de toleranta pentru click (in pixeli)

                    if (min <= threshold) {
                        if (min == distTop) {
                            if (r == 0 && c == 0) return; // nu permit modificarea intrarii
                            grid[r][c].walls[0] = !grid[r][c].walls[0];
                            if (r > 0) grid[r - 1][c].walls[2] = grid[r][c].walls[0]; // actualizam si vecinul
                        } else if (min == distRight) {
                            grid[r][c].walls[1] = !grid[r][c].walls[1];
                            if (c < cols - 1) grid[r][c + 1].walls[3] = grid[r][c].walls[1];
                        } else if (min == distBottom) {
                            if (r == rows - 1 && c == cols - 1) return; //nu permit modif iesirii
                            grid[r][c].walls[2] = !grid[r][c].walls[2];
                            if (r < rows - 1) grid[r + 1][c].walls[0] = grid[r][c].walls[2];
                        } else if (min == distLeft) {
                            grid[r][c].walls[3] = !grid[r][c].walls[3];
                            if (c > 0) grid[r][c - 1].walls[1] = grid[r][c].walls[3];
                        }
                        repaint();
                    }
                }
            }
        });
    }

    public void initGrid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
        grid[0][0].walls[0] = false; // Intrare
        grid[rows - 1][cols - 1].walls[2] = false; // Ieșire
        repaint();
    }

    public void randomizeWalls() {
        if (grid == null) return;

        // Resetez peretii
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int w = 0; w < 4; w++) grid[i][j].walls[w] = true;
            }
        }

        boolean[][] visited = new boolean[rows][cols];
        Stack<Cell> stack = new Stack<>();
        Random rand = new Random();

        visited[0][0] = true;
        stack.push(grid[0][0]);

        while (!stack.isEmpty()) {
            Cell current = stack.pop();
            int r = current.row, c = current.col;
            List<Cell> neighbors = new ArrayList<>();

            if (r > 0 && !visited[r - 1][c]) neighbors.add(grid[r - 1][c]);
            if (c < cols - 1 && !visited[r][c + 1]) neighbors.add(grid[r][c + 1]);
            if (r < rows - 1 && !visited[r + 1][c]) neighbors.add(grid[r + 1][c]);
            if (c > 0 && !visited[r][c - 1]) neighbors.add(grid[r][c - 1]);

            if (!neighbors.isEmpty()) {
                stack.push(current);
                Cell next = neighbors.get(rand.nextInt(neighbors.size()));

                if (next.row == r - 1) { current.walls[0] = false; next.walls[2] = false; }
                else if (next.col == c + 1) { current.walls[1] = false; next.walls[3] = false; }
                else if (next.row == r + 1) { current.walls[2] = false; next.walls[0] = false; }
                else if (next.col == c - 1) { current.walls[3] = false; next.walls[1] = false; }

                visited[next.row][next.col] = true;
                stack.push(next);
            }
        }

        grid[0][0].walls[0] = false;
        grid[rows - 1][cols - 1].walls[2] = false;
        repaint();
    }

    // validare labirint cu BFS
    public boolean validateMaze() {
        if (grid == null) return false;

        if (grid[0][0].walls[0] || grid[rows - 1][cols - 1].walls[2]) return false; //verific daca intrarea si iesirea sunt blocate

        boolean[][] visited = new boolean[rows][cols];
        Queue<Cell> queue = new LinkedList<>();

        queue.add(grid[0][0]);
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            Cell curr = queue.poll();
            int r = curr.row, c = curr.col;

            // daca am ajuns la dest
            if (r == rows - 1 && c == cols - 1) return true;

            // verif vecinii si adaugam in coada doar daca nu exista pereti despartitori
            if (!curr.walls[0] && r > 0 && !visited[r - 1][c]) { visited[r - 1][c] = true; queue.add(grid[r - 1][c]); }
            if (!curr.walls[1] && c < cols - 1 && !visited[r][c + 1]) { visited[r][c + 1] = true; queue.add(grid[r][c + 1]); }
            if (!curr.walls[2] && r < rows - 1 && !visited[r + 1][c]) { visited[r + 1][c] = true; queue.add(grid[r + 1][c]); }
            if (!curr.walls[3] && c > 0 && !visited[r][c - 1]) { visited[r][c - 1] = true; queue.add(grid[r][c - 1]); }
        }
        return false;
    }

    // Export PNG
    public void exportToPNG() {
        if (grid == null) return;
        int imgWidth = cols * W + 40;
        int imgHeight = rows * W + 40;

        BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        // fundal alb
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, imgWidth, imgHeight);

        this.paint(g2);
        g2.dispose();

        try {
            File outputFile = new File("maze_export.png");
            ImageIO.write(image, "PNG", outputFile);
            JOptionPane.showMessageDialog(this, "Labirint exportat cu succes în: " + outputFile.getAbsolutePath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Eroare la export: " + ex.getMessage());
        }
    }

    //salvare
    public void saveMaze() {
        if (grid == null) return;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("maze.ser"))) {
            oos.writeObject(grid);
            oos.writeInt(rows);
            oos.writeInt(cols);
            JOptionPane.showMessageDialog(this, "Labirint salvat cu succes (maze.ser)!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Eroare la salvare: " + ex.getMessage());
        }
    }

    //restore (Object Serialization)
    public void loadMaze() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("maze.ser"))) {
            grid = (Cell[][]) ois.readObject();
            rows = ois.readInt();
            cols = ois.readInt();
            repaint();
            JOptionPane.showMessageDialog(this, "Labirint încărcat cu succes!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Eroare la încărcare: " + ex.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (grid == null) return;
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2)); // Pereți puțin mai groși pentru vizibilitate

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int x = j * W + 20;
                int y = i * W + 20;

                g2.setColor(Color.PINK);
                g2.fillRect(x, y, W, W);

                g2.setColor(Color.BLACK);
                if (grid[i][j].walls[0]) g2.drawLine(x, y, x + W, y);
                if (grid[i][j].walls[1]) g2.drawLine(x + W, y, x + W, y + W);
                if (grid[i][j].walls[2]) g2.drawLine(x, y + W, x + W, y + W);
                if (grid[i][j].walls[3]) g2.drawLine(x, y, x, y + W);
            }
        }
    }
}