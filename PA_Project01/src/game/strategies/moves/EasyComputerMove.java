package game.strategies.moves;

import game.models.Board;
import game.models.Game;
import game.strategies.ComputerMoveStrategy;
import java.util.ArrayList;
import java.util.Random;
import tads.graph.Edge;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

public class EasyComputerMove implements ComputerMoveStrategy {

    @Override
    public Edge<Connection, Joint> calculateMove(Game game) {
        return getRandomMove(game);
    }

    /**
     * Selects a random edge on the board.
     *
     * @return randomly selected edge.
     */
    public Edge<Connection, Joint> getRandomMove(Game game) {

        Board board = game.getBoard();
        ArrayList<Edge<Connection, Joint>> possibleMoves = new ArrayList<>();

        for (Edge<Connection, Joint> edge : board.edges()) {
            if (!edge.element().isSelected()) {
                possibleMoves.add(edge);
            }
        }

        if (!possibleMoves.isEmpty()) {
            return possibleMoves.get(new Random().nextInt(possibleMoves.size()));
        }

        return null;
    }

}
