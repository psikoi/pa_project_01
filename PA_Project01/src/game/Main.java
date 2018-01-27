package game;

import data.DataHandler;
import data.dao.json.*;
import graphics.views.menus.HomeScreenMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

        DataHandler.setDao(new UserDAOJSON(), new MachineDAOJSON());

        launch(args);

    }

    public static void switchContent(Pane pane) {
        stage.getScene().setRoot(pane);
    }

}
