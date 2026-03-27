package org.example;

import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CableService {
    private final City city;

    public CableService(City city) {
        this.city = city;
    }

    public void findSolution(int maxSol)
    {
        SimpleWeightedGraph<Intersection, Street> graph = new SimpleWeightedGraph<>(Street.class);

        //Adaugam datele in graf (inersectiile - nodurile, strazile - muchiile si costurile)

        for(Intersection i : city.getIntersections()) graph.addVertex(i);
        for(Street s : city.getStreets())
        {
            graph.addEdge(s.getI1(), s.getI2(), s);  //muchia
            graph.setEdgeWeight(s, s.getLength());   //costul
        }

        //Cautam MST-ul grafului

        KruskalMinimumSpanningTree<Intersection, Street> kruskal = new KruskalMinimumSpanningTree<>(graph);
        SpanningTreeAlgorithm.SpanningTree<Street> mst = kruskal.getSpanningTree();

        List<SpanningTreeAlgorithm.SpanningTree<Street>> solutions =  new ArrayList<>();
        solutions.add(mst);

        //acum cautam alternative eliminand pe rand muchii din mst ul principal

        for(Street s : mst.getEdges())
        {
            graph.removeEdge(s);

            //creez noul mst
            var otherKruskal = new KruskalMinimumSpanningTree<>(graph);
            var otherMst = otherKruskal.getSpanningTree();

            //verific daca s a putut ajunge in toate nodurile
            if(otherMst.getEdges().size() == city.getIntersections().size() - 1)
            {
                solutions.add(otherMst);
            }

            graph.addEdge(s.getI1(), s.getI2(), s);
            graph.setEdgeWeight(s, s.getLength());
        }

        //ordonare crescatoare dupa cost
        solutions.sort(Comparator.comparingDouble(SpanningTreeAlgorithm.SpanningTree::getWeight));


        //afisarea solutiilor
        int k = Math.min(maxSol, solutions.size());
        for(int i = 0; i < k; i++)
        {
            System.out.println((i + 1) + ". Cost total = " + solutions.get(i).getWeight());
            System.out.println("Strazile alese: " + solutions.get(i).getEdges().stream().map(Street::getName).toList() + "\n");
        }

    }
}
