package session;

import data.DataHandler;
import data.encryption.MD5Encrypter;
import game.models.User;
import session.exceptions.*;

/**
 *  Authentication contains methods that allow users to log in, log out and 
 *  create accounts. These methods are static and are acessed by other 
 *  identities to aid in the logging in process.
 * 
 * @author Tiago
 * @author Ruben
 */
public class Authentication {

    /**
     * Allows a user to login to their account. Throws exceptions when errors in 
     * the logging in process occur.
     * @param username - Username
     * @param password - Password
     */
    public static void login(String username, String password) {

        //se já houverem 2 sessões iniciadas
        if (SessionManager.getUserSessions().size() == 2) {
            throw new TooManySessionsException();
        }

        // se não existir utilizador com o username recebido 
        if (!checkIfUserExists(username)) {
            throw new NoUserFoundException();
        }

        //se a sessão nesta conta já se encontrar iniciada, o utilizador não poderá fazer login
        if (checkIfUserLoggedIn(username)) {
            throw new AlreadyLoggedInException();
        }

        //verifica a password
        User aux = DataHandler.selectUser(username);
        if (!(MD5Encrypter.md5Encryption(password).equals(aux.getPassword()))) {
            throw new IncorrectCredentialsException();
        }

        //faz login
        UserSession session = new UserSession(aux);
        SessionManager.getUserSessions().add(session);
    }

    /**
     * Allows a user to logout of their account. Throws exceptions if an error
     * occurs in the logging out process.
     * 
     * @param username - Username.
     */
    public static void logout(String username) {
        //se a conta não existir
        if (!checkIfUserExists(username)) {
            throw new NoUserFoundException();
        }
        
        //se a conta não tiver a sessão iniciada
        if (!checkIfUserLoggedIn(username)) {
            throw new NotLoggedInException();
        }

        SessionManager.removeSession(username);
    }

    /**
     * Allows a user to register an account. Uses the DataHandler to save the account
     * to a file. Throws exception if an error occurs during the process.
     * 
     * @param username - Username of the new account.
     * @param password - Password of the new account.
     * @param email - Email of the new account.
     */
    public static void register(String username, String password, String email) {
        //se o utilizador já existir
        if (checkIfUserExists(username)) {
            throw new UserAlreadyExists();
        }

        User user = new User(username, password, email);
        DataHandler.insertPlayer(user);
    }

    /**
     * Checks if a user is logged in to their account.
     * @param username - Username of the account being checked.
     * @return true if there is an active session with the account, false if there isn't.
     */
    private static boolean checkIfUserLoggedIn(String username) {

        for (UserSession userSession : SessionManager.getUserSessions()) {
            if (userSession.getUser().getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an account with a specific username already exists in the files.
     * @param username - Username being checked.
     * @return true if there is an account with the username received, false if there isn't.
     */
    private static boolean checkIfUserExists(String username) {
        User aux = DataHandler.selectUser(username);

        return aux != null;
    }
}
