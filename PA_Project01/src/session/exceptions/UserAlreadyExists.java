package session.exceptions;

/**
 * Exception throwed when a user attempts to register an account using 
 * a username that already exists in the files.
 * 
 * @author Tiago
 * @author Ruben
 */
public class UserAlreadyExists extends RuntimeException{

    public UserAlreadyExists() {
        super("Já existe uma conta com este nome");
    }
    
}
