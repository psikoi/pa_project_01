package game;

import data.DataHandler;
import game.models.ComputerGame;
import game.models.Game;
import game.models.GameDifficulty;
import game.models.Machine;
import game.models.User;
import graphics.presenters.GamePresenter;
import graphics.views.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;

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
        //GameSystem.init();

        Game game = createGame();

        game.start();

        gamePresenter = new GamePresenter(game, new GameView(game));
        
        
        launch(args);

    }

    private static Game createGame() {

        User user = new User("Ruben", "123", "ruben.amendoeira@gmail.com");
        User user2 = new User("Tiago", "123", "tiago.afsantos@hotmail.com");

        DataHandler.insertPlayer(user);
        DataHandler.insertPlayer(user2);


        return new ComputerGame(user, new Machine(), 1, 600, GameDifficulty.EASY);
    }

}
