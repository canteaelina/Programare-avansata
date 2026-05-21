package org.example.server;

import java.io.PrintWriter;

public class Player {
    private final String name;
    private final PrintWriter out;
    private int score = 0;
    private long totalResponseTime = 0;

    public Player(String name, PrintWriter out) {
        this.name = name;
        this.out = out;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
    public long getTotalResponseTime() { return totalResponseTime; }

    public void addScore(int points) { this.score += points; }
    public void addTime(long time) { this.totalResponseTime += time; }
    public void sendMessage(String message) { out.println(message); }
}