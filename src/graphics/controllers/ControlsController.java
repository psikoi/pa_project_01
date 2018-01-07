package graphics.controllers;

import game.GameSystem;
import game.models.Game;
import graphics.Controller;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControlsController implements Controller {

    private Game game;

    public ControlsController(Game game) {
        this.game = game;
    }

    @Override
    public void render(Pane pane) {
        Button test = new Button("Test");

        test.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameSystem.game.undoMove(GameSystem.game.getInactivePlayer());
            }
        });
        test.setDisable(!GameSystem.game.canUndo(GameSystem.game.getInactivePlayer()));

        test.setTranslateY(pane.getHeight() - 50);
        test.setTranslateX(pane.getWidth() - 80);


        pane.getChildren().add(test);
    }

}
