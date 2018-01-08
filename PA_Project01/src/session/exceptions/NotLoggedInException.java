package session.exceptions;

/**
 * Exception throwed when a user attempts to log out of an account that doesn't
 * have an active session.
 * 
 * @author Tiago
 * @author Ruben
 */
public class NotLoggedInException extends RuntimeException{

    public NotLoggedInException() {
        super("A sessão não está iniciada");
    }
    
}
