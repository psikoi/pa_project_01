/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.statistics;

import java.io.Serializable;

/**
 *
 * @author Tiago
 */
public class Statistics implements Serializable {
    private int gamesPlayed, victories, losses;
    private long timePlayed;
    
    public Statistics(){
        gamesPlayed = 0;
        victories = 0;
        losses = 0;
        timePlayed = 0;
    }
    
    public int getGamesPlayed(){
        return gamesPlayed;
    }
    
    public int getVictories(){
        return victories;
    }
    
    public int getLosses(){
        return losses;
    }
    
    public long getTimePlayed(){
        return timePlayed;
    }
    
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setTimePlayed(long timePlayed) {
        this.timePlayed = timePlayed;
    }
    
    public void addGamePlayed(){
        gamesPlayed++;
    }
    
    public void addVictory(){
        victories++;
    }
    
    public void addLoss(){
        losses++;
    }
    
    public void addTimePlayed(long timePlayed){
        this.timePlayed += timePlayed;
    }
    
}
