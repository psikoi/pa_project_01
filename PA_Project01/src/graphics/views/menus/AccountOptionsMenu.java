/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class AccountOptionsMenu extends VBox {
    
    public AccountOptionsMenu(String username) {

        setSpacing(10);

        setStyle("-fx-background-color: #e6c990;");
        setAlignment(Pos.CENTER);

        StyledButton statistics = new StyledButton("Estatisticas", 150);
        StyledButton computerStatistics = new StyledButton("Estatisticas do computador", 150);

        statistics.setOnAction((event) -> {
            Main.switchContent(new UserStatisticsOptions(username));
        });

        computerStatistics.setOnAction(e -> {
            Main.switchContent(new ComputerStatisticsOptions(username));
        });


        BackButton back = new BackButton(new Runnable() {
            @Override
            public void run() {
                Main.switchContent(new AccountDetailsLoginMenu());
            }
        });

        HBox topHbox = new HBox();
        topHbox.getChildren().add(back);
        topHbox.setMaxHeight(20);
        topHbox.setTranslateY(-213);

        statistics.setTranslateY(-30);
        computerStatistics.setTranslateY(-30);
        getChildren().add(topHbox);
        getChildren().add(statistics);
        getChildren().add(computerStatistics);
    }

}
