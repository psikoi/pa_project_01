package graphics.views;

import game.models.Game;
import game.models.User;
import graphics.Presenter;
import graphics.View;
import graphics.custom.EdgeLine;
import graphics.presenters.GamePresenter;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import tads.graph.Edge;
import tads.graph.Vertex;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

public class GameView extends Pane implements View {

    private Game game;

    private ArrayList<EdgeLine> edgeLines;
    private Button undoButton;

    public GameView(Game game) {

        this.game = game;

        this.edgeLines = new ArrayList<>();
        for (Edge<Connection, Joint> edge : game.getBoard().getGraph().edges()) {
            EdgeLine line = new EdgeLine(edge);
            edgeLines.add(line);
            getChildren().add(line);
        }

        for (Vertex<Joint> vertex : game.getBoard().getGraph().vertices()) {
            getChildren().add(new Circle(vertex.element().getX(), vertex.element().getY(), 5));
        }

        undoButton = new Button("Undo move");
        undoButton.setTranslateX(game.getMaxWidth() - 100);
        undoButton.setTranslateY(game.getMaxWidth() - 50);
        getChildren().add(undoButton);

    }

    @Override
    public void setTriggers(Presenter presenter) {

        GamePresenter gamePresenter = (GamePresenter) presenter;

        int i = 0;
        for (Edge<Connection, Joint> edge : game.getBoard().getGraph().edges()) {
            edgeLines.get(i).setOnMouseClicked((event) -> {
                if (game.getActivePlayer() instanceof User) {
                    gamePresenter.executePlay(edge);
                }
            });
            i++;
        }

        undoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gamePresenter.undoPlay();
            }
        });

    }

    public void reevaluateEdges(Game game) {
        for (EdgeLine line : edgeLines) {
            getChildren().remove(line);
        }

        this.edgeLines = new ArrayList<>();
        for (Edge<Connection, Joint> edge : game.getBoard().getGraph().edges()) {
            EdgeLine line = new EdgeLine(edge);
            
            if(line.getConnection().element().isSelected())
                line.select();
            
            edgeLines.add(line);
            getChildren().add(line);
        }
    }

    public void selectEdge(Edge<Connection, Joint> connection) {
        for (EdgeLine line : edgeLines) {
            if (line.getConnection().equals(connection)) {
                line.select();
            }
        }
    }

    public void undoEdge(Connection connection) {
        for (EdgeLine line : edgeLines) {
            if (line.getConnection().element().equals(connection)) {
                line.unselect();
            }
        }
    }

    public void changeUndo(boolean enable) {
        undoButton.setDisable(!enable);
    }

    public void displaySolution(ArrayList<Edge<Connection, Joint>> triangleEdges) {

        HashSet<Joint> joints = new HashSet<>();

        for (Edge<Connection, Joint> edge : triangleEdges) {
            for (Vertex<Joint> v : edge.vertices()) {
                joints.add(v.element());
            }
        }

        Polygon polygon = new Polygon();

        for (Joint joint : joints) {
            polygon.getPoints().addAll(new Double[]{(double) joint.getX(), (double) joint.getY()});
        }

        polygon.setFill(new Color(0, 0, 0, .15));
        getChildren().add(polygon);

    }

}
