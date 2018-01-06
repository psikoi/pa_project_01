package game.models;

import data.DataHandler;
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

        if (rounds.size() < 2 || player.equals(getActivePlayer())) {
            return false;
        }

        int emptyRounds = 0;

        for (Round round : rounds) {

            if (round.getPlayer().equals(player) && round.getSelectedEdge() == null) {
                emptyRounds++;
            }
        }

        return emptyRounds == 0;

    }

    public void start() {
        board.generate(level + 5);
        rounds.add(new Round(player1));
    }

    public void skipRound() {
        rounds.add(new Round(getInactivePlayer()));

        if (activePlayerIndex == 1) {
            activePlayerIndex = 0;
        } else {
            activePlayerIndex++;
        }
    }

    public Connection undoMove() {
        return undoMove(getInactivePlayer());
    }

    public Connection undoMove(Player player) {

        if (isFinished() || rounds.size() < 2 || !canUndo(player)) {
            return null;
        }

        Round secToLast = rounds.get(rounds.size() - 2);
        Connection unselected = secToLast.getSelectedEdge();

        secToLast.select(null, false);
        return unselected;
    }

    public boolean play(Edge<Connection, Joint> selected) {

        
        if (isFinished()) {
            return false;
        }

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
