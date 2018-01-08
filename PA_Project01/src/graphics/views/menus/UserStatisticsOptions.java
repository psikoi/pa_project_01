/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics.views.menus;

import game.Main;
import graphics.custom.StyledButton;

/**
 *
 * @author Tiago
 */
public class UserStatisticsOptions extends ComputerStatisticsOptions{
    
    private StyledButton player; 
            
    public UserStatisticsOptions(String username){
        super(username);
        
        player = new StyledButton("Contra jogadores", 150);

        player.setOnAction((event) -> {
            Main.switchContent(new UserStatistics(username, "player"));
        });
        
        easy.setOnAction(e -> {
            Main.switchContent(new UserStatistics(username, "easy"));
        });
        
        hard.setOnAction(e -> {
            Main.switchContent(new UserStatistics(username, "hard"));
        });
        
        topHbox.setTranslateY(-215);
        
        player.setTranslateY(-30);
        
        getChildren().add(player);
    }
}
