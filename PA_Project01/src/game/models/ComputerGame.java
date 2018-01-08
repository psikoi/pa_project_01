package game.models;

import game.factories.ComputerMoveStrategyFactory;
import game.strategies.ComputerMoveStrategy;
import game.strategies.moves.EasyComputerMove;
import tads.graph.Edge;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

public class ComputerGame extends Game {
    
    private ComputerMoveStrategy moveStrategy;

    public ComputerGame(Player player1, Machine machine, 
            int level, int maxWidth, GameDifficulty difficulty) {
        super(player1, machine, level, maxWidth);
        this.moveStrategy  = new ComputerMoveStrategyFactory().make(difficulty);
    }


    public Edge<Connection, Joint> getNextMove() {
        return moveStrategy.calculateMove(this);
    }
    
    public boolean isDifficultyEasy(){
        return (moveStrategy instanceof EasyComputerMove);
    }
    
    

}
