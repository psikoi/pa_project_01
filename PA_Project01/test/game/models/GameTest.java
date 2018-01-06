/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.models;

import java.awt.Dimension;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tads.graph.Edge;
import tads.graph.MyGraph;
import tads.graph.model.Connection;
import tads.graph.model.Joint;

/**
 *
 * @author Tiago
 */
public class GameTest {
    
    private Game game;
    
    public GameTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        game = new Game(new Player(), new Player(), 5, 600);
    }
    
    @After
    public void tearDown() {
    }
    


    @Test
    public void getPlayer1_Player1_GettingPlayer1(){
        Player player1 = new Player();
        Game gameAux = new Game(player1, new Player(), 5, 600);
      
        assertEquals("Os jogadores devem ser iguais", player1, gameAux.getPlayer1());
    }
   
    @Test
    public void getPlayer2_Player2_GettingPlayer2(){
        Player player2 = new Player();
        Game gameAux = new Game(new Player(), player2, 5, 600);
        
        assertEquals("Os jogadores devem ser iguais", player2, gameAux.getPlayer2());
    }
    
    
    @Test
    public void getRounds_Rounds_WithRounds(){}
    
    @Test
    public void getBoard_Board_GettingBoard(){}
    
    @Test
    public void isFinished_False_NotFinished(){
        game.start();
        assertEquals("O jogo ainda não terminou", false, game.isFinished());
    }
    
    @Test
    public void isFinished_True_GameFinished(){
        Connection connection = new Connection();
        Round round = new Round(new Player());
        round.select(connection, true);
        
        game.start();
        game.getRounds().add(round);
        
        assertEquals("O jogo deveria estar acabado", true, game.isFinished());
    }
    
    @Test
    public void hasStarted_False_NotStarted(){
        assertEquals("O jogo ainda nao começou", false, game.hasStarted());
    }
    
    @Test
    public void hasStarted_True_GameStarted(){
        game.start();
        assertEquals("O jogo já começou", true, game.hasStarted());
    }
    
    @Test
    public void getElaspedTime_0Seconds_NotStarted(){
        assertEquals("O jogo não começou (0 segundos de jogo)", 0, game.getElapsedTime());
    }
//    
//    @Test
//    public void getElaspedTime_1Second_GameRunning(){
//        long start = System.currentTimeMillis();
//       
//        game.start();
//        
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//           
//        }
//        
//        
//        assertEquals("O tempo deveria ser 1 segundo", 
//                System.currentTimeMillis() - start, game.getElapsedTime());
//        
//    }
//    
//         @Test
//    public void getDuration_1Second_GameFinished(){
//        Connection connection = new Connection();
//        Round round = new Round(new Player());
//        round.select(connection, true);
//        
//         long start = System.currentTimeMillis();
//         
//        game.getRounds().add(round);
//        game.start();
//        
//         try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//           
//        }
//        
//        
//        assertEquals("O tempo deveria ser 1 segundo", 
//                System.currentTimeMillis() - start, game.getDuration());
//    }
//    
   
    
    @Test
    public void getCurrentRound_CurrentRound_GameRunning(){
        Connection connection = new Connection();
        Round currentRound = new Round(new Player());
        currentRound.select(connection, false);
        
        game.getRounds().add(currentRound);
        
        assertEquals("As rondas deveriam ser iguais",
                    currentRound, game.getCurrentRound());
    }
    
    @Test
    public void getCurrentRound_Null_NoRounds(){
        assertEquals("Não deveria haver rondas",
                    null, game.getCurrentRound());
    }
    
    @Test
    public void getActivePlayer_Player2_Player2Round(){
        Player player1 = new Player();
        Player player2 = new Player();
        Round currentRound = new Round(player1);
        currentRound.select(new Connection(), false);
        
        Game gameAux = new Game(player1, player2, 5);
        gameAux.getRounds().add(currentRound);
        gameAux.skipRound();
        
        assertEquals("O jogador deveria ser o \"player2\"",
                     player2, gameAux.getActivePlayer());
    }
    
    @Test
    public void getInactivePlayer_Player1_Player2Round(){
        Player player1 = new Player();
        Player player2 = new Player();
        Round currentRound = new Round(player1);
        currentRound.select(new Connection(), false);
        
        Game gameAux = new Game(player1, player2, 5);
        gameAux.getRounds().add(currentRound);
        gameAux.skipRound();
        
        assertEquals("O jogador deveria ser o \"player1\"",
                     player1, gameAux.getInactivePlayer());
    }
    
    @Test
    public void getPlayerIndex_0_Player1(){
        Player player1 = new Player();
        Player player2 = new Player();
        
        Game gameAux = new Game(player1, player2, 5);
    
        assertEquals("O indice deveria ser 0", 0, gameAux.getPlayerIndex(player1));
    }
    
    @Test
    public void getPlayerIndex_1_Player2(){
        Player player1 = new Player();
        Player player2 = new Player();
        
        Game gameAux = new Game(player1, player2, 5);
    
        assertEquals("O indice deveria ser 0", 1, gameAux.getPlayerIndex(player2));
    }
    
