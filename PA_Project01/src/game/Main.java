package game;

import data.DataHandler;
import data.dao.sqlite.MachineDAOSQLite;
import data.dao.sqlite.UserDAOSQLite;
import graphics.views.menus.HomeScreenMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import session.Authentication;

public class Main extends Application {

    public static Stage stage;
    public static int screenWidth = 650;

    @Override
    public void start(Stage primaryStage) {

        stage = primaryStage;

        Scene scene = new Scene(new Pane(), screenWidth, screenWidth);

        scene.setRoot(new HomeScreenMenu());

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {

        DataHandler.setDao(new UserDAOSQLite(), new MachineDAOSQLite());

        Authentication.register("Ruben", "123", "ruben.amendoeira@gmail.com");
        Authentication.register("Tiago", "123321", "tiago.afsantos@hotmail.com");
        Authentication.register("Rui", "tshirt", "rui.miguel@hotmail.com");

        Authentication.login("Ruben", "123");

        launch(args);

    }

    public static void switchContent(Pane pane) {
        stage.getScene().setRoot(pane);
    }

}
