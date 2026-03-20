package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class City {
    private List<Intersection> intersections;
    private List<Street> streets;

    public City() {
        this.intersections = new ArrayList<>();
        this.streets = new ArrayList<>();
    }

    public List<Intersection> getIntersections() {
        return intersections;
    }

    public List<Street> getStreets() {
        return streets;
    }

    public void setIntersections(List<Intersection> intersections) {
        this.intersections = intersections;
    }

    public void setStreets(List<Street> streets) {
        this.streets = streets;
    }

    public void displayStreets(int minLength)
    {
        Map<Intersection, Long> intersectionJoins = streets.stream().flatMap(s -> Stream.of(s.getI1(), s.getI2()))
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        System.out.println("Strazi cu lungimea mai mare decat " + minLength + " care unescc cel putin 3 intersecti:");

        streets.stream().filter(s -> s.getLength() > minLength)
                .filter(s -> {
                    long connectionsI1 = intersectionJoins.getOrDefault(s.getI1(), 0L);
                    long connectionsI2 = intersectionJoins.getOrDefault(s.getI2(), 0L);

                    return (connectionsI1 + connectionsI2 - 2) >= 3;
                })
                .forEach(System.out::println);
    }

}
