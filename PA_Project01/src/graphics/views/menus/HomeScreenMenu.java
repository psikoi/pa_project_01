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
    private StyledButton login;
    private StyledButton account;
    private StyledButton tutorial;

    public HomeScreenMenu() {

        setSpacing(10);

        img = new Image("/images/logo.png");

        setStyle("-fx-background-color: #e6c990;");
        setAlignment(Pos.CENTER);

        play = new StyledButton("Jogar", 150);
        register = new StyledButton("Registar", 150);
        login = new StyledButton("Login", 150);
        account = new StyledButton("Conta", 150);
        tutorial = new StyledButton("Tutorial", 150);

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
        
        

        getChildren().add(new ImageView(img));

        getChildren().add(play);
        getChildren().add(register);
        getChildren().add(login);
        getChildren().add(account);
        getChildren().add(tutorial);

    }

}
