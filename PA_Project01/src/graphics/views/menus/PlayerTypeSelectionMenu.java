package graphics.views.menus;

import game.Main;
import graphics.custom.StyledButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PlayerTypeSelectionMenu extends VBox {

    private StyledButton user;
    private StyledButton computer;

    public PlayerTypeSelectionMenu() {

        setSpacing(30);
        setStyle("-fx-background-color: #e6c990;");
        setAlignment(Pos.CENTER);

        Label question = new Label("Jogar contra:");
        question.setFont(Font.font("Arial", FontWeight.BOLD, 25));

        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);

        user = new StyledButton("Utilizador", 150);
        computer = new StyledButton("Computador", 150);

        user.setOnAction((event) -> {
            Main.switchContent(new DoubleLoginMenu());
        });

        computer.setOnAction((event) -> {
            Main.switchContent(new SingleLoginMenu());
        });

        hbox.getChildren().add(user);
        hbox.getChildren().add(computer);

        getChildren().add(question);
        getChildren().add(hbox);
    }

}
