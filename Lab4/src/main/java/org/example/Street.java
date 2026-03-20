package org.example;

public class Street implements Comparable<Street> {
    private String name;
    private int length;
    private Intersection i1, i2;

    public Street(String name, int length, Intersection i1, Intersection i2) {
        this.name = name;
        this.length = length;
        this.i1 = i1;
        this.i2 = i2;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public Intersection getI1() {
        return i1;
    }

    public Intersection getI2() {
        return i2;
    }

    @Override
    public int compareTo(Street other) {
        return Integer.compare(this.length, other.length);
    }

    @Override
    public String toString() {
        return name + " " + i1 + " - " + i2 + "\nLength: " + length;
    }
}
