package graphics.views.menus;

import game.Main;
import graphics.custom.BackButton;
import graphics.custom.StyledButton;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tiago
 */
public class ComputerStatisticsOptions extends VBox {

    private StyledButton easy;
    private StyledButton hard;
    private HBox topHbox;

    public ComputerStatisticsOptions(String username) {

        setSpacing(10);

        setStyle("-fx-background-color: #e6c990;");
        setAlignment(Pos.CENTER);

        easy = new StyledButton("Dificuldade: Fácil", 150);
        hard = new StyledButton("Dificuldade: Difícil", 150);

        easy.setOnAction((event) -> {
            Main.switchContent(new ComputerStatistics(username, "easy"));
        });

        hard.setOnAction(e -> {
            Main.switchContent(new ComputerStatistics(username, "hard"));
        });

        BackButton back = new BackButton(new Runnable() {
            @Override
            public void run() {
                Main.switchContent(new AccountOptionsMenu(username));
            }
        });

        topHbox = new HBox();
        topHbox.getChildren().add(back);
        topHbox.setMaxHeight(20);
        topHbox.setTranslateY(-245);

        easy.setTranslateY(-30);
        hard.setTranslateY(-30);

        getChildren().add(topHbox);
        getChildren().add(easy);
        getChildren().add(hard);
    }

    public StyledButton getEasy() {
        return easy;
    }

    public StyledButton getHard() {
        return hard;
    }

    public void translateHbox(int am) {
        topHbox.setTranslateY(am);
    }

}
