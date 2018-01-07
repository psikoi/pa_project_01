package game.models;

import java.io.Serializable;

/**
 * Represents the player who will take part in any form of game.
 * Contains general information about the player. It's the parent class to 
 * User and Machine.
 * 
 * @author Tiago
 * @author Ruben
 */
public class Player implements Serializable{
    
    /**
     * Statistics about this player.
     */
    private int gamesPlayed, totalVictories, totalLosses, bestScore;
    private long timePlayed;
     

    /**
     * Returns the number of games this player has played.
     * 
     * @return number of games played by the player.
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Sets the number of games played to the specified number.
     * 
     * @param gamesPlayed new value of games played.
     */
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
    
    public void addGamePlayed(){
        this.gamesPlayed++;
    }
    
    public int getTotalLosses(){
        return totalLosses;
    }
    
    public void addLoss(){
        totalLosses++;
    }
    
    public void setLosses(int losses){
        this.totalLosses = losses;
    }
    
    public long getTimePlayed(){
        return timePlayed;
    }
    
    public void addTimePlayed(long time){
        timePlayed += time;
    }
    
    public void setTimePlayed(long time){
        timePlayed = time;
    }

    /**
     * Returns the total victories of a player.
     * 
     * @return number of victories of a player. 
     */
    public int getTotalVictories() {
        return totalVictories;
    }

    /**
     * Sets the number of total victories to the specified one.
     * 
     * @param totalVictories new value of total victories.
     */
    public void setTotalVictories(int totalVictories) {
        this.totalVictories = totalVictories;
    }

    public void addVictory(){
        this.totalVictories++;
    }
    
    /**
     * Returns the best score of a player.
     * 
     * @return player's best score.
     */
    public int getBestScore() {
        return bestScore;
    }

    /**
     * Sets the best score to the specified one.
     * 
     * @param bestScore new value of the best score of a player.
     */
    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    
    
}