//    @Test
//    public void canUndo_True_Player1Undo(){
//        Player player1 = new Player();
//        Player player2 = new Player();
//        
//        Round round1 = new Round(player1);
//        Connection connection1 = new Connection();
//        connection1.select(player1);
//        round1.select(connection1, false);
//        
//        Round round2 = new Round(player2);
//        Connection connection2 = new Connection();
//        connection2.select(player2);
//        round2.select(connection2, false);
//        
//        Round round3 = new Round(player1);
//        Connection connection3 = new Connection();
//        connection3.select(player1);
//        round3.select(connection3, false);
//        
//        Game gameAux = new Game(player1, player2, 5);
//        gameAux.getRounds().add(round1);
//        gameAux.skipRound();
//        gameAux.getRounds().add(round2);
//        gameAux.skipRound();
//        gameAux.getRounds().add(round3);
//        gameAux.skipRound();
//        
//        assertEquals("O jogador pode desfazer a jogada", true, gameAux.canUndo(player1));
//    }
    
//    @Test
//    public void undoMove_True_UndoingMove(){
//        Player player1 = new Player();
//        Player player2 = new Player();
//        
//        Round round1 = new Round(player1);
//        Connection connection1 = new Connection();
//        connection1.select(player1);
//        round1.select(connection1, false);
//        
//        Round round2 = new Round(player2);
//        Connection connection2 = new Connection();
//        connection2.select(player2);
//        round2.select(connection2, false);
//        
//        Round round3 = new Round(player1);
//        Connection connection3 = new Connection();
//        connection3.select(player1);
//        round3.select(connection3, false);
//        
//        Game gameAux = new Game(player1, player2, 5);
//        gameAux.getRounds().add(round1);
//        gameAux.skipRound();
//        gameAux.getRounds().add(round2);
//        gameAux.skipRound();
//        gameAux.getRounds().add(round3);
//        gameAux.skipRound();
//        
//        gameAux.undoMove(player1);
//        
//        assertEquals("Nao devia haver connection", 
//                null, gameAux.getRounds().get(2).getSelectedEdge());
//    }
//    
//      @Test
//      public void play_True_Playing(){
//          game.start();
//          MyGraph<Joint, Connection> myGraph = new MyGraph<>();
//          Joint joint1 = new Joint(1, 1);
//          Joint joint2 = new Joint(2, 2);
//          Connection connection = new Connection();
//          
//          myGraph.insertVertex(joint1);
//          myGraph.insertVertex(joint2);
//          myGraph.insertEdge(joint1, joint2, connection);
//          
//          for(Edge<Connection, Joint> e : myGraph.edges()){
//             game.play(e);
//          }
//          
//          assertEquals("A connection deveria estar na ronda", 
//                        connection,game.getRounds().get(0).getSelectedEdge());
//          
//      }
    
}
