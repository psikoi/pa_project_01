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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tiago
 */
public class ComputerStatisticsOptions extends VBox {

    private BackButton back; 
    protected HBox topHbox;
    protected StyledButton easy;
    protected StyledButton hard;

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
        
        back = new BackButton(new Runnable() {
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
    
}
