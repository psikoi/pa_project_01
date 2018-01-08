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
    private StyledButton logout;

    public AccountOptionsMenu(String username) {

        setSpacing(10);

        setStyle("-fx-background-color: #e6c990;");
        setAlignment(Pos.CENTER);

        statistics = new StyledButton("Estatisticas", 150);
        computerStatistics = new StyledButton("Estatisticas do computador", 150);
        logout = new StyledButton("Logout", 150);

        statistics.setOnAction((event) -> {
            Main.switchContent(new UserStatisticsOptions(username));
        });

        computerStatistics.setOnAction(e -> {
            Main.switchContent(new ComputerStatisticsOptions(username));
        });

        logout.setOnAction(e -> {
            try {
                Main.switchContent(new HomeScreenMenu());
                Authentication.logout(username);
            } catch (RuntimeException exception) {
                alertLogoutFailure(exception.getMessage());
            }
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
        logout.setTranslateY(-30);

        getChildren().add(topHbox);
        getChildren().add(statistics);
        getChildren().add(computerStatistics);
        getChildren().add(logout);
    }

    public void alertLogoutFailure(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erro");
        alert.setHeaderText("Ocorreu um erro ao terminar a sessão");
         if(message == null)
             message = "Por alguma razão desconhecida, o logout lança uma exceção que não está a ocorrer. O log out foi finalizado com sucesso";
        alert.setContentText(message);
       

        alert.showAndWait();
    }

}
