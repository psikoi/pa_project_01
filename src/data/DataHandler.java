package data;

import data.dao.MachineDAO;
import data.dao.UserDAO;
import data.dao.json.UserDAOJSON;
import data.dao.json.MachineDAOJSON;
import encryption.MD5Encrypter;
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
    
    private static UserDAO userDao;
    private static MachineDAO machineDao;
    
    public static void setDao(UserDAO user, MachineDAO machine){
        userDao = user;
        machineDao = machine;
    }

    public static void insertPlayer(Player player) {
        if (player instanceof User) {
            String password = encryptText(((User) player).getPassword());
            ((User) player).setPassword(password);
            userDao.insert((User) player);
        }

        if (player instanceof Machine) {
            machineDao.insert((Machine) player);
        }
    }
    
    public static User selectUser(String username){
        return userDao.select(username);
    }

    public static void saveGame(Game game) {

        if (game instanceof ComputerGame) {

            machineDao.addGamePlayed();
            machineDao.addTimePlayed(game.getDuration());

            if (game.getInactivePlayer() instanceof User) {
                //user ganhou
                Player user = game.getInactivePlayer();
                userDao.addGamePlayed( ((User)user).getUsername());
                userDao.addVictory(((User)user).getUsername());
                userDao.addTimePlayed(((User)user).getUsername(), game.getDuration());
                
                machineDao.addLoss();
                
            }

            if (game.getActivePlayer() instanceof User) {
                //user perdeu
                Player user = game.getActivePlayer();
                userDao.addGamePlayed(((User)user).getUsername());
                userDao.addLoss(((User)user).getUsername());
                userDao.addTimePlayed(((User)user).getUsername(), game.getDuration());

                machineDao.addVictory();
                
            }
        }
    }
    
    private static String encryptText(String text){
        return MD5Encrypter.md5Encryption(text);
    }

}