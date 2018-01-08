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
 * DataHandler handles the data in the files. It inserts users and machines to 
 * the files, and increments their statistics.
 * 
 * @author Ruben
 * @author Tiago
 */
public class DataHandler {

    /**
     * Type of DAO used. The possible options are JSON, Serialization and SQLite.
     */
    private static UserDAO userDao;
    private static MachineDAO machineDao;

    public static void setDao(UserDAO user, MachineDAO machine) {
        userDao = user;
        machineDao = machine;
    }

    /**
     * Inserts a user or a machine into the files.
     * @param player - player being inserted.
     */
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
    
    public static Machine getMachine(){
        return machineDao.select();
    }

    /**
     * Updates the statistics of the players in the files.
     * @param game - game being saved.
     */
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

    /**
     * Increments the statistics of the easy difficulty of the players in
     * the files (one of them being an User, and the other being a Machine).
     * @param game - game that ended, and is being used to update the statistics.
     */
    private static void saveEasyGame(Game game) {
        machineDao.addEasyGamePlayed();
        machineDao.addEasyTimePlayed(game.getDuration());

        if (game.getInactivePlayer() instanceof User) {
            //user won
            Player user = game.getInactivePlayer();
            userDao.addEasyGamePlayed(((User) user).getUsername());
            userDao.addEasyVictory(((User) user).getUsername());
            userDao.addEasyTimePlayed(((User) user).getUsername(), game.getDuration());

            machineDao.addEasyLoss();

        }

        if (game.getActivePlayer() instanceof User) {
            //user lost
            Player user = game.getActivePlayer();
            userDao.addEasyGamePlayed(((User) user).getUsername());
            userDao.addEasyLoss(((User) user).getUsername());
            userDao.addEasyTimePlayed(((User) user).getUsername(), game.getDuration());

            machineDao.addEasyVictory();

        }
    }

    /**
     * Increments the statistics of the hard difficulty of the players in
     * the files (one of them being an User, and the other being a Machine).
     * @param game - game that ended, and is being used to update the statistics.
     */
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
    
    /**
     * Increments the statistics of the player vs player game mode of the
     * players in the files (both of them being Users).
     * @param game - game that ended, and is being used to update the statistics.
     */
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

    /**
     * Method that uses the MD5Encrypter to encrypt text. Used when saving Users 
     * to the files, encrypting their passwords.
     * @param text - text being encrypted
     * @return encrypted text
     */
    private static String encryptText(String text) {
        return MD5Encrypter.md5Encryption(text);
    }

}
