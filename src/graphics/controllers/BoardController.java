package graphics.controllers;

import game.models.Board;
import graphics.Controller;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.scene.layout.Pane;
import tads.graph.Edge;
import tads.graph.Vertex;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

public class BoardController implements Controller {

    private ArrayList<EdgeController> edgeControllers;
    private ArrayList<VertexController> vertexControllers;

    private SolutionController solutionController;

    public BoardController(Board board) {
        this.edgeControllers = new ArrayList<>();
        this.vertexControllers = new ArrayList<>();

        for (Vertex<Joint> vertex : board.vertices()) {
            vertexControllers.add(new VertexController(vertex));
        }

        for (Edge<Connection, Joint> edge : board.edges()) {
            edgeControllers.add(new EdgeController(edge));
        }

    }

    @Override
    public void render(Pane pane) {

        for (EdgeController edgeController : edgeControllers) {
            edgeController.render(pane);
        }

        for (VertexController vertexController : vertexControllers) {
            vertexController.render(pane);
        }

        if (solutionController != null) {
            solutionController.render(pane);
        }

    }

    public void showSolution(HashSet<Joint> joints) {
        solutionController = new SolutionController(joints);
    }

}
