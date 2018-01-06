package game.models;

import data.dao.json.UserDAOJSON;

/** This class represents an User. It extends from Player, allowing it to take
 * part in games and make a distinction between human players and the 
 * computer.
 * 
 * 
 * @author Tiago
 * @author Ruben
 */

public class User extends Player {
    
    /**
     * User information.
     */
    private String username, password, email;

    /**
     * Constructs a new User with the specified attributes.
     * 
     * @param username username of the new user.
     * @param password password of the new user.
     * @param email email of the new user.
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Returns the username.
     * 
     * @return username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password.
     * 
     * @return password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the email.
     * 
     * @return email. 
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Changes the username value to the specified one.
     * 
     * @param username new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Changes the password value to the specified one.
     * 
     * @param password new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Changes the email value to the specified one.
     * 
     * @param email new email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    
}
