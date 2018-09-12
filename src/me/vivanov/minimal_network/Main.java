package me.vivanov.minimal_network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length < 1)
            System.out.println("You need to provide a file name as an argument");

        try (Reader reader = new BufferedReader(new FileReader(args[0]))){
            final Scanner scanner = new Scanner(reader);

            Map<Integer, Edge> networkEdges = new HashMap<>();
            Set<Integer> nodes = new HashSet<>(); // nodes need to be unique

            scanner.nextInt(); // number of nodes
            int m = scanner.nextInt(); // number of edges

            // collect the network's unique edges and all nodes
            for (int i = 0; i < m; i++) {
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                int w = scanner.nextInt();

                int left, right;
                // order vertices so that the left is always the smaller, this way the Edge objects will be unique in the NavigableSet
                if (u < v) {
                    left = u;
                    right = v;
                } else {
                    left = v;
                    right = u;
                }

                Edge edge = new Edge(left, right, w);
                int hashCode = edge.hashCode();

                // check if this is another edge between the same pair of vertices, because a pair of vertices may have
                // more than one edge
                if (networkEdges.containsKey(hashCode)) {
                    if (networkEdges.get(hashCode).getWeight() <= w)
                        continue; // if the existing edge in the collection is smaller in weight, skipp adding
                }

                networkEdges.put(hashCode, edge);
                nodes.add(left);
                nodes.add(right);
            }

            // convert the map to LinkedList for easier working and fast remove() method
            List<Edge> networkEdgesArray = new LinkedList<>(networkEdges.values());

            Set<Integer> visited = new HashSet<>(); // we need efficiency in the .contains() method
            visited.add(networkEdgesArray.get(0).getLeft()); // start with the first node
            int minimumWeight = 0;

            // walk through all the nodes
            while (visited.size() < nodes.size()) {

                // collect all the candidate edges from the network (with one vertices visited, another not yet), ordering them by weight
                NavigableSet<Edge> candidateEdges = new TreeSet<>(Comparator.comparing(Edge::getWeight));

                for (Edge edge : networkEdgesArray) {

                    if (visited.contains(edge.getLeft()) == visited.contains(edge.getRight()))
                        continue; // we need only one of the nodes of the edge to be visited

                    candidateEdges.add(edge);
                }

                Edge minimalEdge = candidateEdges.pollFirst();
                if (minimalEdge != null) {
                    minimumWeight += minimalEdge.getWeight(); // add to the total
                    // add the two vertices of the edge to the visited
                    visited.add(minimalEdge.getLeft());
                    visited.add(minimalEdge.getRight());

                    networkEdgesArray.remove(minimalEdge); // make the algorithm greedy
                }
                else {
                    throw new Exception("minimal edge not found: " + candidateEdges);
                }
            }

            System.out.println(minimumWeight);

        } catch (IOException ex) {
            System.out.println("Exception of type: " + ex.getClass().getSimpleName());
            System.out.println(" with message: " + ex.getMessage());
            for (StackTraceElement elem : ex.getStackTrace()) {
                System.out.println(elem.getFileName() + ": " + elem.getLineNumber());
            }
        }
    }
}

class Edge {
    private final int left;
    private final int right;
    private final int weight;

    Edge(int left, int right, int weight) {
        this.left = left;
        this.right = right;
        this.weight = weight;
    }

    int getLeft() {
        return left;
    }

    int getRight() {
        return right;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}