package game.models;

import game.memento.BoardMemento;
import java.util.ArrayList;
import tads.graph.Edge;
import tads.graph.MyGraph;
import tads.graph.Vertex;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

/**
 * Represents a game's board.
 * 
 * ATTENTION: The Inappropriate intimacy bad smell @getGraph() is unavoidable to properly comply
 * with the Memento Design Pattern.
 * 
 * @author Ruben
 * @author Tiago
 */
public class Board {

    private MyGraph<Joint, Connection> graph;

    public Board() {
        this.graph = new MyGraph<>();
    }

    /**
     * Generates a new Board with a multi-graph with "size" amount of vertices.
     * 
     * @param size (The number of vertices to generate)
     * @param width (The maximum screen width, to calculate edgeWidth)
     */
    public void generate(int size, int width) {

        int num = size;
        int startX = width / 2;
        int startY = width / 2;
        int edgeWidth = (width / 2) - 100;

        for (int i = 0; i < num; i++) {
            int x = (int) (startX + edgeWidth * Math.cos(i * 2 * Math.PI / num));
            int y = (int) (startY + edgeWidth * Math.sin(i * 2 * Math.PI / num));
            graph.insertVertex(new Joint(x, y));
        }

        for (Vertex<Joint> v : graph.vertices()) {
            for (Vertex<Joint> v2 : graph.vertices()) {
                if (!v2.equals(v) && !graph.areAdjacent(v, v2)) {
                    graph.insertEdge(v, v2, new Connection());
                }

            }
        }

    }

    /**
     * Checks if a move has completed a triangle in the graph.
     * 
     * @param player (The player who selected)
     * @param selected (The edge that has been selected)
     * @return (An arraylist containing the 3 incident edges, empty list if none found)
     */
    public ArrayList<Edge<Connection, Joint>> checkMove(Player player, Edge<Connection, Joint> selected) {

        ArrayList<Edge<Connection, Joint>> edges = new ArrayList<>();

        for (Edge<Connection, Joint> incident : graph.incidentEdges(selected.vertices()[0])) {

            Player selector = incident.element().getSelector();

            if (selector == null || !selector.equals(player)) {
                continue;
            }

            Vertex<Joint> opposite = graph.opposite(selected.vertices()[0], incident);

            for (Edge<Connection, Joint> oppositeIncident : graph.incidentEdges(opposite)) {

                Player selector2 = oppositeIncident.element().getSelector();

                if (selector2 == null || !selector2.equals(player)) {
                    continue;
                }

                if (graph.opposite(opposite, oppositeIncident) == selected.vertices()[1]) {
                    edges.add(selected);
                    edges.add(incident);
                    edges.add(oppositeIncident);
                    return edges;
                }

            }
        }

        return edges;
    }

    /**
     * Finds an edge containing a given connection.
     * 
     * @param connection (The connection to search for)
     * @return (The edge, if found)
     */
    public Edge<Connection, Joint> find(Connection connection) {
        for (Edge<Connection, Joint> edge : graph.edges()) {
            if (edge.element().equals(connection)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Finds identical edge in the graph, meaning it will find an edge
     * with the same vertices.
     *  
     * @param edge (To compare)
     * @return (The edge if found, null otherwise)
     */
    public Edge<Connection, Joint> findIdentical(Edge<Connection, Joint> edge) {

        for (Edge<Connection, Joint> e : graph.edges()) {

            Joint j1 = e.vertices()[0].element();
            Joint j2 = e.vertices()[1].element();

            Joint t1 = edge.vertices()[0].element();
            Joint t2 = edge.vertices()[1].element();

            if (j1.getX() == t1.getX() && j1.getY() == t1.getY() 
                    && j2.getX() == t2.getX() && j2.getY() == t2.getY()) {
                return e;
            }

        }
        return null;
    }

    /**
     * Finds identical vertex in the graph, meaning it will find a vertex
     * with the same coordinates.
     *  
     * @param joint (To compare)
     * @return (The vertex if found, null otherwise)
     */
    public Vertex<Joint> findIdentical(Vertex<Joint> joint) {
        
        for (Vertex<Joint> vertex : graph.vertices()) {
            
            Joint j = vertex.element();
            Joint m = joint.element();
            
            if (j.getX() == m.getX() && j.getY() == m.getY()) {
                return vertex;
            }
            
        }
        return null;
    }

    /**
     * @return (All unselected edges)
     */
    public ArrayList<Edge<Connection, Joint>> getPossibleMoves() {

        ArrayList<Edge<Connection, Joint>> possibleMoves = new ArrayList<>();

        for (Edge<Connection, Joint> edge : graph.edges()) {
            if (!edge.element().isSelected()) {
                possibleMoves.add(edge);
            }

        }

        return possibleMoves;

    }

    /**
     * @return (Number of selected edges)
     */
    public int numSelectedEdges() {
        int count = 0;
        for (Edge<Connection, Joint> e : graph.edges()) {
            if (e.element().isSelected()) {
                count++;
            }
        }
        return count;
    }

    /**
     * @return (A copy of this board, with copies of it's attributes)
     */
    public Board copy() {

        Board board = new Board();

        for (Vertex<Joint> v : graph.vertices()) {
            board.getGraph().insertVertex(v.element().copy());
        }

        for (Edge<Connection, Joint> e : graph.edges()) {
            Vertex<Joint> v = e.vertices()[0];
            Vertex<Joint> v2 = e.vertices()[1];
            board.getGraph().insertEdge(board.findIdentical(v), board.findIdentical(v2), e.element().copy());
        }

        return board;

    }

    public Board createMemento() {
        return copy();
    }

    public void setMemento(BoardMemento memento) {
        this.graph = memento.getFigure().getGraph();
    }

    public MyGraph<Joint, Connection> getGraph() {
        return graph;
    }

}
