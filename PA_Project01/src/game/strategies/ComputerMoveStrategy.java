package game.strategies;

import game.models.Game;
import tads.graph.Edge;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

public interface ComputerMoveStrategy {

    public Edge<Connection, Joint> calculateMove(Game game);
    
}
