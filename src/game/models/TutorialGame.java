package game.models;

import tads.graph.Edge;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

/**
 * TutorialGame is the class that sets the behaviour of the tutorial. 
 * It extends from ComputerGame, but some of the methods have differences that 
 * separate a regular game from the tutorial.
 * 
 * @author Tiago
 * @author Ruben
 */
public class TutorialGame extends ComputerGame{
    
    /**
     * Constructs a new TutorialGame with a specified Player. 
     * 
     * @param player1 player who will take part in the game.
     */
    public TutorialGame(Player player1, Machine machine){
        super(player1, machine, 1);
    }

    /**
     * Overriden from ComputerGame. 
     * Adds certain characteristics to make the game a Tutorial.
     * 
     * @param selected selected Edge.
     */
    @Override
    public void play(Edge<Connection, Joint> selected) {
        super.play(selected); 
        
        //dependo do getRounds().size (nº de ronda)
        //fazer certas açoes
        
    }
    
    
    
}
