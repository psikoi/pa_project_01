package game.models;

import java.util.ArrayList;
import java.util.HashSet;
import tads.graph.Edge;
import tads.graph.MyGraph;
import tads.graph.Vertex;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

public class Board extends MyGraph<Joint, Connection> {

    private int width;

    public Board(int width) {
        this.width = width;
    }

    public void generate(int size) {

        int num = size;
        int startX = width / 2;
        int startY = width / 2;
        int edgeWidth = (width / 2) - 100;

        for (int i = 0; i < num; i++) {
            int x = (int) (startX + edgeWidth * Math.cos(i * 2 * Math.PI / num));
            int y = (int) (startY + edgeWidth * Math.sin(i * 2 * Math.PI / num));
            insertVertex(new Joint(x, y));
        }

        for (Vertex<Joint> v : vertices()) {
            for (Vertex<Joint> v2 : vertices()) {
                if (!v2.equals(v) && !areAdjacent(v, v2)) {
                    insertEdge(v, v2, new Connection());
                }

            }
        }

    }

    public ArrayList<Edge<Connection, Joint>> checkMove(Player player, Edge<Connection, Joint> selected) {

        ArrayList<Edge<Connection, Joint>> edges = new ArrayList<>();

        for (Edge<Connection, Joint> incident : incidentEdges(selected.vertices()[0])) {

            Player selector = incident.element().getSelector();

            if (selector == null || !selector.equals(player)) {
                continue;
            }

            Vertex<Joint> opposite = opposite(selected.vertices()[0], incident);

            for (Edge<Connection, Joint> oppositeIncident : incidentEdges(opposite)) {

                Player selector2 = oppositeIncident.element().getSelector();

                if (selector2 == null || !selector2.equals(player)) {
                    continue;
                }

                if (opposite(opposite, oppositeIncident) == selected.vertices()[1]) {
                    edges.add(selected);
                    edges.add(incident);
                    edges.add(oppositeIncident);
                    break;
                }

            }
        }

        return edges;
    }

    public ArrayList<Edge<Connection, Joint>> checkAnyTriangle() {

        ArrayList<Edge<Connection, Joint>> edges = new ArrayList<>();

        for (Edge<Connection, Joint> selected : edges()) {

            for (Edge<Connection, Joint> incident : incidentEdges(selected.vertices()[0])) {

                Player selector = incident.element().getSelector();

                if (selector == null || !selector.equals(selected.element().getSelector())) {
                    continue;
                }

                Vertex<Joint> opposite = opposite(selected.vertices()[0], incident);

                for (Edge<Connection, Joint> oppositeIncident : incidentEdges(opposite)) {

                    Player selector2 = oppositeIncident.element().getSelector();

                    if (selector2 == null || !selector2.equals(selected.element().getSelector())) {
                        continue;
                    }

                    if (opposite(opposite, oppositeIncident) == selected.vertices()[1]) {
                        edges.add(selected);
                        edges.add(incident);
                        edges.add(oppositeIncident);
                        return edges;
                    }
                }
            }
        }

        return edges;
    }

    public Edge<Connection, Joint> find(Connection connection) {
        for (Edge<Connection, Joint> edge : this.edges()) {
            if (edge.element().equals(connection)) {
                return edge;
            }
        }
        return null;
    }

    public Edge<Connection, Joint> findIdentical(Edge<Connection, Joint> edge) {

        for (Edge<Connection, Joint> e : this.edges()) {

            Joint j1 = e.vertices()[0].element();
            Joint j2 = e.vertices()[1].element();

            Joint t1 = edge.vertices()[0].element();
            Joint t2 = edge.vertices()[1].element();

            if (j1.getX() == t1.getX() && j1.getY() == t1.getY() && j2.getX() == t2.getX() && j2.getY() == t2.getY()) {
                return e;
            }

        }
        return null;
    }

    public Vertex<Joint> findIdentical(Vertex<Joint> joint) {
        for (Vertex<Joint> vertex : vertices()) {
            Joint j = vertex.element();
            Joint m = joint.element();
            if (j.getX() == m.getX() && j.getY() == m.getY()) {
                return vertex;
            }
        }
        return null;
    }

    public ArrayList<Edge<Connection, Joint>> getPossibleMoves() {

        ArrayList<Edge<Connection, Joint>> possibleMoves = new ArrayList<>();

        for (Edge<Connection, Joint> edge : edges()) {
            if (!edge.element().isSelected()) {
                possibleMoves.add(edge);
            }

        }

        return possibleMoves;

    }

    public int numSelectedEdges() {
        int count = 0;
        for (Edge<Connection, Joint> e : edges()) {
            if(e.element().isSelected())
                count++;
        }
        return count;
    }

    public Board copy() {

        Board board = new Board(width);

        for (Vertex<Joint> v : vertices()) {
            board.insertVertex(v.element().copy());
        }

        for (Edge<Connection, Joint> e : edges()) {
            Vertex<Joint> v = e.vertices()[0];
            Vertex<Joint> v2 = e.vertices()[1];
            board.insertEdge(board.findIdentical(v), board.findIdentical(v2), e.element().copy());
        }

        return board;

    }

}
