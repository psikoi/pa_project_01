package game.strategies.moves;

import game.models.Game;
import game.strategies.ComputerMoveStrategy;
import tads.graph.Edge;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

public class HardComputerMove implements ComputerMoveStrategy{

    @Override
    public Edge<Connection, Joint> calculateMove(Game game) {
        return null;
    }

}
