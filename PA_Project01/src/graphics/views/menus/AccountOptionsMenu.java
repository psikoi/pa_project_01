/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics.views.menus;

import game.Main;
import game.models.User;
import graphics.custom.BackButton;
import graphics.custom.StyledButton;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import session.Authentication;

/**
 *
 * @author Tiago
 */
public class AccountOptionsMenu extends VBox {

    private BackButton back;
    private StyledButton statistics;
    private StyledButton computerStatistics;
    
    public AccountOptionsMenu(String username) {

        setSpacing(10);

        setStyle("-fx-background-color: #e6c990;");
        setAlignment(Pos.CENTER);

        statistics = new StyledButton("Estatisticas", 150);
        computerStatistics = new StyledButton("Estatisticas do computador", 150);

        statistics.setOnAction((event) -> {
            Main.switchContent(new UserStatisticsOptions(username));
        });

        computerStatistics.setOnAction(e -> {
            Main.switchContent(new ComputerStatisticsOptions(username));
        });


        back = new BackButton(new Runnable() {
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
