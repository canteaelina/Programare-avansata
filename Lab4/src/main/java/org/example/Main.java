package org.example;

import com.github.javafaker.Faker;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        City city = new City();
        Faker faker = new Faker();
        List<Intersection> nodes = IntStream.rangeClosed(0, 9)
                .mapToObj(i -> new Intersection(faker.address().cityName()))
                .collect(Collectors.toList());

        city.setIntersections(nodes);

        List<Street> streets = new LinkedList<>();

        streets.add(new Street(faker.address().streetName(), 10, nodes.get(0), nodes.get(1)));
        streets.add(new Street(faker.address().streetName(), 15, nodes.get(1), nodes.get(2)));
        streets.add(new Street(faker.address().streetName(), 5,  nodes.get(2), nodes.get(3)));
        streets.add(new Street(faker.address().streetName(), 20, nodes.get(3), nodes.get(4)));
        streets.add(new Street(faker.address().streetName(), 8,  nodes.get(4), nodes.get(5)));
        streets.add(new Street(faker.address().streetName(), 12, nodes.get(5), nodes.get(1))); // extra conexiune pt test
        streets.add(new Street(faker.address().streetName(), 30, nodes.get(6), nodes.get(7)));
        streets.add(new Street(faker.address().streetName(), 7,  nodes.get(7), nodes.get(8)));
        streets.add(new Street(faker.address().streetName(), 25, nodes.get(8), nodes.get(9)));
        streets.add(new Street(faker.address().streetName(), 3,  nodes.get(9), nodes.get(0)));

        Collections.sort(streets, Street::compareTo);

       for(var i : streets)
       {
           System.out.println(i);
       }

        Set<Intersection> intersectionSet = new HashSet<>();

        Intersection inDup = new Intersection("IDUPLICATE");

        intersectionSet.add(inDup);
        intersectionSet.add(inDup);

        System.out.println("Size ul dupa aduagarea unei duplicate:");
        System.out.println(intersectionSet.size());


        city.setStreets(streets);

        city.displayStreets(10);

        CableService networkService = new CableService(city);
        networkService.findSolution(10);

        System.out.println("\nGenerare Oras (graf incomplet):");
        City incompleteCity = Generator.genCity(100, 0.7);
        MaintenanceRoute.findMaintenanceRoute(incompleteCity);

        System.out.println("\nGenerare Oras (graf complet):");
        City completeCity = Generator.genCity(100, 1.0);
        MaintenanceRoute.findMaintenanceRoute(completeCity);
    }
}
