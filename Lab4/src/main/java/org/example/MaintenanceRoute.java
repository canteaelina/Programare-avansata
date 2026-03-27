package org.example;

import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;

public class MaintenanceRoute {
    private static final int INF = 1_000_000_000;

    private static void dfs(int node, Map<Integer, List<Integer>> adj, Set<Integer> visited, List<Integer> tour) {
        visited.add(node);
        tour.add(node);
        for (int neighbor : adj.get(node)) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, adj, visited, tour);
            }
        }
    }

    public static void findMaintenanceRoute(City city) {
        List<Intersection> nodes = city.getIntersections();
        List<Street> streets = city.getStreets();
        int n = nodes.size();

        Map<Intersection, Integer> nodeToIndex = new HashMap<>();
        Map<Integer, Intersection> indexToNode = new HashMap<>();
        for (int i = 0; i < n; i++) {
            nodeToIndex.put(nodes.get(i), i);
            indexToNode.put(i, nodes.get(i));
        }

        int[][] dist = new int[n][n];
        int[][] next = new int[n][n]; //pentru reconstruirea rutei

        //initializam matricile
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                    next[i][j] = i;
                } else {
                    dist[i][j] = INF;
                    next[i][j] = -1;
                }
            }
        }

        //initializez cu valorile din primul pas de la floyd warshall
        for (Street s : streets) {
            int u = nodeToIndex.get(s.getI1());
            int v = nodeToIndex.get(s.getI2());
            dist[u][v] = s.getLength();
            dist[v][u] = s.getLength();
            next[u][v] = v;
            next[v][u] = u;
        }

        //floyd warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }

        //creez graful metric
        SimpleWeightedGraph<Integer, DefaultWeightedEdge> metricGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (int i = 0; i < n; i++) metricGraph.addVertex(i);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (dist[i][j] != INF) {
                    var edge = metricGraph.addEdge(i, j);
                    metricGraph.setEdgeWeight(edge, dist[i][j]);
                }
            }
        }


        //gasesc MST ul
        var kruskal = new KruskalMinimumSpanningTree<>(metricGraph);
        var mst = kruskal.getSpanningTree();

        Map<Integer, List<Integer>> mstAdj = new HashMap<>();
        for (int i = 0; i < n; i++) mstAdj.put(i, new ArrayList<>());
        for (var edge : mst.getEdges()) {
            int u = metricGraph.getEdgeSource(edge);
            int v = metricGraph.getEdgeTarget(edge);
            mstAdj.get(u).add(v);
            mstAdj.get(v).add(u);
        }

        //parcurgerea dfs
        List<Integer> logicalTour = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        dfs(0, mstAdj, visited, logicalTour);
        logicalTour.add(0); // Întoarcerea la depou

        //reconstructia drumului
        List<Intersection> physicalRoute = new ArrayList<>();
        int totalPhysicalCost = 0;

        for (int i = 0; i < logicalTour.size() - 1; i++)
        {
            int start = logicalTour.get(i);
            int end = logicalTour.get(i + 1);

            if (next[start][end] == -1)
            {
                System.out.println("Eroare: Graful nu este conex! Nu se poate ajunge la toate intersecțiile.");
                return;
            }
            int current = start;
            while (current != end) {
                physicalRoute.add(indexToNode.get(current));
                int nextNode = next[current][end];
                totalPhysicalCost += dist[current][nextNode]; //adaugam lungimea strazii reale
                current = nextNode;
            }

        }
        physicalRoute.add(indexToNode.get(0)); //Ultimul nod

        System.out.println("Costul Total: " + totalPhysicalCost);
        System.out.println("Traseul:");

        for (int i = 0; i < physicalRoute.size() - 1; i++) {
            System.out.print(physicalRoute.get(i).getName() + " -> ");
        }
        System.out.println(physicalRoute.get(physicalRoute.size() - 1).getName());
    }
}
