package AudiesParty;

import java.util.ArrayList;
import java.util.Collections;

public class DFSTraversal<T extends Comparable<T>> {
    private final Graph<T> graph;
    private ArrayList<Edge<T>> visited, order;

    public DFSTraversal(Graph<T> graph){
        this.graph = graph;
        this.visited = new ArrayList<>();
        this.order = new ArrayList<>();
    }

    private void deepFirstSearch(Edge<T> pointer){
        if (pointer.getWeight()<=0) {
            visited.add(pointer);
        }
        if (visited.contains(pointer)) {
            return;
        }
        order.add(pointer);
        visited.add(pointer);
        ArrayList<Edge<T>> relations = graph.getRelations(pointer.getSrc());
        for (Edge<T> relation : relations) {
            deepFirstSearch(relation);
        }
        relations = graph.getRelations(pointer.getDest());
        for (Edge<T> relation : relations) {
            deepFirstSearch(relation);
        }
    }

    public ArrayList<Graph<T>> getSubGraphs(){
        visited = new ArrayList<>();
        ArrayList<Graph<T>> subGraphs = new ArrayList<>();
        Graph<T> subGraph;
        for (Edge<T> e : graph.getEdges()) {
            order = new ArrayList<>();
            subGraph = new Graph<T>();
            if (e.getWeight()<=0) {
                visited.add(e);
                subGraph.add(e);
                subGraphs.add(subGraph);
                continue;
            }
            deepFirstSearch(e);
            if (order.isEmpty()) {continue;}
           
            subGraph.setEdges(order);
            subGraphs.add(subGraph);
        }
        Collections.sort(subGraphs);
        return subGraphs;
    }

}
