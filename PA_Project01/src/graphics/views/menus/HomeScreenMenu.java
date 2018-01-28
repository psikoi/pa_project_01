package graphics.views.menus;

import game.Main;
import graphics.custom.StyledButton;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class HomeScreenMenu extends VBox {

    public HomeScreenMenu() {

        setSpacing(10);

        setStyle("-fx-background-color: #e6c990;");
        setAlignment(Pos.CENTER);

        StyledButton play = new StyledButton("Jogar", 150);
        StyledButton register = new StyledButton("Registar", 150);
        StyledButton login = new StyledButton("Login", 150);
        StyledButton account = new StyledButton("Conta", 150);
        StyledButton tutorial = new StyledButton("Tutorial", 150);

        tutorial.setDisable(true);

        play.setOnAction((event) -> {
            Main.switchContent(new PlayerTypeSelectionMenu());
        });
        
        register.setOnAction(e -> {
            Main.switchContent(new RegisterMenu());
        });
        
        login.setOnAction(e -> {
            LoginMenu noAlreadyLoggedInMenu = new LoginMenu();
            noAlreadyLoggedInMenu.removeAlreadyLoggedIn();
            
            Main.switchContent(noAlreadyLoggedInMenu);
        });
        
        account.setOnAction(e -> {
            Main.switchContent(new AccountDetailsLoginMenu());
        });
        
        

        getChildren().add(new ImageView(new Image("/images/logo.png")));

        getChildren().add(play);
        getChildren().add(register);
        getChildren().add(login);
        getChildren().add(account);
        getChildren().add(tutorial);

    }

}
