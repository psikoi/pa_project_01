package game.models;

import data.DataHandler;
import game.memento.BoardMementoCareTaker;
import java.util.ArrayList;
import tads.graph.Edge;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

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

        this.board = new Board(maxWidth);
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

    public boolean isFinished() {

        if (!hasStarted()) {
            return false;
        }

        return rounds.get(rounds.size() - 1).isLosingRound();
    }

    public boolean hasStarted() {
        return rounds != null && !rounds.isEmpty();
    }

    public long getElapsedTime() {

        if (!hasStarted()) {
            return 0;
        }

        return System.currentTimeMillis() - rounds.get(0).getStartTimestamp();
    }

    public long getDuration() {

        if (!hasStarted()) {
            return 0;
        }

        if (!isFinished()) {
            return getElapsedTime();
        }

        return getCurrentRound().getEndTimestamp() - rounds.get(0).getStartTimestamp();
    }

    public Round getCurrentRound() {

        if (!hasStarted()) {
            return null;
        }

        return rounds.get(rounds.size() - 1);

    }

    public Player getActivePlayer() {
        return activePlayerIndex == 0 ? player1 : player2;
    }

    public Player getInactivePlayer() {
        return activePlayerIndex == 1 ? player1 : player2;
    }

    public boolean canUndo(Player player) {
        return player instanceof User && player.getUndoCount() == 0;
    }

    public void start() {
        this.player1.setUndoCount(0);
        this.player2.setUndoCount(0);

        this.board.generate(level + 5);
        this.mementoCareTaker.saveState(board);

        this.rounds.clear();
        this.rounds.add(new Round(player1));
    }

    public void skipRound() {
        rounds.add(new Round(getInactivePlayer()));
        switchPlayerTurn();
    }

    public void undoMove() {
        undoMove(getInactivePlayer());
    }

    public void undoMove(Player player) {

        if (isFinished() || rounds.size() < 2 || !canUndo(player)) {
            return;
        }
        
        rounds.remove(rounds.size() - 1);
        switchPlayerTurn();
        
        getActivePlayer().setUndoCount(1);
        mementoCareTaker.restoreState(board);

    }

    private void switchPlayerTurn() {
        if (activePlayerIndex == 1) {
            activePlayerIndex = 0;
        } else {
            activePlayerIndex++;
        }
    }

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
