package data;

import data.dao.MachineDAO;
import data.dao.UserDAO;
import data.encryption.MD5Encrypter;
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

    public static void setDao(UserDAO user, MachineDAO machine) {
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

    public static User selectUser(String username) {
        return userDao.select(username);
    }

    public static void saveGame(Game game) {

        if (game instanceof ComputerGame) {
            if (((ComputerGame) game).isDifficultyEasy()) {
                saveEasyGame(game);
            } else {
                saveHardGame(game);
            }
        } else {
            savePVPGame(game);
        }
    }

    private static void saveEasyGame(Game game) {
        machineDao.addEasyGamePlayed();
        machineDao.addEasyTimePlayed(game.getDuration());

        if (game.getInactivePlayer() instanceof User) {
            //user ganhou
            Player user = game.getInactivePlayer();
            userDao.addEasyGamePlayed(((User) user).getUsername());
            userDao.addEasyVictory(((User) user).getUsername());
            userDao.addEasyTimePlayed(((User) user).getUsername(), game.getDuration());

            machineDao.addEasyLoss();

        }

        if (game.getActivePlayer() instanceof User) {
            //user perdeu
            Player user = game.getActivePlayer();
            userDao.addEasyGamePlayed(((User) user).getUsername());
            userDao.addEasyLoss(((User) user).getUsername());
            userDao.addEasyTimePlayed(((User) user).getUsername(), game.getDuration());

            machineDao.addEasyVictory();

        }
    }

    private static void saveHardGame(Game game) {
        machineDao.addHardGamePlayed();
        machineDao.addHardTimePlayed(game.getDuration());

        if (game.getInactivePlayer() instanceof User) {
            //user ganhou
            Player user = game.getInactivePlayer();
            userDao.addHardGamePlayed(((User) user).getUsername());
            userDao.addHardVictory(((User) user).getUsername());
            userDao.addHardTimePlayed(((User) user).getUsername(), game.getDuration());

            machineDao.addHardLoss();

        }

        if (game.getActivePlayer() instanceof User) {
            //user perdeu
            Player user = game.getActivePlayer();
            userDao.addHardGamePlayed(((User) user).getUsername());
            userDao.addHardLoss(((User) user).getUsername());
            userDao.addHardTimePlayed(((User) user).getUsername(), game.getDuration());

            machineDao.addHardVictory();
        }
    }
    
    private static void savePVPGame(Game game){
        String activeUsername = ((User)game.getActivePlayer()).getUsername();
        String inactiveUsername = ((User)game.getInactivePlayer()).getUsername();
        
        userDao.addPVPGamePlayed(activeUsername);
        userDao.addPVPGamePlayed(inactiveUsername);
        
        long gameDuration = game.getDuration();
        
        userDao.addPVPTimePlayed(activeUsername, gameDuration);
        userDao.addPVPTimePlayed(inactiveUsername, gameDuration);
        
        userDao.addPVPVictory(inactiveUsername);
        userDao.addPVPLoss(activeUsername);
    }

    private static String encryptText(String text) {
        return MD5Encrypter.md5Encryption(text);
    }

}
