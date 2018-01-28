package graphics.views.menus;

import game.Main;
import graphics.custom.BackButton;
import graphics.custom.StyledButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PlayerTypeSelectionMenu extends VBox {

    public PlayerTypeSelectionMenu() {
        setSpacing(30);
        setStyle("-fx-background-color: #e6c990;");
        setAlignment(Pos.CENTER);

        Label question = new Label("Jogar contra:");
        question.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);

        StyledButton user = new StyledButton("Utilizador", 150);
        StyledButton computer = new StyledButton("Computador", 150);

        user.setOnAction((event) -> {
            Main.switchContent(new DoubleLoginMenu());
        });

        computer.setOnAction((event) -> {
            Main.switchContent(new LoginMenu());
        });

        BackButton back = new BackButton(new Runnable() {
            @Override
            public void run() {
                Main.switchContent(new HomeScreenMenu());
            }
        });
        
        
        HBox topHbox = new HBox();
        topHbox.getChildren().add(back);
        topHbox.setMaxHeight(20);
        topHbox.setTranslateY(-234);
        
        question.setTranslateY(-40);
        hbox.setTranslateY(-40);
        
        hbox.getChildren().add(user);
        hbox.getChildren().add(computer);
        getChildren().add(topHbox);
        getChildren().add(question);
        getChildren().add(hbox);
    }

}
