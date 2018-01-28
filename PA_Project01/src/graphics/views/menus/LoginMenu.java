/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics.views.menus;

import game.Main;
import graphics.custom.BackButton;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tiago
 */
public class LoginMenu extends VBox{
    
    private SingleLoginMenu loginMenu;
    private BackButton back;
    private HBox topHbox;
    
    public LoginMenu(){
        setAlignment(Pos.CENTER);
        setSpacing(30);
        setStyle("-fx-background-color: #e6c990;");
        
        loginMenu = new SingleLoginMenu();
        
        back = new BackButton(new Runnable() {
            @Override
            public void run() {
                Main.switchContent(new PlayerTypeSelectionMenu());
            }
        });
        
        
        topHbox = new HBox();
        topHbox.getChildren().add(back);
        topHbox.setMaxHeight(20);
        topHbox.setTranslateY(-34);
        
        loginMenu.setTranslateY(-30);
        
        getChildren().add(topHbox);
        getChildren().add(loginMenu);
    }
    
    public void removeAlreadyLoggedIn(){
        loginMenu.removeAlreadyLoggedIn();
        
        back.setOnAction(e -> {
            Main.switchContent(new HomeScreenMenu());
        });
        
        topHbox.setTranslateY(-150);
        
    }
    
    public SingleLoginMenu getSingleLoginMenu(){
        return loginMenu;
    }
    
    public BackButton getBackButton(){
        return back;
    }
    
}
