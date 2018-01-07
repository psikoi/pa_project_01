
package tads.graph;

public interface Edge<E,V> {
    public E element()throws InvalidEdgeException;
    public Vertex<V>[] vertices();
}
