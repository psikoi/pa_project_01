package game;

import com.sun.glass.events.WindowEvent;
import encryption.MD5Encrypter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        GameSystem.contentPane = new Pane();

        Scene scene = new Scene(
                GameSystem.contentPane,
                GameSystem.dimensions.getWidth(),
                GameSystem.dimensions.getHeight());

        GameSystem.gameController.render(GameSystem.contentPane);

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
        });
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        GameSystem.init();
        launch(args);
    }

}
