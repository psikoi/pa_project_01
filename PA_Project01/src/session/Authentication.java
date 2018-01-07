package session;

import data.DataHandler;
import data.encryption.MD5Encrypter;
import game.models.User;

/**
 *
 * @author Tiago
 */
public class Authentication {

    public static boolean login(String username, String password) {

        //se já houverem 2 sessoes iniciadas
        if (SessionManager.getUserSessions().size() == 2) {
            return false;
        }

        // se nao existir utilizador
        if (!checkIfUserExists(username)) {
            return false;
        }

        //se alguem ja tiver a sessao iniciada nesta conta, ele nao pode fazer login
        if (checkIfUserLoggedIn(username)) {
            return false;
        }

        //verifica a password
        User aux = DataHandler.selectUser(username);
        if (!(MD5Encrypter.md5Encryption(password).equals(aux.getPassword()))) {
            return false;
        }

        //fazer login
        UserSession session = new UserSession(aux);
        SessionManager.getUserSessions().add(session);
        return true;
    }

    public static boolean logout(String username) {
        if (!checkIfUserExists(username)) {
            return false;
        }

        if (!checkIfUserLoggedIn(username)) {
            return false;
        }

        SessionManager.removeSession(username);
        return true;
    }

    public static boolean register(String username, String password, String email) {
        //se o utilizador existir
        if (checkIfUserExists(username)) {
            return false;
        }

        User user = new User(username, password, email);
        DataHandler.insertPlayer(user);
        return true;
    }

    //true -> logged in, false -> not logged in
    private static boolean checkIfUserLoggedIn(String username) {

        for (UserSession userSession : SessionManager.getUserSessions()) {
            if (userSession.getUser().getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    //true -> existe, false -> nao existe
    private static boolean checkIfUserExists(String username) {
        User aux = DataHandler.selectUser(username);

        return aux != null;
    }
}
