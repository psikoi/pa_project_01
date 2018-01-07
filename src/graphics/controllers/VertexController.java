package graphics.controllers;

import graphics.Controller;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import tads.graph.Vertex;
import tads.graph.model.Joint;

public class VertexController implements Controller {

    private Vertex<Joint> vertex;
    private Circle vertexCircle;
    

    public VertexController(Vertex<Joint> vertex) {
        this.vertex = vertex;
        initShape();
    }

    private void initShape() {
        vertexCircle = new Circle(vertex.element().getX(), vertex.element().getY(), 4);
    }

    @Override
    public void render(Pane pane) {
        initShape();
        vertexCircle.setFill(Color.WHITE);
        vertexCircle.setStroke(Color.BLACK);

        pane.getChildren().add(vertexCircle);


    }

}
