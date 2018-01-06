package data;

import data.dao.json.UserDAOJSON;
import data.dao.json.MachineDAOJSON;
import game.models.ComputerGame;
import game.models.Game;
import game.models.Machine;
import game.models.Player;
import game.models.User;

/**
 *
 * @author Tiago
 */
public class DataHandler {

    public static void insertPlayer(Player player) {
        if (player instanceof User) {
            UserDAOJSON.getInstance().insert((User) player);
        }

        if (player instanceof Machine) {
            MachineDAOJSON.getInstance().insert((Machine) player);
        }
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
