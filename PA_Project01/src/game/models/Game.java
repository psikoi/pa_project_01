package game.models;

import data.DataHandler;
import game.memento.BoardMementoCareTaker;
import java.util.ArrayList;
import tads.graph.Edge;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

/**
 * Represents a Game object.
 * This class is responsible for all of the main game's functionalities.
 * 
 * @author Ruben
 * @author Tiago
 */
public class Game {

    private Board board;
    private int level;

    private Player player1;
    private Player player2;

    private ArrayList<Round> rounds;

    private int activePlayerIndex;

    private int maxWidth;

    private BoardMementoCareTaker mementoCareTaker;

    private ArrayList<Edge<Connection, Joint>> triangleEdges;

    public Game(Player player1, Player player2, int level, int maxWidth) {

        this.board = new Board();
        this.level = level;

        this.maxWidth = maxWidth;

        this.player1 = player1;
        this.player2 = player2;

        this.player1.setPlayerIndex(0);
        this.player1.setPlayerIndex(1);

        this.rounds = new ArrayList<>();

        this.activePlayerIndex = 0;

        this.mementoCareTaker = new BoardMementoCareTaker();

    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * @return (True if the last round was a loosing round, false otherwise)
     */
    public boolean isFinished() {

        if (!hasStarted()) {
            return false;
        }

        return rounds.get(rounds.size() - 1).isLosingRound();
    }

    /**
     * @return (True if the game has started)
     */
    public boolean hasStarted() {
        return rounds != null && !rounds.isEmpty();
    }

    /**
     * @return (The elapsed time, in milliseconds since the beginning of the game)
     */
    public long getElapsedTime() {

        if (!hasStarted()) {
            return 0;
        }

        return System.currentTimeMillis() - rounds.get(0).getStartTimestamp();
    }

    /**
     * @return (The total duration of the game, in milliseconds since the beginning)
     */
    public long getDuration() {

        if (!hasStarted()) {
            return 0;
        }

        if (!isFinished()) {
            return getElapsedTime();
        }

        return getCurrentRound().getEndTimestamp() - rounds.get(0).getStartTimestamp();
    }

    /**
     * @return (The current round, awaiting play)
     */
    public Round getCurrentRound() {

        if (!hasStarted()) {
            return null;
        }

        return rounds.get(rounds.size() - 1);

    }

    /**
     * @return (The player whose turn it is)
     */
    public Player getActivePlayer() {
        return activePlayerIndex == 0 ? player1 : player2;
    }

    /**
     * @return (The player whose turn it isn't)
     */
    public Player getInactivePlayer() {
        return activePlayerIndex == 1 ? player1 : player2;
    }

    /**
     * @param player
     * @return (True if the player hasn't undone a move yet)
     */
    public boolean canUndo(Player player) {
        return player instanceof User && player.getUndoCount() == 0;
    }

    /**
     * Starts a game, reseting the player's undo counts, generating a new
     * board and reseting the rounds.
     */
    public void start() {
        this.player1.setUndoCount(0);
        this.player2.setUndoCount(0);

        this.board.generate(level + 5, maxWidth);
        this.mementoCareTaker.saveState(board);

        this.rounds.clear();
        this.rounds.add(new Round(player1));
    }

    /**
     * Skips a round, adding a new round for the inactive player, switching
     * the turns.
     */
    public void skipRound() {
        rounds.add(new Round(getInactivePlayer()));
        switchPlayerTurn();
    }

    /**
     * Undoes the move of the current inactive player.
     */
    public void undoMove() {
        undoMove(getInactivePlayer());
    }

    /**
     * Undoes a move, if the player can still undo, increasing their
     * undo count.
     * 
     * @param player (
     */
    public void undoMove(Player player) {

        if (isFinished() || rounds.size() < 2 || !canUndo(player)) {
            return;
        }
        
        rounds.remove(rounds.size() - 1);
        switchPlayerTurn();
        
        getActivePlayer().setUndoCount(1);
        mementoCareTaker.restoreState(board);

    }

    /**
     * Switches the player turn variable, switching between 1 and 0.
     */
    private void switchPlayerTurn() {
        if (activePlayerIndex == 1) {
            activePlayerIndex = 0;
        } else {
            activePlayerIndex++;
        }
    }

    /**
     * Executes a play on the game.
     * Saves the current game state before executing, checking the move for
     * game completion.
     * 
     * @param selected (The selected edge)
     * @return (true if executed correctly)
     */
    public boolean play(Edge<Connection, Joint> selected) {

        if (isFinished()) {
            return false;
        }
        
        mementoCareTaker.saveState(board);

        triangleEdges = board.checkMove(getActivePlayer(), selected);

        if (triangleEdges.isEmpty()) {
            getCurrentRound().select(selected.element(), false);
            skipRound();
        } else {
            getCurrentRound().select(selected.element(), true);
            end();
        }


        return true;
    }

    /**
     * Ends the game, saving the game and its statistics.
     */
    public void end() {
        DataHandler.saveGame(this);
    }

    public ArrayList<Edge<Connection, Joint>> getTriangleEdges() {
        return triangleEdges;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

}
