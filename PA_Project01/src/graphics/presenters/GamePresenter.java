package graphics.presenters;

import game.models.ComputerGame;
import game.models.Game;
import game.models.Machine;
import graphics.Presenter;
import graphics.views.GameView;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import tads.graph.Edge;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

public class GamePresenter implements Presenter {

    private Game model;
    private GameView view;

    public GamePresenter(Game model, GameView view) {
        this.model = model;
        this.view = view;
        this.view.setTriggers(this);
    }

    public void executePlay(Edge<Connection, Joint> edge) {

        if (edge.element().isSelected()) {
            return;
        }

        if (model.play(edge)) {
            view.selectEdge(edge);
            view.changeUndo(model.canUndo(edge.element().getSelector()));

            if (model.isFinished()) {
                view.displaySolution(model.getTriangleEdges());
            } else {

                if (model.getActivePlayer() instanceof Machine
                        && model instanceof ComputerGame) {
                    automatedPlay();
                }

            }
        }

    }

    private void automatedPlay() {
        ComputerGame cgame = (ComputerGame) model;

        Timer delay = new Timer();

        delay.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (model.getActivePlayer() instanceof Machine) {
                            executePlay(cgame.getNextMove());
                        }
                        delay.cancel();
                    }
                });
            }

        }, 500 + new Random().nextInt(1000));
    }

    public void undoPlay() {
        model.undoMove();
        view.reevaluateEdges(model);
        view.setTriggers(this);
        view.changeUndo(model.canUndo(model.getActivePlayer()));
    }

    public GameView getView() {
        return view;
    }

}
