package me.vivanov.minimal_network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1)
            System.out.println("You need to provide a file name as an argument");

        try (Reader reader = new BufferedReader(new FileReader(args[0]))){
            final Scanner scanner = new Scanner(reader);
            Map<Integer, List<Edge>> nodes = createNodeMap(scanner);

            Set<Integer> nodeKeys = nodes.keySet();
            Iterator<Integer> it = nodeKeys.iterator();
            Set<Integer> visited = new HashSet<>();
            int totalWeight = 0;
            while(it.hasNext()) {
                int node = it.next();

                if (visited.size() == 0) visited.add(node); // add the first node

                Edge smallestEdge = findSmallestEdge(nodes, visited);
                if (smallestEdge != null) {
                    totalWeight += smallestEdge.getWeight();
                }
                visited.add(node); // add the current node to the list of visited nodes
            }

            System.out.println(totalWeight);

        } catch (IOException ex) {
            System.out.println("Exception of type: " + ex.getClass().getSimpleName());
            System.out.println(" with message: " + ex.getMessage());
            for (StackTraceElement elem : ex.getStackTrace()) {
                System.out.println(elem.getFileName() + ": " + elem.getLineNumber());
            }
        }
    }

    /**
     * Find the edge with the smallest weight and not visited "destination node" amongst the edges of the visited nodes
     */
    private static Edge findSmallestEdge(Map<Integer, List<Edge>> nodes, Set<Integer> visited) {
        Edge smallestEdge = null;
        Integer nodeOfSmallestEdge = null;

        // iterate over all the visited nodes
        for (Integer node : visited) {
            List<Edge> edges = nodes.get(node);
            Iterator<Edge> edgeIterator = edges.iterator();

            // iterate over the edges of the visited nodes
            while (edgeIterator.hasNext()) {
                Edge edge = edgeIterator.next();

                // if the destination node of this edge is present in the visited, remove it from the edges collection
                if (visited.contains(edge.getOtherNode(node))) {
                    edgeIterator.remove();
                } else if (smallestEdge == null || (edge.getWeight() < smallestEdge.getWeight())) {
                    smallestEdge = edge;
                    nodeOfSmallestEdge = node;
                }
            }
        }

        if (nodeOfSmallestEdge != null) {
            nodes.get(nodeOfSmallestEdge).remove(smallestEdge);
            visited.add(smallestEdge.getOtherNode(nodeOfSmallestEdge));
        }

        return smallestEdge;
    }

    private static Map<Integer, List<Edge>> createNodeMap(Scanner scanner) {
        Map<Integer, List<Edge>> nodeMap = new HashMap<>();

        scanner.nextInt(); // number of nodes
        int m = scanner.nextInt(); // number of edges

        for (int i = 0; i < m; i++) {

            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int w = scanner.nextInt();

            if (u == v) continue;

            Edge edge = new Edge(u, v, w);
            addEdges(nodeMap, new int[]{u, v}, edge);
        }

        return nodeMap;
    }

    /**
     * Add pointer to the edge for the two nodes in the map
     */
    private static void addEdges(Map<Integer, List<Edge>> nodesMap, int[] nodes,  Edge edge) {
        for (int node : nodes) {
            List<Edge> edges = getNodeEdges(nodesMap, node);
            edges.add(edge);
            nodesMap.put(node, edges);
        }
    }

    private static List<Edge> getNodeEdges(Map<Integer, List<Edge>> nodesMap, int node) {
        if (!nodesMap.containsKey(node)) {
            return new ArrayList<>();
        }

        return nodesMap.get(node);
    }
}

class Edge {
    private final int first;
    private final int second;
    private final int weight;

    Edge(int first, int second, int weight) {
        this.first = first;
        this.second = second;
        this.weight = weight;
    }

    int getOtherNode(int node) {
        return this.first == node ? this.second : this.first;
    }

    int getWeight() {
        return weight;
    }

    @Override()
    public String toString() {
        return first + " " + second + " " + weight;
    }
}