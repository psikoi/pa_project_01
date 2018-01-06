package game.models;

import java.util.ArrayList;
import java.util.Random;
import tads.graph.Edge;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

/**
 * This class represents a Machine. It extends from Player, allowing it to take
 * part in games and make a distinction between the computer and human players.
 * 
 * @author Tiago
 * @author Ruben 
 */

public class Machine extends Player {

    /**
     * Selects a random edge on the board. 
     * 
     * @return randomly selected edge. 
     */
    public Edge<Connection, Joint> getRandomMove(Game game){
        
        Board board = game.getBoard();
        ArrayList<Edge<Connection, Joint>> possibleMoves = new ArrayList<>();
        
        for(Edge<Connection, Joint> edge : board.edges()){
            if(!edge.element().isSelected()){
                possibleMoves.add(edge);
            }
        }
        
        if(!possibleMoves.isEmpty())
            return possibleMoves.get(new Random().nextInt(possibleMoves.size()));
        
        return null;
    }
    
}
