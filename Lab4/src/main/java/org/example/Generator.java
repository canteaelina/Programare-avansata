package org.example;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    private static Street createStreet(Faker faker, Intersection i1, Intersection i2)
    {
        double dist = Math.hypot(i1.getX() - i2.getX(), i1.getY() - i2.getY());
        return new Street(faker.address().streetName(), (int) Math.round(dist), i1, i2);
    }

    public static City genCity(int nrOfNodes, double edgeProb)
    {
        City city = new City();
        Faker faker = new Faker();
        Random rand = new Random();

        //generam intersectiile cu coordonate aleatoare
        List<Intersection> nodes = new ArrayList<>();
        for (int i = 0; i < nrOfNodes; i++)
        {
            nodes.add(new Intersection(faker.address().cityName(),
                    rand.nextDouble() * 1000,
                    rand.nextDouble() * 1000));
        }
        city.setIntersections(nodes);

        List<Street> streets = new ArrayList<>();

        //verif daca graful e conex
        for (int i = 0; i < nrOfNodes - 1; i++) {
            streets.add(createStreet(faker, nodes.get(i), nodes.get(i + 1)));
        }

        //adaugam restul strazilor in functie de probabilitatea de a aparea
        //daca edgeProb = 1 avem graf complet
        for (int i = 0; i < nrOfNodes; i++) {
            for (int j = i + 2; j < nrOfNodes; j++) {
                if (rand.nextDouble() < edgeProb) //verific daca pun muchia sau nu (daca genereaza 0.12 si edgeProb e 0.3 (0.12 < 0.3) adaugam muchia altfel nu
                {
                    streets.add(createStreet(faker, nodes.get(i), nodes.get(j)));
                }
            }
        }

        city.setStreets(streets);
        return city;
    }
}
