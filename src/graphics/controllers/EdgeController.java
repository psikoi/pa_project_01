package graphics.controllers;

import game.GameSystem;
import game.Main;
import game.models.ComputerGame;
import game.models.Player;
import graphics.Controller;
import java.awt.Point;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import tads.graph.Edge;
import tads.graph.Vertex;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

public class EdgeController implements Controller {

    private Edge<Connection, Joint> edge;

    private Line visibleLine;
    private Line clickableLine;

    private final Color DEFAULT_COLOR = Color.rgb(50, 50, 50);
    private final Color HOVER_COLOR = Color.rgb(0, 0, 0);

    private final Color FIRST_SELECT_COLOR = Color.rgb(65, 135, 210);
    private final Color SECOND_SELECT_COLOR = Color.rgb(210, 40, 40);

    private final int DEFAULT_LINE_WIDTH = 1;
    private final int SELECTED_LINE_WIDTH = 3;
    private final int HOVER_LINE_WIDTH = 3;
    private final int CLICKABLE_LINE_WIDTH = 5;

    public EdgeController(Edge<Connection, Joint> edge) {
        this.edge = edge;
        initShapes();
    }

    @Override
    public void render(Pane pane) {

        initShapes();

        Player selector = edge.element().getSelector();
        if (selector != null) {

            visibleLine.setStroke(
                    GameSystem.game.getPlayerIndex(selector) == 0
                    ? FIRST_SELECT_COLOR
                    : SECOND_SELECT_COLOR);

            visibleLine.setStrokeWidth(SELECTED_LINE_WIDTH);
        } else {
            visibleLine.setStroke(DEFAULT_COLOR);
            visibleLine.setStrokeWidth(DEFAULT_LINE_WIDTH);
        }

        pane.getChildren().add(visibleLine);
        pane.getChildren().add(clickableLine);

    }

    private void initShapes() {

        Vertex<Joint>[] vertices = edge.vertices();

        visibleLine = new Line(
                vertices[0].element().getX(),
                vertices[0].element().getY(),
                vertices[1].element().getX(),
                vertices[1].element().getY());

        clickableLine = new Line(
                vertices[0].element().getX(),
                vertices[0].element().getY(),
                vertices[1].element().getX(),
                vertices[1].element().getY());

        clickableLine.setOpacity(0);
        clickableLine.setStrokeWidth(CLICKABLE_LINE_WIDTH);

        initMouseListener();
    }

    private void initMouseListener() {

        clickableLine.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {

                if (edge.element().isSelected()) {
                    return;
                }
                
                if(GameSystem.game instanceof ComputerGame){
                    if(((ComputerGame) GameSystem.game).getThinking()){
                        return;
                    }
                }

                visibleLine.setStroke(
                        GameSystem.game.getPlayerIndex(GameSystem.game.getActivePlayer())
                        == 0 ? FIRST_SELECT_COLOR : SECOND_SELECT_COLOR);

                visibleLine.setStrokeWidth(SELECTED_LINE_WIDTH);

                GameSystem.game.play(edge);

            }
        });

        clickableLine.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {

                if (edge.element().isSelected() || GameSystem.game.isFinished()) {
                    return;
                }

                visibleLine.setStroke(HOVER_COLOR);
                visibleLine.setStrokeWidth(HOVER_LINE_WIDTH);

            }
        });

        clickableLine.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {

                if (edge.element().isSelected()) {
                    return;
                }

                visibleLine.setStroke(DEFAULT_COLOR);
                visibleLine.setStrokeWidth(DEFAULT_LINE_WIDTH);
            }
        });
    }

}
