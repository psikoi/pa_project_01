package graphics.views.menus;

import game.Main;
import game.factories.GameFactory;
import game.models.Game;
import game.models.GameDifficulty;
import graphics.custom.BackButton;
import graphics.custom.StyledButton;
import graphics.presenters.GamePresenter;
import graphics.views.GameView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ComputerDifficultySelectionMenu extends VBox {

    public ComputerDifficultySelectionMenu() {

        setSpacing(30);
        setStyle("-fx-background-color: #e6c990;");
        setAlignment(Pos.CENTER);

        Label question = new Label("Dificuldade:");
        question.setFont(Font.font("Arial", FontWeight.BOLD, 25));

        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);

        StyledButton easy = new StyledButton("Fácil", 150);
        StyledButton hard = new StyledButton("Difícil", 150);

        easy.setOnAction((event) -> {
            startGame(new GameFactory().create(GameDifficulty.EASY));
        });

        hard.setOnAction((event) -> {
            startGame(new GameFactory().create(GameDifficulty.HARD));
        });

        BackButton back = new BackButton(new Runnable() {
            @Override
            public void run() {
                Main.switchContent(new LoginMenu());
            }
        });

        HBox topHbox = new HBox();
        topHbox.getChildren().add(back);
        topHbox.setMaxHeight(20);
        topHbox.setTranslateY(-235);

        hbox.getChildren().add(easy);
        hbox.getChildren().add(hard);
        hbox.setTranslateY(-50);

        question.setTranslateY(-50);

        getChildren().add(topHbox);
        getChildren().add(question);
        getChildren().add(hbox);

    }

    private void startGame(Game game) {
        game.start();
        GamePresenter p = new GamePresenter(game, new GameView(game));
        Main.switchContent(p.getView());
    }

}
