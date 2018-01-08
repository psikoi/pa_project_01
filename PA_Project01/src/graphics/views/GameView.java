package graphics.views;

import game.Main;
import game.factories.GameFactory;
import game.models.ComputerGame;
import game.models.Game;
import game.models.GameDifficulty;
import game.models.User;
import graphics.Presenter;
import graphics.View;
import graphics.custom.EdgeLine;
import graphics.presenters.GamePresenter;
import graphics.views.menus.HomeScreenMenu;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tads.graph.Edge;
import tads.graph.Vertex;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

public class GameView extends Pane implements View {

    private Game game;

    private ArrayList<EdgeLine> edgeLines;
    private Button undoButton;

    private Button mainMenu;
    private Button newGame;
    
    private Label endingLabel;

    public GameView(Game game) {

        setStyle("-fx-background-color: #e6c990;");

        this.game = game;

        this.edgeLines = new ArrayList<>();
        for (Edge<Connection, Joint> edge : game.getBoard().getGraph().edges()) {
            EdgeLine line = new EdgeLine(edge);
            edgeLines.add(line);
            getChildren().add(line);
        }

        for (Vertex<Joint> vertex : game.getBoard().getGraph().vertices()) {
            Circle c = new Circle(vertex.element().getX(), vertex.element().getY(), 5);
            c.setFill(Color.web("#a58d5e"));
            getChildren().add(c);
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

            if (line.getConnection().element().isSelected()) {
                line.select();
            }

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

        onEndDisplayButtons();
        onEndDisplayLabel();
    }

    public void onEndDisplayButtons() {
        mainMenu = new Button("Menu principal");
        mainMenu.setTranslateY(game.getMaxWidth() - 50);

        mainMenu.setOnAction(e -> {
            Main.switchContent(new HomeScreenMenu());
        });

        newGame = new Button("Novo jogo");
        newGame.setTranslateX(150);

        newGame.setTranslateY(game.getMaxWidth() - 50);

        newGame.setOnAction(e -> {
            if (game instanceof ComputerGame) {
                if (((ComputerGame) game).isDifficultyEasy()) {
                    startGame(new GameFactory().create(GameDifficulty.EASY));
                }else{
                    startGame(new GameFactory().create(GameDifficulty.HARD));
                }
            }else{
                startGame(new GameFactory().create(null));
            }
        });

        getChildren().add(mainMenu);
        getChildren().add(newGame);
    }
    
    public void onEndDisplayLabel(){
        endingLabel = new Label();
        
        if(game instanceof ComputerGame){
            if(game.getInactivePlayer() instanceof User){
                endingLabel.setText("Você ganhou!");
            }else{
                endingLabel.setText("Você perdeu.");
            }
        }else{
            String username = ((User)game.getInactivePlayer()).getUsername();
            endingLabel.setText(username + " ganhou!");
        }
        
        endingLabel.setTranslateY(50);
        endingLabel.setTranslateX( (game.getMaxWidth() / 2) - 100);
        endingLabel.setAlignment(Pos.CENTER);
        endingLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        getChildren().add(endingLabel);
    }

    private void startGame(Game game) {
        game.start();
        GamePresenter p = new GamePresenter(game, new GameView(game));
        Main.switchContent(p.getView());
    }

}
