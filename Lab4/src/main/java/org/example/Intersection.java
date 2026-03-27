package org.example;

public class Intersection {
    private String name;
    private double x, y; //coordonate pt respectarea regulii cu triunghiul

    public Intersection(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Intersection(String name) {
        this.name = name;
        this.x = 0.0;
        this.y = 0.0;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() { return name; }

}
