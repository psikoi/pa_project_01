package game;

import data.DataHandler;
import data.dao.json.*;
import data.dao.serialization.*;
import data.dao.sqlite.*;
import game.models.ComputerGame;
import game.models.Game;
import game.models.GameDifficulty;
import game.models.Machine;
import game.models.User;
import graphics.presenters.GamePresenter;
import graphics.views.GameView;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
import session.Authentication;
import session.SessionManager;

public class Main extends Application {

    public static Stage stage;

    public static GamePresenter gamePresenter;

    @Override
    public void start(Stage primaryStage) {

        stage = primaryStage;

        Pane contentPane = new Pane();

        contentPane.getChildren().add(gamePresenter.getView());

        Scene scene = new Scene(contentPane, 600, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {

        Game game = createGame();

        game.start();

        gamePresenter = new GamePresenter(game, new GameView(game));

        launch(args);

    }

    private static Game createGame() {

        DataHandler.setDao(new UserDAOJSON(), new MachineDAOSerialization());

        Authentication.register("Ruben", "123", "ruben.amendoeira@gmail.com");
        Authentication.register("Tiago", "123321", "tiago.afsantos@hotmail.com");
        Authentication.register("Rui", "tshirt", "rui.miguel@hotmail.com");

        Authentication.login("Ruben", "123");
        Authentication.login("Tiago", "123321");

        ArrayList<User> loggedIn = SessionManager.getLoggedInUsers();
        
        Machine machine = new Machine();
        DataHandler.insertPlayer(machine);

        return new ComputerGame(loggedIn.get(0), machine, 1, 600, GameDifficulty.HARD);
    }

}
