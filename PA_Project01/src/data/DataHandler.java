/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import data.dao.dao_json.UserDAOJSON;
import data.dao.dao_json.MachineDAOJSON;
import game.models.ComputerGame;
import game.models.Game;
import game.models.Player;
import game.models.User;

/**
 *
 * @author Tiago
 */
public class DataHandler {
    
    public static void insertPlayer(Player player){
        
    }

    public static void saveGame(Game game) {

        if (game instanceof ComputerGame) {
            
            MachineDAOJSON.getInstance().addGamePlayed();

            if (game.getInactivePlayer() instanceof User) {
                //user ganhou
                UserDAOJSON.getInstance().addGamePlayed(game.getInactivePlayer().getUsername());
                UserDAOJSON.getInstance().addVictory(game.getInactivePlayer().getUsername());
            }

            if (game.getActivePlayer() instanceof User) {
                //user perdeu
                UserDAOJSON.getInstance().addGamePlayed(game.getActivePlayer().getUsername());
                
                MachineDAOJSON.getInstance().addVictory();
            }
        }

    }

}
