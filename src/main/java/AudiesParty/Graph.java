package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Graph<T extends Comparable<T>> implements Comparable<Graph<T>>{
    private ArrayList<Edge<T>> edges;

    public Graph(){
        edges = new ArrayList<>();
    }

    public void add (Edge<T> e){
        edges.add(e);
    }

    public ArrayList<Edge<T>> getEdges(){
        return edges;
    }

    public int getWeight(){
        int total = 0;
        for (Edge<T> edge : edges) {
            total+=edge.getWeight();
        }
        return total;
    }

    public void setEdges(ArrayList<Edge<T>> edges){
        this.edges = edges;
        Collections.sort(this.edges);
    }

    public void cutGraph(){
        Edge<T> toCut;
        for (Edge<T> edge : edges) {
            toCut = edge;
            if (toCut.getWeight()>=0) {
                toCut.setWeight(-toCut.getWeight());
                break;
            }
        }
    }

    public HashSet<T> getVertexes(){
        HashSet<T> hashSet = new HashSet<>();
        for (Edge<T> edge : edges) {
            hashSet.add(edge.getDest());
            hashSet.add(edge.getSrc());
        }
        return hashSet;
    }

    public ArrayList<Edge<T>> getRelations(T source){
        ArrayList<Edge<T>> relations = new ArrayList<>();
        for (Edge<T> edge : edges) {
            if (edge.getSrc().equals(source) || edge.getDest().equals(source))
                relations.add(edge);
        }
        return relations;
    }

    public int minorWeight(){
        return edges.get(0).getWeight();
    }

    public int majorWeight(){
        return edges.get(edges.size()-1).getWeight();
    }

    @Override
    public int compareTo(Graph<T> graph) {
        return Integer.compare(getWeight(), graph.getWeight());
    }
}

