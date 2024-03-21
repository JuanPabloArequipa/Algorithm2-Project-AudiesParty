package project;

import utils.Edge;
import utils.Graph;
import utils.GraphParser;
import utils.DFSTraversal;

import java.util.ArrayList;
import java.util.HashSet;

public class AudiesParty {
    public static void main(String[] args) {
        GraphParser parser = new GraphParser("src/main/resources/example2.txt");
        Graph<String> graph = parser.createGraph(10);

        System.out.println("Guests List: ");
        getGuestsList(graph);
        System.out.println("Groups: ");
        getGuestsGroup(graph, 4);
    }


    // FIRST OUTPUT

    public static void getGuestsList(Graph<String> graph){
        HashSet<String> hashSet = new HashSet<>();
        for (Edge<String> edge: graph.getEdges()) {
            hashSet.add(edge.getSrc());
            hashSet.add(edge.getDest());
        }

        for (String guest: hashSet) {
            System.out.print(guest + " ");
        }
        System.out.println();
    }

    // SECOND OUTPUT
    private static void cutGraph(Graph<String> graph, int k){
        for (int i = 0; i < k-1; i++) {
            graph.cutGraph();
        }
    }

    private static ArrayList<Graph<String>> getDFSGraphs(Graph<String> graph){
        DFSTraversal<String> aux = new DFSTraversal<>(graph);
        return aux.getSubGraphs();
    }

    private static boolean areGraphsCut(ArrayList<Graph<String>> graphs){
        for (Graph<String> graph : graphs) {
            if (graph.getWeight()>=0) {
                return false;
            }
        }
        return true;
    }

    public static void getGuestsGroup(Graph<String> graph, int k){
        cutGraph(graph, k);
        HashSet<String> hashSet;
        ArrayList<String> visited = new ArrayList<>();

        //StringBuilder sb = new StringBuilder();
        ArrayList<Graph<String>> graphs = getDFSGraphs(graph);

        Graph<String> strongest = graphs.get(0);
        Graph<String> least = graphs.get(graphs.size()-1);

        String strongestStr = "",leastStr = "";
        Graph<String> current;

        if (!(k <= graph.getVertexes().size())) {
            System.out.println("It is not possible");
        }

        for (int i = graphs.size()-1; i >=0; i--) {
            if (graphs.get(i).minorWeight() < least.minorWeight() && graphs.get(i).getWeight() > 0)
                least = graphs.get(i);
            if (graphs.get(i).majorWeight() > strongest.majorWeight())
                strongest = graphs.get(i);
        }

        if (strongest.getWeight()<0) strongest = null;
        if (least.getWeight()<0) least = null;

        if (areGraphsCut(graphs)) {
            Graph<String> cuttedGraph = graphs.get(graphs.size()-1);
            Edge<String> cuttedEdge = cuttedGraph.getEdges().get(0);
            String aPerson = cuttedEdge.getDest();
            System.out.print(aPerson +" ");
            visited.add(aPerson);
        }

        for (int i = graphs.size()-1; i >=0; i--) {
            hashSet = new HashSet<>();
            current = graphs.get(i);
            for (String vertex : current.getVertexes()) {
                if (visited.contains(vertex)) continue;
                hashSet.add(vertex);
                visited.add(vertex);
            }
            if (hashSet.isEmpty()) continue;
            if (strongest!=null && strongest.equals(current)) {
                strongestStr = hashSet.toString();
            }

            if (least!=null && least.equals(current)) {
                leastStr = hashSet.toString();
            }

            for (String item : hashSet) {
                System.out.print(item + " ");
            }
            System.out.println();
        }

        strongestStr = strongestStr.isEmpty()? "None": strongestStr.substring(1,strongestStr.length()-1);
        leastStr = leastStr.isEmpty() ? "None": leastStr.substring(1,leastStr.length()-1);

        System.out.println("Group with strongest friendly relationship: "+ strongestStr);
        System.out.println("Group with least friendly relationship: " + leastStr);
    }
}
