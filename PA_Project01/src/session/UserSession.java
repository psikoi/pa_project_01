/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import game.models.User;

/**
 *
 * @author Tiago
 */
public class UserSession {
    private User user;
    
    public UserSession(User user){
        this.user = user;
    }
    
    public User getUser(){
        return user;
    }
}
