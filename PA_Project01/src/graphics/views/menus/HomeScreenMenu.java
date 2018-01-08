package graphics.views.menus;

import game.Main;
import graphics.custom.StyledButton;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class HomeScreenMenu extends VBox {

    private Image img;
    private ImageView logoImgView;

    private StyledButton play;
    private StyledButton register;
    private StyledButton tutorial;

    public HomeScreenMenu() {

        setSpacing(10);

        img = new Image("/images/logo.png");

        setStyle("-fx-background-color: #e6c990;");
        setAlignment(Pos.CENTER);

        play = new StyledButton("Jogar", 150);
        register = new StyledButton("Registar", 150);
        tutorial = new StyledButton("Tutorial", 150);

        tutorial.setDisable(true);

        play.setOnAction((event) -> {
            Main.switchContent(new PlayerTypeSelectionMenu());
        });

        getChildren().add(new ImageView(img));

        getChildren().add(play);
        getChildren().add(register);
        getChildren().add(tutorial);

    }

   

}
