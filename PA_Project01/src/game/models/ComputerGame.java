package game.models;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import tads.graph.Edge;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

public class ComputerGame extends Game {

    private boolean thinking;

    public ComputerGame(Player player1, Machine machine, int level, int maxWidth) {
        super(player1, machine, level, maxWidth);
    }

    @Override
    public boolean play(Edge<Connection, Joint> selected) {
        super.play(selected);

        if (getActivePlayer() instanceof Machine) {
            thinking = true;
            Timer delay = new Timer();

            delay.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            delayedPlay(delay);
                        }
                    });
                }

            }, 500 + new Random().nextInt(1000));
        }

        return true;

    }

    private void delayedPlay(Timer delay) {
        play(((Machine) getActivePlayer()).getRandomMove(this));
        thinking = false;
        delay.cancel();
    }

    public boolean getThinking() {
        return thinking;
    }

}
