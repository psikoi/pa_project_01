package game.models;

import game.factories.ComputerMoveStrategyFactory;
import game.strategies.ComputerMoveStrategy;
import game.strategies.moves.EasyComputerMove;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import tads.graph.Edge;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

public class ComputerGame extends Game {

    private boolean thinking;
    
    private ComputerMoveStrategy moveStrategy;

    public ComputerGame(Player player1, Machine machine, 
            int level, int maxWidth, GameDifficulty difficulty) {
        super(player1, machine, level, maxWidth);
        this.moveStrategy  = new ComputerMoveStrategyFactory().make(difficulty);
    }


    public Edge<Connection, Joint> getNextMove() {
        return moveStrategy.calculateMove(this);
    }

    public boolean isThinking() {
        return thinking;
    }

    public void setThinking(boolean thinking) {
        this.thinking = thinking;
    }
    
    

}
