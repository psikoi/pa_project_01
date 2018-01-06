package game.models;

import graphics.controllers.BoardController;
import java.util.ArrayList;
import tads.graph.Edge;
import tads.graph.MyGraph;
import tads.graph.Vertex;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

public class Board extends MyGraph<Joint, Connection> {

    private int width;
    private BoardController controller;

    public Board(int width) {
        this.width = width;
        this.controller = new BoardController(this);
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

}
