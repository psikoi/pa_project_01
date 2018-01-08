package session.exceptions;

/**
 * Exception throwed when a user attempts to log in into an account that doesn't
 * exist in the files.
 * 
 * @author Tiago
 * @author Ruben
 */
public class NoUserFoundException extends RuntimeException{

    public NoUserFoundException() {
        super("O nome de utilizador inserido não corresponde a uma conta");
    }
    
}
