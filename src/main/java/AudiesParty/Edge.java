package utils;

public class Edge<T extends Comparable<T>> implements Comparable<Edge<T>>{
    private T src, dest;
    private int weight;

    public Edge(T src, T dest, int weight){
        this.dest = dest;
        this.src = src;
        this.weight = weight;
    }

    public T getSrc(){
        return this.src;
    }

    public T getDest(){
        return this.dest;
    }

    public int getWeight(){
        return this.weight;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge<T> o) {
        return Integer.compare(this.weight, o.weight);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge) {
            Edge<T> edge = (Edge<T>) obj;
            return src.compareTo(edge.src) == 0 && dest.compareTo(edge.dest) == 0;
        }
        return false;
    }
}
