package graphics.custom;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import tads.graph.Edge;
import tads.graph.Vertex;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

public class EdgeLine extends Line {

    private final Color DEFAULT_COLOR = Color.web("#a58d5e");
    private final Color HOVER_COLOR = Color.web("#edd6aa");

    private final Color FIRST_SELECT_COLOR = Color.rgb(65, 135, 210);
    private final Color SECOND_SELECT_COLOR = Color.rgb(210, 40, 40);

    private final int DEFAULT_LINE_WIDTH = 3;
    private final int SELECTED_LINE_WIDTH = 5;
    private final int HOVER_LINE_WIDTH = 5;

    private Edge<Connection, Joint> connection;

    public EdgeLine(Edge<Connection, Joint> connection) {
        this.connection = connection;
        initShapes(connection);

        setOnMouseEntered((event) -> {
            if (!connection.element().isSelected()) {
                setStrokeWidth(HOVER_LINE_WIDTH);
                setStroke(HOVER_COLOR);
            }
        });

        setOnMouseExited((event) -> {
            if (!connection.element().isSelected()) {
                setStrokeWidth(DEFAULT_LINE_WIDTH);
                setStroke(DEFAULT_COLOR);
            }
        });
    }

    private void initShapes(Edge<Connection, Joint> connection) {

        Vertex<Joint>[] vertices = connection.vertices();

        setStartX(vertices[0].element().getX());
        setStartY(vertices[0].element().getY());

        setEndX(vertices[1].element().getX());
        setEndY(vertices[1].element().getY());

        setStrokeWidth(DEFAULT_LINE_WIDTH);
        setStroke(DEFAULT_COLOR);

    }

    public void select() {
        setStroke(connection.element().getSelector().getPlayerIndex()
                == 1 ? FIRST_SELECT_COLOR : SECOND_SELECT_COLOR);
        setStrokeWidth(SELECTED_LINE_WIDTH);
    }

    public void unselect() {
        setStroke(DEFAULT_COLOR);
        setStrokeWidth(DEFAULT_LINE_WIDTH);
    }

    public Edge<Connection, Joint> getConnection() {
        return connection;
    }

}
