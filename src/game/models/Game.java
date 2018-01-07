package game.models;

import data.DataHandler;
import data.dao.json.UserDAOJSON;
import game.GameSystem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import tads.graph.Edge;
import tads.graph.Vertex;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

public class Game {

    private Board board;
    private int level;

    private Player player1;
    private Player player2;

    private ArrayList<Round> rounds;

    private int activePlayerIndex;

    public Game(Player player1, Player player2, int level) {

        this.board = new Board((int) GameSystem.dimensions.getWidth());
        this.level = level;

        this.player1 = player1;
        this.player2 = player2;

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

    public int getPlayerIndex(Player player) {
        return player1.equals(player) ? 0 : 1;
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

    public void undoMove(Player player) {

        if (isFinished() || rounds.size() < 2 || !canUndo(player)) {
            return;
        }

        Round secToLast = rounds.get(rounds.size() - 2);

        secToLast.select(null, false);
        GameSystem.refresh();

    }

    public void play(Edge<Connection, Joint> selected) {

        if (isFinished()) {
            return;
        }

        ArrayList<Edge<Connection, Joint>> triangleEdges
                = board.checkMove(getActivePlayer(), selected);

        if (triangleEdges.isEmpty()) {
            getCurrentRound().select(selected.element(), false);
            skipRound();
        } else {
//            addGameToPlayers();
//            addVictoryToPlayer();
            getCurrentRound().select(selected.element(), true);
            displaySolution(triangleEdges);
            end();
            
        }

        GameSystem.refresh();
    }

    private void displaySolution(ArrayList<Edge<Connection, Joint>> edges) {

        HashSet<Joint> joints = new HashSet<>();

        for (Edge<Connection, Joint> edge : edges) {
            for (Vertex<Joint> joint : edge.vertices()) {
                joints.add(joint.element());
            }
        }

        GameSystem.gameController.showSolution(joints);
        GameSystem.refresh();

    }

    public void end() {
        //Cria classe DataHandler
        //com metodo save(Game)
        //que guarda as stats dos players
        DataHandler.saveGame(this);
    }

//    private void addGameToPlayers(){
//        System.out.println("q");
//        UserDAOJSON.getInstance().addGamePlayed(((User)player1).getUsername());
//        System.out.println("h");
//        player2.addGamePlayed();
//       // UserDAOJSON.getInstance().addGamePlayed(((User)player2).getUsername());
//    }
//    
//    private void addVictoryToPlayer(){
//        UserDAOJSON.getInstance().addVictory(((User)player1).getUsername());
//    }
}
