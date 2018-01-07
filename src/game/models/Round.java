package game.models;

import game.GameSystem;
import tads.graph.model.Connection;

/**
 * Represents a single Round in a game. When a user makes his play, a new 
 * Round is created containing the information on which Player played, the 
 * Edge that was selected, whether or not the game ended and the timestamps
 * of the beginning and the end of the Round.
 * 
 * @author Tiago
 * @author Ruben
 */

public class Round {
    
    /**
     * Player who made his move and who the round belongs to.
     */
    private Player player;
    
   /**
    * Edge selected by the player.
    */
    private Connection selectedEdge;
    
    /**
     * Whether or not this round finished the game.
     */
    private boolean losingRound;

    /**
     * Timestamp that keeps the time of the beginning of the round.
     */
    private long startTimestamp;
    
    /**
     * Timestamp that keeps the time of the end of the round.
     */
    private long endTimestamp;

    /**
     * Constructs a new Round with the player who will make the move.
     * Sets selectedEdge and losingRound as null and false respectively, though
     * they are susceptible of change when the round ends.
     * Sets startTimestamp as the time when the round is created.
     * 
     * @param player player who will make the next move in the game.
     */
    public Round(Player player) {
        this.player = player;
        this.selectedEdge = null;
        this.losingRound = false;
        this.startTimestamp = System.currentTimeMillis();
    }

    /**
     * Returns player.
     * 
     * @return player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the selected edge.
     * 
     * @return selected edge.
     */
    public Connection getSelectedEdge() {
        return selectedEdge;
    }

    /**
     * Returns information about whether or not the game ended.
     * 
     * @return boolean that indicates if this round ended the game.
     */
    public boolean isLosingRound() {
        return losingRound;
    }

    
    /**
     * Returns the time the round started.
     * 
     * @return time of beginning of the round.
     */
    public long getStartTimestamp() {
        return startTimestamp;
    }

    /**
     * Returns the time of the end of the round.
     * 
     * @return time in which the round ended.
     */
    public long getEndTimestamp() {
        return endTimestamp;
    }

    /**
     * Alters the selectedEdge and losingRound attributes. 
     * Recives the edge the player selected for his round, and information about
     * whether the game ended or is still ongoing. 
     * Sets endTimestamp to the time the round ended.
     * 
     * @param selected edge selected by the player.
     * @param losingRound whether or not the round was the losing round of the game.
     */
    public void select(Connection selected, boolean losingRound) {
        
        if(this.selectedEdge != null){
            this.selectedEdge.select(null);
        }
        
        this.selectedEdge = selected;

        if (selected != null) {
            this.selectedEdge.select(player);
        }
        
        

        this.losingRound = losingRound;
        this.endTimestamp = System.currentTimeMillis();
    }

    /**
     * Returns a string containing readable information about the round.
     * @return information about the round in a readable String.
     */
    @Override
    public String toString() {
        return "Player: " + GameSystem.game.getPlayerIndex(player) + " " + (selectedEdge == null ? "N/A" : "-");
    }

}
