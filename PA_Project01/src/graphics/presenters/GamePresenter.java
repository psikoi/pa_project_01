package graphics.presenters;

import game.models.ComputerGame;
import game.models.Game;
import game.models.Machine;
import graphics.Presenter;
import graphics.views.GameView;
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
        
        if(edge.element().isSelected())
            return;

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
        cgame.setThinking(true);

        executePlay(cgame.getNextMove());
        cgame.setThinking(false);
        /*

        Timer delay = new Timer();

        delay.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        executePlay(cgame.getNextMove());
                        cgame.setThinking(false);
                        delay.cancel();
                    }
                });
            }

        }, 500 + new Random().nextInt(1000));*/
    }

    public void undoPlay() {
        Connection undo = model.undoMove();
        if (undo != null) {
            view.changeUndo(model.canUndo(model.getInactivePlayer()));
            view.undoEdge(undo);
        }

    }

    public GameView getView() {
        return view;
    }

}
