package game.factories;

import data.DataHandler;
import game.Main;
import game.models.ComputerGame;
import game.models.Game;
import game.models.GameDifficulty;
import game.models.Machine;
import game.models.User;
import java.util.ArrayList;
import session.SessionManager;

/**
 * This is a standart pattern Factory class that initiates a Game object.  
 * 
 * @author Ruben
 * @author Tiago
 */
public class GameFactory {

    /**
     * This method initiates a new Game object.
     * 
     * @param difficulty (The game's difficulty, if null, it's a player vs player game)
     * @return Game
     */
    public Game create(GameDifficulty difficulty) {

        ArrayList<User> loggedIn = SessionManager.getLoggedInUsers();

        if (difficulty == null) {
            return new Game(loggedIn.get(0), loggedIn.get(1), 1, Main.screenWidth);
        } else {
            Machine machine = new Machine();
            DataHandler.insertPlayer(machine);
            return new ComputerGame(loggedIn.get(0), machine, 1, Main.screenWidth, difficulty);
        }
        
    }
}
