package graphics.controllers;

import game.models.Game;
import graphics.Controller;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.scene.layout.Pane;
import tads.graph.model.Joint;

public class GameController implements Controller {

    private BoardController boardController;
    private ControlsController controlsController;

    public GameController(Game game) {
        this.boardController = new BoardController(game.getBoard());
        this.controlsController = new ControlsController(game);
    }

    @Override
    public void render(Pane pane) {

        if (boardController == null) {
            return;
        }

        boardController.render(pane);

        if (controlsController == null) {
            return;
        }

        controlsController.render(pane);

    }

    public void showSolution(HashSet<Joint> joints) {
        boardController.showSolution(joints);
    }

}
