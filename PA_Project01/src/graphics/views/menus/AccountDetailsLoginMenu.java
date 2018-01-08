/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics.views.menus;

import game.Main;
import game.models.User;
import session.SessionManager;

/**
 *
 * @author Tiago
 */
public class AccountDetailsLoginMenu extends LoginMenu{
    public AccountDetailsLoginMenu(){
        super();
        super.getSingleLoginMenu().getLoginButton().setOnAction(e -> {
            String username;
            username = super.getSingleLoginMenu().getUsername();
            if(super.getSingleLoginMenu().login()){
                Main.switchContent(new AccountOptionsMenu(username));
            }
        });
        
        super.getSingleLoginMenu().selectButton.setOnAction(e -> {
            User s = SessionManager.getLoggedInUsers().get(super.getSingleLoginMenu().getUserSelection().getSelectionModel().getSelectedIndex());
            
            if (s != null) {
                Main.switchContent(new AccountOptionsMenu(s.getUsername()));
            }
        });
        
        super.getBackButton().setOnAction(e -> {
            Main.switchContent(new HomeScreenMenu());
        });
        
    }
}
