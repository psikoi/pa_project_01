package game;

import data.DataHandler;
import data.dao.json.*;
import data.dao.serialization.*;
import data.dao.sqlite.*;
import game.models.ComputerGame;
import game.models.Game;
import game.models.Machine;
import game.models.User;
import graphics.controllers.GameController;
import java.awt.Dimension;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import session.Authentication;
import session.SessionManager;

public class GameSystem {

    public static Game game;
    public static GameController gameController;

    public static Dimension dimensions;
    public static Pane contentPane;

    public static void init() {

        dimensions = new Dimension(600, 600);
        
        DataHandler.setDao(new UserDAOJSON(), new MachineDAOSerialization());

        Authentication.register("Ruben", "123", "ruben.amendoeira@gmail.com");
        Authentication.register("Tiago", "123321", "tiago.afsantos@hotmail.com");
        Authentication.register("Rui", "tshirt", "rui.miguel@hotmail.com");
        Authentication.register("Tiago", "33333", "123.tiago@hotmail.com");
        
        Authentication.login("Ruben", "123");
        Authentication.login("Tiago", "123321");
        
        ArrayList<User> loggedIn = SessionManager.getLoggedInUsers();
        
        //User user = new User("Ruben", "123", "ruben.amendoeira@gmail.com");
//        User user2 = new User("Tiago", "123321", "tiago.afsantos@hotmail.com");
        
        Machine machine = new Machine();
        
        DataHandler.insertPlayer(machine);
        
        game = new ComputerGame(loggedIn.get(0), machine, 1);
        game.start();
        
        
        gameController = new GameController(game);
    }
    
    public static void refresh(){
        contentPane.getChildren().clear();
        gameController.render(contentPane);
    }

}
