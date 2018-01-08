package game.models;

import game.statistics.*;
import java.io.Serializable;

/**
 * Represents the player who will take part in any form of game. Contains
 * general information about the player. It's the parent class to User and
 * Machine.
 *
 * @author Tiago
 * @author Ruben
 */
public class Player implements Serializable {

    /**
     * Statistics about this player.
     */
    protected EasyStatistics easyStatistics;
    protected HardStatistics hardStatistics;

    private int playerIndex;
    private int undoCount;

    public Player() {
        this.easyStatistics = new EasyStatistics();
        this.hardStatistics = new HardStatistics();
    }

    public int getUndoCount() {
        return undoCount;
    }

    public void setUndoCount(int undoCount) {
        this.undoCount = undoCount;
    }

    /**
     * Returns the number of games this player has played.
     *
     * @return number of games played by the player.
     */
    public int getEasyGamesPlayed() {
        return easyStatistics.getGamesPlayed();
    }

    /**
     * Sets the number of games played to the specified number.
     *
     * @param gamesPlayed new value of games played.
     */
    public void setEasyGamesPlayed(int gamesPlayed) {
        this.easyStatistics.setGamesPlayed(gamesPlayed);
    }

    public void addEasyGamePlayed() {
        easyStatistics.addGamePlayed();
    }

    public int getEasyLosses() {
        return easyStatistics.getLosses();
    }

    public void addEasyLoss() {
        easyStatistics.addLoss();
    }

    public void setEasyLosses(int losses) {
        easyStatistics.setLosses(losses);
    }

    public long getEasyTimePlayed() {
        return easyStatistics.getTimePlayed();
    }

    public void addEasyTimePlayed(long time) {
        easyStatistics.addTimePlayed(time);
    }

    public void setEasyTimePlayed(long time) {
        easyStatistics.setTimePlayed(time);
    }

    /**
     * Returns the total victories of a player.
     *
     * @return number of victories of a player.
     */
    public int getEasyVictories() {
        return easyStatistics.getVictories();
    }

    /**
     * Sets the number of total victories to the specified one.
     *
     * @param victories new value of total victories.
     */
    public void setEasyVictories(int victories) {
        easyStatistics.setVictories(victories);
    }

    public void addEasyVictory() {
        easyStatistics.addVictory();
    }

    public int getHardGamesPlayed() {
        return hardStatistics.getGamesPlayed();
    }

    public void setHardGamesPlayed(int gamesPlayed) {
        this.hardStatistics.setGamesPlayed(gamesPlayed);
    }

    public void addHardGamePlayed() {
        hardStatistics.addGamePlayed();
    }

    public int getHardLosses() {
        return hardStatistics.getLosses();
    }

    public void addHardLoss() {
        hardStatistics.addLoss();
    }

    public void setHardLosses(int losses) {
        hardStatistics.setLosses(losses);
    }

    public long getHardTimePlayed() {
        return hardStatistics.getTimePlayed();
    }

    public void addHardTimePlayed(long time) {
        hardStatistics.addTimePlayed(time);
    }

    public void setHardTimePlayed(long time) {
        hardStatistics.setTimePlayed(time);
    }

    public int getHardVictories() {
        return hardStatistics.getVictories();
    }

    public void setHardVictories(int victories) {
        hardStatistics.setVictories(victories);
    }

    public void addHardVictory() {
        hardStatistics.addVictory();
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

}
